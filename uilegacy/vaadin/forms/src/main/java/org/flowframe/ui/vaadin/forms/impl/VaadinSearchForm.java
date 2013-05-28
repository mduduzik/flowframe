package org.flowframe.ui.vaadin.forms.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.flowframe.ui.vaadin.forms.FormMode;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class VaadinSearchForm extends VaadinForm {
	private static final long serialVersionUID = -859931L;

	private VaadinFormHeader header;
	private VaadinFormAlertPanel alertPanel;
	private VerticalLayout layout;
	private Panel innerLayoutPanel;
	private VerticalLayout innerLayout;
	private Button addFilterButton;
	private FormMode mode;
	private Container container;
	private Item dummyItem;

	private Set<VaadinSearchFormFieldPair> filterSet;

	public VaadinSearchForm() {
		this.header = new VaadinFormHeader();
		this.layout = new VerticalLayout();
		this.innerLayout = new VerticalLayout();
		this.alertPanel = new VaadinFormAlertPanel();
		this.addFilterButton = new Button();
		this.filterSet = new HashSet<VaadinSearchFormFieldPair>();

		initialize();
	}

	private void createDummyItem() {
		Object id = this.container.getItemIds().iterator().next();
		if (id != null) {
			this.dummyItem = this.container.getItem(id);
		}
	}

	private void initialize() {
		this.addFilterButton.setCaption("Add Filter");
		this.addFilterButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				addFilter();
			}
		});

		HorizontalLayout filterGeneratorLayout = new HorizontalLayout();
		filterGeneratorLayout.setSpacing(true);
		filterGeneratorLayout.setMargin(true, false, false, true);
		filterGeneratorLayout.setStyleName("conx-entity-editor-form");
		filterGeneratorLayout.addStyleName("conx-filter-generator");
		filterGeneratorLayout.setWidth("100%");
		filterGeneratorLayout.addComponent(addFilterButton);
		filterGeneratorLayout.setComponentAlignment(addFilterButton, Alignment.BOTTOM_LEFT);

		this.alertPanel.setVisible(false);
		this.alertPanel.addCloseListener(new ClickListener() {
			private static final long serialVersionUID = 5815832688929242745L;

			@Override
			public void click(ClickEvent event) {
				VaadinSearchForm.this.alertPanel.setVisible(false);
			}
		});

		this.innerLayout.setWidth("100%");
		this.innerLayout.setSpacing(true);
		this.innerLayout.setMargin(false);
		this.innerLayout.addComponent(filterGeneratorLayout);

		this.innerLayoutPanel = new Panel();
		this.innerLayoutPanel.setSizeFull();
		((Layout) this.innerLayoutPanel.getContent()).setMargin(false, false, false, false);
		this.innerLayoutPanel.setStyleName("light");
		this.innerLayoutPanel.addComponent(innerLayout);

		this.layout.setWidth("100%");
		this.layout.setStyleName("conx-entity-editor-form");
		this.layout.addComponent(alertPanel);
		this.layout.addComponent(header);
		this.layout.addComponent(innerLayoutPanel);
		this.layout.setExpandRatio(innerLayoutPanel, 1.0f);

		setImmediate(true);
		setFormFieldFactory(new VaadinJPAFieldFactory());
		setLayout(layout);
		// False so that commit() must be called explicitly
		setWriteThrough(false);
		// Disallow invalid data from acceptance by the container
		setInvalidCommitted(false);
	}

	public FormMode getMode() {
		return mode;
	}

	@Override
	public void setTitle(String title) {
		this.header.setTitle(title);
	}

	@Override
	protected void attachField(Object propertyId, com.vaadin.ui.Field field) {
	}

	public void setContainer(Container container) {
		this.container = container;
		createDummyItem();
	}

	@Override
	public void setItemDataSource(Item newDataSource, Collection<?> propertyIds) {
	}

	@Override
	public void setItemDataSource(Item newDataSource) {
	}

	private void addFilter() {
		if (this.dummyItem != null) {
			VaadinSearchFormFieldPair newFilter = new VaadinSearchFormFieldPair(this, this.container.getContainerPropertyIds(), this.dummyItem);
			if (filterSet.size() > 0) {
				newFilter.setMargin(false, false, false, true);
			}
			this.innerLayout.addComponent(newFilter, filterSet.size());
			filterSet.add(newFilter);
		}
	}

	public void removeFilter(VaadinSearchFormFieldPair component) {
		this.innerLayout.removeComponent(component);
		if (filterSet.size() > 0) {
			filterSet.remove(component);
		}
	}
	
	public void resetForm() {
		for (VaadinSearchFormFieldPair filter : filterSet) {
			filter.setValue(null);
		}
	}
	
	public void clearForm() {
		for (VaadinSearchFormFieldPair filter : filterSet) {
			this.innerLayout.removeComponent(filter);
		}
		this.filterSet.clear();
	}

	@SuppressWarnings("rawtypes")
	public void buildQuery() {
		((JPAContainer) this.container).getEntityProvider().setQueryModifierDelegate(new DefaultQueryModifierDelegate() {

			@Override
			public void queryWillBeBuilt(CriteriaBuilder criteriaBuilder, javax.persistence.criteria.CriteriaQuery<?> query) {
			}

			@Override
			public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> query, List<Predicate> predicates) {
				Root<?> root = query.getRoots().iterator().next();
				for (VaadinSearchFormFieldPair filter : filterSet) {
					if (filter.getPropertyId() != null) {
						Path<?> path = root.get((String) filter.getPropertyId());
						Object operator = filter.getOperator();
						Predicate newPredicate = null;
						if (operator.equals(VaadinSearchFormFieldPair.OPERATOR_EQUAL_TO)) {
							newPredicate = criteriaBuilder.equal(path, filter.getValue());
						} /*
						 * else if (operator.equals(VaadinSearchFormFieldPair.
						 * OPERATOR_LESS_THAN)) { newPredicate =
						 * criteriaBuilder.equal(path, filter.getValue()); }
						 * else if (operator.equals(VaadinSearchFormFieldPair.
						 * OPERATOR_GREATER_THAN)) { newPredicate =
						 * criteriaBuilder.greaterThan(path, filter.getValue());
						 * }
						 */
						predicates.add(newPredicate);
					}
				}
			}
		});
	}
}
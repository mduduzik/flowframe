package org.flowframe.ui.component.domain;

import java.util.Set;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForceDiscriminator;

import org.flowframe.ds.domain.DataSource;
import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@SuppressWarnings("deprecation")
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="sysuicomponent")
//@DiscriminatorOptions(force=true)
// FIXME THIS IS A HACK TO MAKE QUERIES WORK.
@ForceDiscriminator
public class AbstractComponent extends MultitenantBaseEntity implements Sizeable {

    private static final long serialVersionUID = 6207901499318773670L;
    @ManyToOne
    private DataSource dataSource;
    
    /**
     * 
     * Component name
     * 
     */
    private String typeId;

    /**
     * Caption text.
     */
    private String caption;


    /**
     * Icon to be shown together with caption.
     */
    private String iconPath;

    /**
     * Is the component enabled (its normal usage is allowed).
     */
    private boolean enabled = true;

    /**
     * Is the component visible (it is rendered).
     */
    private boolean visible = true;

    /**
     * Is the component read-only ?
     */
    private boolean readOnly = false;


    /**
     * The container this component resides in.
     */
    
    @OneToOne
    private AbstractComponent parent = null;
    
    
    private float wdth = SIZE_UNDEFINED;
    private float height = SIZE_UNDEFINED;
    private int widthUnit = UNITS_PIXELS;
    private int heightUnit = UNITS_PIXELS;    

    /**
     * Immediate mode: if true, all variable changes are required to be sent
     * from the terminal immediately.
     */
    private boolean immediate = false;

	public AbstractComponent(String typeId) {
		super();
		this.typeId = typeId;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public AbstractComponent getParent() {
		return parent;
	}

	public void setParent(AbstractComponent parent) {
		this.parent = parent;
	}

	public float getWidth() {
		return wdth;
	}

	public void setWidth(float width) {
		this.wdth = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getWidthUnit() {
		return widthUnit;
	}

	public void setWidthUnit(int widthUnit) {
		this.widthUnit = widthUnit;
	}

	public int getHeightUnit() {
		return heightUnit;
	}

	public void setHeightUnit(int heightUnit) {
		this.heightUnit = heightUnit;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}


	@Override
	public int getWidthUnits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWidthUnits(int units) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHeightUnits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHeightUnits(int units) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeight(String height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWidth(float width, int unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeight(float height, int unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWidth(String width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSizeFull() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSizeUndefined() {
		// TODO Auto-generated method stub
		
	}
	
	public Set<String> getVisiblePropertyIds()
	{
		DataSource ds = getDataSource();
		Collection<String> visiblePropertyids = ds.getVisibleFieldNames();
		return new HashSet<String>(visiblePropertyids);
	}	
}

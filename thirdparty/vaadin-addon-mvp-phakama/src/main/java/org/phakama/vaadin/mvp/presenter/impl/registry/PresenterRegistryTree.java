package org.phakama.vaadin.mvp.presenter.impl.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.phakama.vaadin.mvp.presenter.IPresenter;
import org.phakama.vaadin.mvp.view.IView;

class PresenterRegistryTree {
	private Map<IPresenter<? extends IView>, PresenterRegistryNode> nodeCache = new HashMap<IPresenter<? extends IView>, PresenterRegistryNode>();

	void add(IPresenter<? extends IView> presenter, IPresenter<? extends IView> parent) {
		PresenterRegistryNode node = this.nodeCache.get(presenter);
		if (node == null) {
			node = new PresenterRegistryNode(presenter);
			this.nodeCache.put(presenter, node);
			if (parent != null) {
				PresenterRegistryNode parentNode = this.nodeCache.get(parent);
				if (parentNode == null) {
					parentNode = new PresenterRegistryNode(parent);
					this.nodeCache.put(parent, parentNode);
				}
				parentNode.addChild(node);
			}
		}
	}
	
	void remove(IPresenter<? extends IView> presenter) {
		PresenterRegistryNode node = this.nodeCache.get(presenter);
		if (node == null) {
			return;
		} else {
			// Orphan the children, disown the parent
			node.kill();
			this.nodeCache.remove(presenter);
		}
	}
	
	Collection<IPresenter<? extends IView>> siblingsOf(IPresenter<? extends IView> presenter) {
		PresenterRegistryNode node = this.nodeCache.get(presenter);
		if (node == null) {
			return null;
		} else {
			if (node.getParent() == null) {
				return null;
			} else {
				Collection<IPresenter<? extends IView>> siblings = toPresenterCollection(node.getParent().getChildren());
				siblings.remove(presenter);
				return siblings;
			}
		}
	}
	
	Collection<IPresenter<? extends IView>> childrenOf(IPresenter<? extends IView> presenter) {
		PresenterRegistryNode node = this.nodeCache.get(presenter);
		if (node == null) {
			return null;
		} else {
			return toPresenterCollection(node.getChildren());
		}
	}
	
	IPresenter<? extends IView> parentOf(IPresenter<? extends IView> presenter) {
		PresenterRegistryNode node = this.nodeCache.get(presenter);
		if (node == null) {
			return null;
		} else {
			if (node.getParent() == null) {
				return null;
			} else {
				return node.getParent().getData();
			}
		}
	}
	
	private Collection<IPresenter<? extends IView>> toPresenterCollection(Collection<PresenterRegistryNode> nodes) {
		ArrayList<IPresenter<? extends IView>> presenters = new ArrayList<IPresenter<? extends IView>>();
		for (PresenterRegistryNode node : nodes) {
			presenters.add(node.getData());
		}
		return presenters;
	}
}

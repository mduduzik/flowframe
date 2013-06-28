package org.phakama.vaadin.mvp.presenter.impl.registry;

import java.util.ArrayList;
import java.util.Collection;

import org.phakama.vaadin.mvp.presenter.IPresenter;
import org.phakama.vaadin.mvp.view.IView;

class PresenterRegistryNode {
	private IPresenter<? extends IView> data;

	private PresenterRegistryNode parent = null;
	private ArrayList<PresenterRegistryNode> children = null;

	PresenterRegistryNode(IPresenter<? extends IView> data) {
		this.data = data;
	}

	void setParent(PresenterRegistryNode parent) {
		this.parent = parent;
	}

	PresenterRegistryNode getParent() {
		return this.parent;
	}

	void setData(IPresenter<? extends IView> data) {
		this.data = data;
	}

	IPresenter<? extends IView> getData() {
		return this.data;
	}

	Collection<PresenterRegistryNode> getChildren() {
		return this.children;
	}

	void addChild(PresenterRegistryNode child) {
		if (this.children == null) {
			this.children = new ArrayList<PresenterRegistryNode>();
		}

		if (!this.children.contains(child)) {
			child.setParent(this);
			this.children.add(child);
		}
	}

	void removeChild(PresenterRegistryNode child) {
		if (this.children != null) {
			this.children.remove(child);
			child.setParent(null);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof PresenterRegistryNode))
			return false;
		return ((PresenterRegistryNode) obj).getData() == this.data;
	}

	@Override
	public int hashCode() {
		if (data == null) {
			return super.hashCode();
		} else {
			return 29 + (31 * this.data.hashCode());
		}
	}

	void kill() {
		if (this.parent != null) {
			this.parent.removeChild(this);
		}

		if (this.children != null) {
			for (PresenterRegistryNode child : this.children) {
				if (child != null) {
					child.setParent(null);
				}
			}
			
			this.children.clear();
			this.children = null;
		}

		this.data = null;
		this.parent = null;

		try {
			finalize();
		} catch (Throwable e) {
		}
	}
}

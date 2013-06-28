package org.phakama.vaadin.mvp.view;

import java.io.Serializable;

/**
 * A utility interface employed by the {@link IViewRegistry} to prioritize a
 * particular view implementation, in a collection of view implementations, to
 * return in the {@link IViewRegistry#find(Class, IViewPrioritizer)} method.
 * 
 * @author Sandile
 * 
 */
public interface IViewPrioritizer extends Serializable {
	/**
	 * This method determines whether the <code>first</code> parameter is more
	 * preferred than the <code>second</code> parameter. <code>true</code>
	 * should be returned if <code>first</code> is greater than
	 * <code>second</code>. Both <code>first</code> and <code>second</code> will
	 * implement the same {@link IView} interface.
	 * 
	 * @param first
	 *            the point of comparison
	 * @param second
	 *            what we're comparing <code>first</code> to
	 * @return whether <code>first</code> is more preferred than
	 *         <code>second</code>
	 */
	public boolean prioritize(Class<? extends IView> first, Class<? extends IView> second);
}

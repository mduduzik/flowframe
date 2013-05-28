package org.flowframe.ui.component.domain.custom.provider;

import org.flowframe.ui.component.domain.custom.provider.factory.ICustomContentPresenterFactory;
import org.flowframe.ui.component.domain.custom.provider.presenter.ICustomContentPresenter;

public interface ICustomContentProvider {
	/**
	 * Provides a hard-coded content presenter which is rendered according to
	 * the employed user interface library.
	 * 
	 * @param presenterFactory 
	 * 		The UI framework specific implementation of an MVP presenter factory
	 * @return
	 * 		The custom content presenter
	 */
	public ICustomContentPresenter provideContentPresenter(ICustomContentPresenterFactory presenterFactory);
}

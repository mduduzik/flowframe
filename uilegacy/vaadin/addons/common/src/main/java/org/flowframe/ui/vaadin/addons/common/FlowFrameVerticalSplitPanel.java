package org.flowframe.ui.vaadin.addons.common;

import org.flowframe.ui.vaadin.addons.common.gwt.client.ui.VFlowFrameSplitPanelVertical;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.ClientWidget.LoadStyle;

/**
 * A vertical split panel contains two components and lays them vertically. The
 * first component is above the second component.
 * 
 * <pre>
 *      +--------------------------+
 *      |                          |
 *      |  The first component     |
 *      |                          |
 *      +==========================+  <-- splitter
 *      |                          |
 *      |  The second component    |
 *      |                          |
 *      +--------------------------+
 * </pre>
 * 
 */
@SuppressWarnings("serial")
@ClientWidget(value = VFlowFrameSplitPanelVertical.class, loadStyle = LoadStyle.EAGER)
public class FlowFrameVerticalSplitPanel extends FlowFrameAbstractSplitPanel {

	public FlowFrameVerticalSplitPanel() {
		super();
        setSizeFull();
	}
}

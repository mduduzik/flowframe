package org.flowframe.ui.vaadin.addons.common.gwt.client.ui;

import com.vaadin.terminal.gwt.client.ui.VOverlay;

public class VFlowFrameMenuOverlay extends VOverlay {
	public static int Z_INDEX = 20000;
	
	public VFlowFrameMenuOverlay() {
		super();
		setShadowEnabled(false);
	}
	
	public VFlowFrameMenuOverlay(boolean autoHide) {
        super(autoHide);
        setShadowEnabled(false);
    }

    public VFlowFrameMenuOverlay(boolean autoHide, boolean modal) {
        super(autoHide, modal);
        setShadowEnabled(false);
    }

    public VFlowFrameMenuOverlay(boolean autoHide, boolean modal, boolean showShadow) {
        super(autoHide, modal, false);
    }
    
    protected void updateMenuOverlayShadowSizeAndPosition() {
    	updateShadowSizeAndPosition();
    }
}

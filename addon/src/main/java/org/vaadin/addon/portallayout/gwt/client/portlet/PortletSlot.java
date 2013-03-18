package org.vaadin.addon.portallayout.gwt.client.portlet;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PortletSlot extends SimplePanel {

    public PortletSlot(PortletChrome portlet) {
        setStyleName("v-portlet-slot");
        setWidget(portlet);
    }
    
    @Override
    public PortletChrome getWidget() {
        return (PortletChrome)super.getWidget();
    }
    
    @Override
    public void setWidget(Widget w) {
        super.setWidget(w);
        removeStyleName("v-portallayout-positioner");
    }
    

    @Override
    public void setHeight(String height) {
        super.setHeight(height);
    }
   
}
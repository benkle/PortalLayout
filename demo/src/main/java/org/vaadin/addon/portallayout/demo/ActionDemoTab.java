package org.vaadin.addon.portallayout.demo;

import org.vaadin.addon.portallayout.container.PortalColumns;
import org.vaadin.addon.portallayout.demo.DemoTable.NameType;
import org.vaadin.addon.portallayout.portal.AbsolutePortalLayout;
import org.vaadin.addon.portallayout.portal.PortalBase;
import org.vaadin.addon.portallayout.portal.StackPortalLayout;
import org.vaadin.addon.portallayout.portlet.Portlet;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class ActionDemoTab extends Panel /*implements PortletCloseListener, PortletCollapseListener*/ {
   
    public class DemoPortal extends StackPortalLayout {
        
        public DemoPortal() {
            setWidth("100%");
            setHeight("100%");
            //addCloseListener(ActionDemoTab.this);
            //addCollapseListener(ActionDemoTab.this);
            setMargin(true);
        }
    }
    
    private final PortalBase videoPortal = new DemoPortal() {
        @Override
        public Portlet portletFor(Component c/*, int position*/) {
            //clearPortletStyleNames(c);
            //addPortletStyleName(c, "red");
            c.setHeight("300px");
            Portlet p = super.portletFor(c/*, position*/);
            return p;
        }
    };
    
    private final PortalBase imagePortal = new AbsolutePortalLayout() {
        @Override
        public Portlet portletFor(Component c/*, int position*/) {
            setWidth("100%");
            setHeight("800px");
            //clearPortletStyleNames(c);
            //addPortletStyleName(c, "green");
            c.setHeight("30%");
            Portlet p = super.portletFor(c/*, position*/);
            //p.setCaption("Test Image");
            //p.setPreferredContentWidth("200px");
            return p;
        };
    };
    
    private final PortalBase miscPortal = new DemoPortal()  {
        @Override
        public Portlet portletFor(Component c/*, int position*/) {
            
            //c.setHeight("300px");
            Portlet p = super.portletFor(c/*, position*/);
            //p.setLocked(true);
            return p;
            //clearPortletStyleNames(c);
            //addPortletStyleName(c, "yellow");
        };
    };

    private boolean init = false;
    
    private final PortalColumns layout = new PortalColumns();
    
    public ActionDemoTab() {
        super();
        setSizeFull();
        setContent(layout);
        layout.setWidth("100%");
        //layout.setMargin(true);
        //layout.setSpacing(true);
        buildPortals();
        construct();
    }

    private void buildPortals() {
        ((StackPortalLayout)videoPortal).setSpacing(false);
        ((StackPortalLayout)miscPortal).setSpacing(true);
        layout.appendPortal(videoPortal);
        layout.appendPortal(imagePortal);
        layout.appendPortal(miscPortal);
    }
    

    public void construct() {
        if (init)
            return;
        init = true;
        createVideoContents();
        createImageContents();
        createMiscContents();
    }
    
    private void createMiscContents() {
        final DemoTable table = new DemoTable();
        Portlet portlet = miscPortal.portletFor(table);
        table.setCaption("Artists");
        //table.setIcon(new ThemeResource("chart.png"));
        
        final HorizontalLayout header =  new HorizontalLayout();
        final TextField filterField = new TextField();
        final NativeSelect filterType = new NativeSelect();
        final Label caption = new Label("Filter: ");
        for (final NameType t : NameType.values()) {
            filterType.addItem(t);
        }
        filterType.setValue(NameType.NT_FIRST_NAME);
        caption.addStyleName("v-white-text");
        filterField.setImmediate(true);
        header.setSizeUndefined();
        header.addComponent(caption);
        header.addComponent(filterField);
        header.addComponent(filterType);
        header.setSpacing(true);
        header.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);
        header.setComponentAlignment(filterType, Alignment.MIDDLE_LEFT);
        portlet.setHeaderComponent(header);
        filterField.addListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                table.filter((NameType)filterType.getValue(), filterField.getValue());
            }
        });
    }

    private void createImageContents() {
        final PortalImage image = new PortalImage();
        Portlet portlet = imagePortal.portletFor(image);
        portlet.setClosable(false);
        /*final RatingStars rating = new RatingStars();
        rating.setImmediate(true);
        rating.addListener(new ValueChangeListener() {            
            @Override
            public void valueChange(ValueChangeEvent event) {
                if (rating.getValue() != null) {
                    image.setRating((Double)rating.getValue());
                }
            }
        });*/
        //imagePortal.setHeaderComponent(image, rating);
        /*imagePortal.addAction(image, new ToolbarAction(new ThemeResource("arrow_right.png")) {
            @Override
            public void execute(final Context context) {
                if (!image.isEmpty()) {
                    image.showNextFile();
                    final Component header = context.getPortal().getHeaderComponent(image);
                    if (header instanceof Field) {
                        ((Field) header).setValue(image.getRating());
                    }
                }
            }
        });
        imagePortal.addAction(image, new ToolbarAction(new ThemeResource("arrow_left.png")) {
            @Override
            public void execute(final Context context) {
                if (!image.isEmpty()) {
                    image.showPrevFile();
                    final Component header = context.getPortal().getHeaderComponent(image);
                    if (header instanceof Field) {
                        ((Field) header).setValue(image.getRating());
                    }
                }
            }
        });*/
    }
    
    private void createVideoContents() {
        
        Embedded pl = new Embedded(null, new ExternalResource(
                "http://www.youtube.com/v/meXvxkn1Y_8&hl=en_US&fs=1&"));
        pl.setAlternateText("Vaadin Eclipse Quickstart video");
        pl.setMimeType("application/x-shockwave-flash");
        pl.setParameter("allowFullScreen", "true");
        pl.setHeight("100%");
        pl.setWidth("100%");
        pl.setImmediate(true);
        final TextArea tf = new TextArea();
        tf.setSizeFull();
        Portlet portlet = videoPortal.portletFor(tf);
        portlet.setCollapsible(false);
        pl.setCaption("Joy Division - Disorder");
        //pl.setIcon(new ThemeResource("video.png"));
        
        final HorizontalLayout header =  new HorizontalLayout();
        final TextField idField = new TextField();
        final Label caption = new Label("Enter video id: ");
        caption.addStyleName("v-white-text");
        idField.setImmediate(true);
        header.setSizeUndefined();
        header.addComponent(caption);
        header.addComponent(idField);
        header.setSpacing(true);
        header.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);
        
        idField.addListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                //pl.setVideoId(idField.getValue().toString());
            }
        });
        
        /*videoPortal.setHeaderComponent(pl, header);
        videoPortal.addAction(pl, new ToolbarAction(new ThemeResource("stop.png")) {
            @Override
            public void execute(final Context context) {
                pl.stop();
                final Notification n = new Notification("Stop! If didn't stop - DO NOT use YouTube add-on and FF!");
                n.setDelayMsec(1000);
                getWindow().showNotification(n);
            }
        });
        
        videoPortal.addAction(pl, new ToolbarAction(new ThemeResource("pause.png")) {
            @Override
            public void execute(final Context context) {
                pl.pause();
                final Notification n = new Notification("Pause! If didn't pause - DO NOT use YouTube add-on and FF!");
                n.setDelayMsec(1000);
                getWindow().showNotification(n);
            }
        });
        
        videoPortal.addAction(pl, new ToolbarAction(new ThemeResource("play.png")) {
            @Override
            public void execute(final Context context) {
                pl.requestRepaint();
                pl.play();
                final Notification n = new Notification("Play! If didn't start - DO NOT use YouTube add-on and FF!");
                n.setDelayMsec(1000);
                getWindow().showNotification(n);
            }
        });*/
    }


    /*@Override
    public void portletCollapseStateChanged(PortletCollapseEvent event) {
        final Context context = event.getPortlet();
        getWindow().showNotification(context.getComponent().getCaption() + "collapsed " + 
                context.getPortal().isCollapsed(context.getComponent()));
    }

    @Override
    public void portletClosed(PortletClosedEvent event) {
        getWindow().showNotification(event.getPortlet().getComponent().getCaption() + "closed");
    }*/

}

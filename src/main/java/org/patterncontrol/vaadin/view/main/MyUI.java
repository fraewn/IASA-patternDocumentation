package org.patterncontrol.vaadin.view.main;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.patterncontrol.vaadin.view.components.*;
import org.patterncontrol.vaadin.view.util.Views;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Title("PatternControl")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Navigator nav = new Navigator(this, this);
        nav.addView(Views.LOGINVIEW, LoginView.class);
        nav.addView(Views.FILETOPATTERNVIEW, FilesView.class);
        nav.addView(Views.PATTERNTOCOMPONENTVIEW, PatternView.class);
        nav.addView(Views.COMPONENTVIEW, ComponentView.class);
        nav.addView(Views.CONFIRMVIEW, ConfirmView.class);
        UI.getCurrent().getNavigator().navigateTo(Views.LOGINVIEW);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
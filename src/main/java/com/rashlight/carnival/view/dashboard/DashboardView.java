package com.rashlight.carnival.view.dashboard;


import com.rashlight.carnival.entity.User;
import com.rashlight.carnival.value.CarnivalToolbox;
import com.rashlight.carnival.view.main.MainView;

import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "dashboard", layout = MainView.class)
@ViewController("DashboardView")
@ViewDescriptor("dashboard-view.xml")
public class DashboardView extends StandardView {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private NativeLabel infoText;
    @ViewComponent
    private NativeLabel helloText;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);
        helloText.setText(messageBundle.getMessage("nativeLabel.text.prefix") + " " + user.getFirstName() + " " + user.getLastName());
        infoText.setText(messageBundle.getMessage("infoText.text.prefix") + " " + user.getPoints());
    }
}

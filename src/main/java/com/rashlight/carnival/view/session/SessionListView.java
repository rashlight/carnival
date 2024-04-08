package com.rashlight.carnival.view.session;

import com.rashlight.carnival.entity.Session;
import com.rashlight.carnival.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;

@Route(value = "sessions", layout = MainView.class)
@ViewController("Session.list")
@ViewDescriptor("session-list-view.xml")
@LookupComponent("sessionsDataGrid")
@DialogMode(width = "64em")
public class SessionListView extends StandardListView<Session> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Session> sessionsDc;

    @ViewComponent
    private InstanceContainer<Session> sessionDc;

    @ViewComponent
    private InstanceLoader<Session> sessionDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        updateControls(false);
    }

    @Subscribe("sessionsDataGrid.create")
    public void onSessionsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Session entity = dataContext.create(Session.class);
        sessionDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("sessionsDataGrid.edit")
    public void onSessionsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveBtn")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.save();
        sessionsDc.replaceItem(sessionDc.getItem());
        updateControls(false);
    }

    @Subscribe("cancelBtn")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        sessionDl.load();
        updateControls(false);
    }

    @Subscribe(id = "sessionsDc", target = Target.DATA_CONTAINER)
    public void onSessionsDcItemChange(final InstanceContainer.ItemChangeEvent<Session> event) {
        Session entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            sessionDl.setEntityId(entity.getId());
            sessionDl.load();
        } else {
            sessionDl.setEntityId(null);
            sessionDc.setItem(null);
        }
    }

    private void updateControls(boolean editing) {
        form.getChildren().forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });

        detailActions.setVisible(editing);
        listLayout.setEnabled(!editing);
    }
}
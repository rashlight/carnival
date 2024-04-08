package com.rashlight.carnival.view.session;

import com.rashlight.carnival.entity.*;
import com.rashlight.carnival.value.CarnivalToolbox;
import com.rashlight.carnival.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.validation.EntityValidationException;
import io.jmix.flowui.action.view.LookupDiscardAction;
import io.jmix.flowui.component.datetimepicker.TypedDateTimePicker;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private VerticalLayout listLayout;
    @ViewComponent
    private NativeLabel noResultLabel;
    @ViewComponent
    private DataGrid<GuessNumResult> guessNumResultDataGrid;
    @ViewComponent
    private DataGrid<CrashMulResult> crashMulResultDataGrid;
    @ViewComponent
    private DataGrid<FighterResult> fighterResultDataGrid;
    @ViewComponent
    private CollectionLoader<GuessNumResult> guessNumResultDl;
    @ViewComponent
    private CollectionLoader<FighterResult> fighterResultDl;
    @ViewComponent
    private CollectionLoader<CrashMulResult> crashMulResultDl;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private CollectionLoader<Session> sessionsDl;
    @ViewComponent
    private LookupDiscardAction<Object> discardAction;

    @Subscribe
    public void onInit(final InitEvent event) {
        sessionsDl.load();
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
    }

    @Subscribe(id = "sessionsDc", target = Target.DATA_CONTAINER)
    public void onSessionsDcItemChange(final InstanceContainer.ItemChangeEvent<Session> event) {
        Session entity = event.getItem();
        dataContext.clear();
        if (entity == null) {
            noResultLabel.setVisible(true);
            guessNumResultDataGrid.setVisible(false);
            crashMulResultDataGrid.setVisible(false);
            fighterResultDataGrid.setVisible(false);
        } else {
            switch (entity.getGameType()) {
                case GUESSNUM:
                    noResultLabel.setVisible(false);
                    guessNumResultDataGrid.setVisible(true);
                    crashMulResultDataGrid.setVisible(false);
                    fighterResultDataGrid.setVisible(false);

                    guessNumResultDl.setParameter("matchId", entity.getMatchId());
                    guessNumResultDl.load();
                    break;
                case CRASHMUL:
                    noResultLabel.setVisible(false);
                    guessNumResultDataGrid.setVisible(false);
                    crashMulResultDataGrid.setVisible(true);
                    fighterResultDataGrid.setVisible(false);

                    crashMulResultDl.setParameter("matchId", entity.getMatchId());
                    crashMulResultDl.load();
                    break;
                case FIGHTER:
                    noResultLabel.setVisible(false);
                    guessNumResultDataGrid.setVisible(false);
                    crashMulResultDataGrid.setVisible(false);
                    fighterResultDataGrid.setVisible(true);

                    fighterResultDl.setParameter("matchId", entity.getMatchId());
                    fighterResultDl.load();
                    break;
                default:
                    throw new InvalidParameterException("Unknown GameType for User!");
            }
        }
    }
}
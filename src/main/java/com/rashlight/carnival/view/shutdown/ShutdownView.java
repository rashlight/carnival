package com.rashlight.carnival.view.shutdown;


import com.rashlight.carnival.CarnivalApplication;
import com.rashlight.carnival.entity.*;
import com.rashlight.carnival.value.CarnivalToolbox;
import com.rashlight.carnival.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

@Route(value = "shutdown", layout = MainView.class)
@ViewController("ShutdownView")
@ViewDescriptor("shutdown-view.xml")
public class ShutdownView extends StandardView implements SessionResultUpdate {

    private ShutdownState shutdownState;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private JmixButton shutdownButton;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private ApplicationContext applicationContext;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        shutdownState = new ShutdownState();
        if (CarnivalToolbox.getLoggedInUser(currentAuthentication).getPoints() >= CarnivalToolbox.SHUTDOWN_POINTS) {
            shutdownButton.setEnabled(true);
            shutdownState.setMatchId(UUID.randomUUID());
            shutdownState.setPointsGiven(1000000L);
            shutdownState.setMultiplier(-1.0d);
        }
    }
    
    @Autowired
    private DataManager dataManager;

    @Subscribe(id = "shutdownButton", subject = "clickListener")
    public void onShutdownButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createOptionDialog()
                .withHeader("Shutdown Carnival")
                .withText("Are you sure you want to shutdown Carnival? Your name will be added in the History tab.")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> {
                                    User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);
                                    user.setPoints(user.getPoints() - shutdownState.getPointsGiven());
                                    dataManager.save(user);
                                    updateResult();
                                    updateSession();

                                    // Say goodbye here
                                    SpringApplication.exit(applicationContext, () -> 1);
                                }),
                        new DialogAction(DialogAction.Type.NO))
                .open();
    }

    @Override
    public void updateSession() {
        Session session = dataManager.create(Session.class);
        session.setGameType(GameType.SHENANIGANS);
        session.setMatchId(shutdownState.getMatchId());
        session.setUser(CarnivalToolbox.getLoggedInUser(currentAuthentication));
        session.setTime(LocalDateTime.now());
        session.setPointsChange(CarnivalToolbox.floorLongFromDouble(shutdownState.getPointsGiven() * shutdownState.getMultiplier()));
        dataManager.save(session);
    }

    @Override
    public void updateResult() {
        ShutdownResult result = dataManager.create(ShutdownResult.class);
        result.setMatchId(shutdownState.getMatchId());
        result.setUser(CarnivalToolbox.getLoggedInUser(currentAuthentication));
        result.setTime(LocalDateTime.now());
        result.setPointsGiven(CarnivalToolbox.SHUTDOWN_POINTS);
        result.setMultiplier(shutdownState.getMultiplier());
        dataManager.save(result);
    }
}
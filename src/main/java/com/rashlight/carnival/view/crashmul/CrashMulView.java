package com.rashlight.carnival.view.crashmul;


import com.rashlight.carnival.communication.grpc.CrashMulServiceClient;
import com.rashlight.carnival.communication.grpc.CrashMulTask;
import com.rashlight.carnival.entity.*;
import com.rashlight.carnival.value.CarnivalToolbox;
import com.rashlight.carnival.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.BackgroundTaskHandler;
import io.jmix.flowui.backgroundtask.BackgroundWorker;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

@Route(value = "crash-mul", layout = MainView.class)
@ViewController("CrashMulView")
@ViewDescriptor("crash-mul-view.xml")
public class CrashMulView extends StandardView implements SessionResultUpdate {
    public CrashMulState crashMulState;
    @ViewComponent
    private JmixTextArea crashMulRuleText;
    @Autowired
    private MessageBundle messageBundle;
    @ViewComponent
    private JmixButton crashMulReadyButton;
    @ViewComponent
    private JmixButton crashMulStopButton;
    @Autowired
    private BackgroundWorker backgroundWorker;
    @ViewComponent
    private VerticalLayout postStartBox;
    @ViewComponent
    private NativeLabel crashMulMatchLabel;
    @Autowired
    private CrashMulServiceClient crashMulServiceClient;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private TypedTextField<Object> crashMulPointsInput;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;
    @ViewComponent
    private VerticalLayout preStartBox;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private ApplicationContext applicationContext;

    private long getDeltaPoints() {
        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);

        if (CarnivalToolbox.isNullOrEmpty(crashMulPointsInput.getValue()))
            throw new NullPointerException(CarnivalToolbox.getError(messages, "noPointsValue"));

        return user.getPoints() - Long.parseLong(crashMulPointsInput.getValue());
    }

    private void changeVisualMode(GuessNumMode mode) {
        switch (mode) {
            case GuessNumMode.PRESTART:
                postStartBox.setEnabled(false);
                postStartBox.setVisible(false);
                preStartBox.setEnabled(true);
                preStartBox.setVisible(true);
                break;
            case GuessNumMode.POSTSTART:
                preStartBox.setEnabled(false);
                preStartBox.setVisible(false);
                postStartBox.setEnabled(true);
                postStartBox.setVisible(true);
                break;
        }
    }

    private void reward() {
        int reward = (int)Math.floor(crashMulState.getPointsGiven() * crashMulState.getMultiplier());
        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);
        user.setPoints(user.getPoints() + reward);
        dataManager.save(user);

        if (crashMulState.getMultiplier() <= 1.0d || crashMulState.getFinalMultiplier() <= 1.0d) {
            notifications.show(
                    messageBundle.getMessage("winner-semi"),
                    messageBundle.formatMessage(
                            "points-earned",
                            reward,
                            String.format("(%.2fx)", crashMulState.getMultiplier()),
                            String.format("(%.2fx)", crashMulState.getFinalMultiplier())
                    )
            );
        } else {
            notifications.show(
                    messageBundle.getMessage("winner"),
                    messageBundle.formatMessage(
                            "points-earned",
                            reward,
                            String.format("(%.2fx)", crashMulState.getMultiplier()),
                            String.format("(%.2fx)", crashMulState.getFinalMultiplier())
                    )
            );
        }

        changeVisualMode(GuessNumMode.PRESTART);
    }

    private void punish() {
        notifications.show(
                messageBundle.getMessage("gameOver"),
                messageBundle.formatMessage("lostReward", crashMulState.getPointsGiven().toString())
        );
        changeVisualMode(GuessNumMode.PRESTART);
    }

    public void handleFinalize(Double result) {
        crashMulState.setFinalMultiplier(result);
        crashMulState.setIsPlayerStop(false);

        if (crashMulState.getMultiplier() <= 0d) {
            punish();
        } else {
            reward();
        }

        crashMulState.setIsPlayerStop(false);
        updateResult();
        updateSession();
    }

    public void handleMultiplierUpdate(Double value) {
        crashMulMatchLabel.setText(value + "x");
    }

    public boolean handleTaskTimeout() {
        changeVisualMode(GuessNumMode.PRESTART);
        throw new ValidationException("CrashMulTask timeout is too long");
    }

    public boolean handleUnhandledException(Exception ex) {
        throw new ValidationException(ex);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        crashMulState = new CrashMulState();
        crashMulRuleText.setValue(
                messageBundle.getMessage("crashMulRule.text")
        );
        crashMulPointsInput.focus();
    }

    private void processCrashMulStartButtonClick() {
        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);

        if (CarnivalToolbox.isNullOrEmpty(crashMulPointsInput.getValue())) {
            notifications.create(
                    CarnivalToolbox.getError(messages, "invalidPointsTitle"),
                    CarnivalToolbox.getError(messages, "invalidPointsMessage")
            ).withThemeVariant(NotificationVariant.LUMO_WARNING).show();
            return;
        }

        if (getDeltaPoints() < 0) {
            notifications.create(
                    CarnivalToolbox.getError(messages, "insuficientPointsTitle"),
                    CarnivalToolbox.getError(messages, "insuficientPointsMessage")
            ).withThemeVariant(NotificationVariant.LUMO_WARNING).show();
        } else {
            long pointsGiven = Long.parseLong(crashMulPointsInput.getValue());
            user.setPoints(user.getPoints() - pointsGiven);
            dataManager.save(user);

            crashMulReadyButton.setVisible(true);
            crashMulStopButton.setVisible(false);
            crashMulStopButton.setEnabled(true);

            crashMulState.setMatchId(UUID.randomUUID());
            crashMulState.setPointsGiven(pointsGiven);
            crashMulState.setFinalMultiplier(0.5d);
            crashMulState.setMultiplier(-1.0d);
            crashMulMatchLabel.setText(messageBundle.getMessage("crashMulMatchLabel.startup"));
            changeVisualMode(GuessNumMode.POSTSTART);
            notifications.create(
                    messageBundle.getMessage("gameStart"),
                    messageBundle.getMessage("glhf")
            ).withPosition(Notification.Position.BOTTOM_END).show();
        }
    }

    // This is the button for starting the game, not for the game start
    @Subscribe(id = "crashMulStartButton", subject = "clickListener")
    public void onCrashMulStartButtonClick(final ClickEvent<JmixButton> event) {
        processCrashMulStartButtonClick();
    }

    @Subscribe(id = "crashMulReadyButton", subject = "clickListener")
    public void onCrashMulReadyButtonClick(final ClickEvent<JmixButton> event) {
        crashMulReadyButton.setVisible(false);
        crashMulStopButton.setVisible(true);
        crashMulStopButton.setEnabled(true);

        BackgroundTask<Double, Double> task = new CrashMulTask(this.crashMulServiceClient, this);
        BackgroundTaskHandler<Double> taskHandler = backgroundWorker.handle(task);
        taskHandler.execute();
    }

    @Subscribe(id = "crashMulStopButton", subject = "clickListener")
    public void onCrashMulStopButtonClick(final ClickEvent<JmixButton> event) {
        Double value = crashMulServiceClient.getCurrentMultiplier();
        crashMulState.setMultiplier(value);
        crashMulState.setFinalMultiplier(value);
        crashMulState.setIsPlayerStop(true);
        updateResult();
        crashMulStopButton.setEnabled(false);
    }

    @Override
    public void updateSession() {
        Session session = dataManager.create(Session.class);
        session.setGameType(GameType.CRASHMUL);
        session.setMatchId(crashMulState.getMatchId());
        session.setUser(CarnivalToolbox.getLoggedInUser(currentAuthentication));
        session.setTime(LocalDateTime.now());
        session.setPointsChange(CarnivalToolbox.floorLongFromDouble(crashMulState.getPointsGiven() * crashMulState.getMultiplier()));
        dataManager.save(session);
    }

    @Override
    public void updateResult() {
        CrashMulResult crashMulResult = dataManager.create(CrashMulResult.class);
        crashMulResult.setMatchId(crashMulState.getMatchId());
        crashMulResult.setUser(CarnivalToolbox.getLoggedInUser(currentAuthentication));
        crashMulResult.setTime(LocalDateTime.now());
        crashMulResult.setPointsGiven(crashMulState.getPointsGiven());
        crashMulResult.setIsPlayerStop(crashMulState.getIsPlayerStop());
        crashMulResult.setFinalMultiplier(crashMulState.getFinalMultiplier());
        crashMulResult.setMultiplier(crashMulState.getMultiplier());
        dataManager.save(crashMulResult);
    }
}
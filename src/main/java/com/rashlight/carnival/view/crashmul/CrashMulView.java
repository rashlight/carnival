package com.rashlight.carnival.view.crashmul;


import com.rashlight.carnival.communication.grpc.CrashMulServiceClient;
import com.rashlight.carnival.communication.grpc.CrashMulTask;
import com.rashlight.carnival.entity.CrashMulState;
import com.rashlight.carnival.entity.GuessNumMode;
import com.rashlight.carnival.entity.User;
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
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Route(value = "crash-mul", layout = MainView.class)
@ViewController("CrashMulView")
@ViewDescriptor("crash-mul-view.xml")
public class CrashMulView extends StandardView {
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
    private CrashMulState crashMulState;
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

            crashMulState.setMatchId(UUID.randomUUID());
            crashMulState.setPointsGiven(pointsGiven);
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
        crashMulReadyButton.setEnabled(false);
        crashMulReadyButton.setVisible(false);
        crashMulStopButton.setVisible(true);
        crashMulStopButton.setEnabled(true);

        BackgroundTask<Double, Double> task = new CrashMulTask();
        BackgroundTaskHandler<Double> taskHandler = backgroundWorker.handle(task);
        taskHandler.execute();
    }

    @Subscribe(id = "crashMulStopButton", subject = "clickListener")
    public void onCrashMulStopButtonClick(final ClickEvent<JmixButton> event) {
        crashMulState.setPlayerMultiplier(crashMulServiceClient.getCurrentMultiplier());
        crashMulStopButton.setEnabled(false);
    }
}
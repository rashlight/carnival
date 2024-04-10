package com.rashlight.carnival.view.guessnum;

import com.rashlight.carnival.entity.*;
import com.rashlight.carnival.view.main.MainView;
import com.rashlight.carnival.value.CarnivalToolbox;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Route(value = "guess-num", layout = MainView.class)
@ViewController("GuessNumView")
@ViewDescriptor("guess-num-view.xml")
public class GuessNumView extends StandardView implements SessionResultUpdate {
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private VerticalLayout postStartBox;
    @ViewComponent
    private VerticalLayout preStartBox;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private TypedTextField<Object> guessNumPointsInput;
    @Autowired
    private Messages messages;
    @ViewComponent
    private JmixButton guessNumStartButton;
    private GuessNumState guessNumState;
    @ViewComponent
    private TypedTextField<Object> guessNumEnter;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private JmixTextArea guessNumRuleText;
    @ViewComponent
    private NativeLabel guessNumAttemptsLeft;
    @ViewComponent
    private NativeLabel guessNumJudgementLabel;

    private void setupNumbers() {
        Random rand = new Random();
        guessNumState.setActualNum(rand.nextInt(1, 101));
        guessNumState.setAttemptsLeft(5);
    }

    private void reward() {
        int reward = (int)Math.floor(guessNumState.getPointsGiven() * guessNumState.getMultiplier());
        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);
        user.setPoints(user.getPoints() + reward);
        dataManager.save(user);
        notifications.show(
                messageBundle.getMessage("winner"),
                "You earned " + reward + String.format("(%.2fx)", guessNumState.getMultiplier())
        );
        updateSession();
        changeVisualMode(GuessNumMode.PRESTART);
    }

    private void punish() {
        if (guessNumState.getAttemptsLeft() <= 0) {
            guessNumState.setMultiplier(-1.0d);
            updateSession();
            notifications.show(
                    messageBundle.getMessage("gameOver"),
                    messageBundle.getMessage("actualNumDescribe") + " " + guessNumState.getActualNum()
            );
            changeVisualMode(GuessNumMode.PRESTART);
        }
    }

    private void judge(GuessNumJudgement guessNumJudgement) {
        switch (guessNumJudgement) {
            case EXACT:
                reward();
                return;
            case HIGHER:
                guessNumJudgementLabel.setText(messageBundle.getMessage("guessNumJudgementLabel.text.high"));
                punish();
                break;
            case LOWER:
                guessNumJudgementLabel.setText(messageBundle.getMessage("guessNumJudgementLabel.text.low"));
                punish();
                break;
            default:
                throw new IllegalStateException("Cannot judge GuessNum with value " + String.valueOf(guessNumJudgement));
        }
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

    private long getDeltaPoints() {
        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);
        if (CarnivalToolbox.isNullOrEmpty(guessNumPointsInput.getValue()))
            throw new NullPointerException(CarnivalToolbox.getError(messages, "noPointsValue"));

        return user.getPoints() - Long.parseLong(guessNumPointsInput.getValue());
    }

    private void processGuessNumButtonClick() {
        if (CarnivalToolbox.isNullOrEmpty(guessNumEnter.getValue())) {
            notifications.create(
                    CarnivalToolbox.getError(messages, "invalidGuessNumTitle"),
                    CarnivalToolbox.getError(messages, "invalidGuessNumMessage")
            ).withPosition(Notification.Position.BOTTOM_CENTER).withThemeVariant(NotificationVariant.LUMO_ERROR);
            return;
        }

        int guessNum = Integer.parseInt(guessNumEnter.getValue());
        guessNumState.setAttemptValue(guessNum);
        guessNumState.setAttemptsLeft(guessNumState.getAttemptsLeft() - 1);
        Double multiplier = BigDecimal.valueOf(5.0d - 0.5d * (5 - guessNumState.getAttemptsLeft()))
                .setScale(1, RoundingMode.HALF_EVEN)
                .doubleValue();
        guessNumState.setMultiplier(multiplier);

        guessNumAttemptsLeft.setText(guessNumState.getAttemptsLeft() + " " + messageBundle.getMessage("guessNumLeftLabel.text.postfix"));

        updateResult();
        if (guessNum == guessNumState.getActualNum()) {
            judge(GuessNumJudgement.EXACT);
        } else if (guessNum > guessNumState.getActualNum()) {
            judge(GuessNumJudgement.LOWER);
        } else {
            judge(GuessNumJudgement.HIGHER);
        }
    }

    private void processGuessNumStartButtonClick() {
        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);

        if (CarnivalToolbox.isNullOrEmpty(guessNumPointsInput.getValue())) {
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
            long pointsGiven = Long.parseLong(guessNumPointsInput.getValue());
            user.setPoints(user.getPoints() - pointsGiven);
            dataManager.save(user);

            guessNumState.setMatchId(UUID.randomUUID());
            guessNumState.setPointsGiven(pointsGiven);
            guessNumState.setMultiplier(CarnivalToolbox.DefaultMultiplier);

            setupNumbers();
            guessNumEnter.setValue("");
            guessNumJudgementLabel.setText(messageBundle.getMessage("guessNumJudgementLabel.text"));
            guessNumAttemptsLeft.setText(guessNumState.getAttemptsLeft() + " " + messageBundle.getMessage("guessNumLeftLabel.text.postfix"));
            changeVisualMode(GuessNumMode.POSTSTART);
            notifications.create(
                    messageBundle.getMessage("gameStart"),
                    messageBundle.getMessage("glhf")
            ).withPosition(Notification.Position.BOTTOM_END).show();
            guessNumEnter.focus();
        }
    }

    @Install(to = "guessNumEnter", subject = "validator")
    private void guessNumEnterValidator(final Object value) {
        if (CarnivalToolbox.isNullOrEmpty(guessNumEnter.getValue()) || Integer.parseInt(value.toString()) < 0 || Integer.parseInt(value.toString()) > 100) {
            throw new ValidationException(CarnivalToolbox.getError(messages, "noValue"));
        }
    }

    @Install(to = "guessNumPointsInput", subject = "validator")
    private void guessNumPointsInputValidator(final Object value) {
        if (guessNumPointsInput.isEmpty()) {
            guessNumStartButton.setEnabled(false);
            throw new ValidationException(CarnivalToolbox.getError(messages, "noValue"));
        }
        else {
            guessNumStartButton.setEnabled(true);
        }
    }

    @Subscribe
    public void onBeforeClose(final BeforeCloseEvent event) {
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        guessNumState = new GuessNumState();
        guessNumRuleText.setValue(
            messageBundle.getMessage("guessNumRule.text")
        );
        Shortcuts.addShortcutListener(
                this,
                this::processGuessNumStartButtonClick,
                Key.ENTER
        ).listenOn(guessNumPointsInput);
        Shortcuts.addShortcutListener(
                this,
                this::processGuessNumButtonClick,
                Key.ENTER
        ).listenOn(guessNumEnter);
        guessNumPointsInput.focus();
    }

    @Subscribe(id = "guessNumStartButton", subject = "clickListener")
    public void onGuessNumStartButtonClick(final ClickEvent<JmixButton> event) {
        processGuessNumStartButtonClick();
    }

    @Subscribe("guessNumPointsInput")
    public void onGuessNumPointsInputKeyPress(final KeyPressEvent event) {
        if (event.getKey() == Key.ENTER) {
            processGuessNumStartButtonClick();
        }
    }

    @Subscribe("guessNumEnter")
    public void onGuessNumEnterKeyPress(final KeyPressEvent event) {
        if (event.getKey() == Key.ENTER) {
            processGuessNumButtonClick();
        }
    }

    @Subscribe(id = "guessNumConfirmButton", subject = "clickListener")
    public void onGuessNumButtonClick(final ClickEvent<JmixButton> event) {
        processGuessNumButtonClick();
    }

    @Override
    public void updateSession() {
        Session session = dataManager.create(Session.class);
        session.setGameType(GameType.GUESSNUM);
        session.setMatchId(guessNumState.getMatchId());
        session.setUser(CarnivalToolbox.getLoggedInUser(currentAuthentication));
        session.setTime(LocalDateTime.now());
        session.setPointsChange(
                Double.valueOf(Math.floor(guessNumState.getPointsGiven() * guessNumState.getMultiplier())).longValue()
        );
        dataManager.save(session);
    }

    @Override
    public void updateResult() {
        GuessNumResult guessNumResult = dataManager.create(GuessNumResult.class);
        guessNumResult.setMatchId(guessNumState.getMatchId());
        guessNumResult.setUser(CarnivalToolbox.getLoggedInUser(currentAuthentication));
        guessNumResult.setTime(LocalDateTime.now());
        guessNumResult.setPointsGiven(guessNumState.getPointsGiven());
        guessNumResult.setAttempt(5 - guessNumState.getAttemptsLeft());
        guessNumResult.setUser(CarnivalToolbox.getLoggedInUser(currentAuthentication));
        guessNumResult.setAttemptValue(guessNumState.getAttemptValue());
        guessNumResult.setActualValue(guessNumState.getActualNum());
        guessNumResult.setMultiplier(guessNumState.getMultiplier());
        dataManager.save(guessNumResult);
    }
}
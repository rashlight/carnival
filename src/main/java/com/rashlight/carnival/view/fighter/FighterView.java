package com.rashlight.carnival.view.fighter;

import com.rashlight.carnival.entity.*;
import com.rashlight.carnival.value.CarnivalToolbox;
import com.rashlight.carnival.value.FighterAI;
import com.rashlight.carnival.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import groovy.lang.Tuple2;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Route(value = "fighter", layout = MainView.class)
@ViewController("FighterView")
@ViewDescriptor("fighter-view.xml")
public class FighterView extends StandardView implements SessionResultUpdate {
    @Autowired
    private Messages messages;
    @ViewComponent
    private JmixButton fighterStartButton;
    @ViewComponent
    private TypedTextField<Object> fighterPointsInput;
    @ViewComponent
    private JmixTextArea fighterRuleText;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private Notifications notifications;
    @Autowired
    private DataManager dataManager;
    private FighterState fighterState;
    @ViewComponent
    private VerticalLayout postStartBox;
    @ViewComponent
    private VerticalLayout preStartBox;
    @ViewComponent
    private JmixButton button9;
    @ViewComponent
    private JmixButton button8;
    @ViewComponent
    private JmixButton button7;
    @ViewComponent
    private JmixButton button6;
    @ViewComponent
    private JmixButton button5;
    @ViewComponent
    private JmixButton button4;
    @ViewComponent
    private JmixButton button3;
    @ViewComponent
    private JmixButton button2;
    @ViewComponent
    private JmixButton button13;
    @ViewComponent
    private JmixButton button12;
    @ViewComponent
    private JmixButton button11;
    @ViewComponent
    private JmixButton button10;
    @ViewComponent
    private JmixButton button1;
    @ViewComponent
    private JmixButton button0;
    private List<JmixButton> buttonList;
    @ViewComponent
    private NativeLabel fighterDescriptionMatchLabel;
    @ViewComponent
    private NativeLabel fighterTitleMatchLabel;
    @ViewComponent
    private JmixButton attackButton;
    @ViewComponent
    private JmixButton defendButton;
    @ViewComponent
    private JmixButton dashButton;
    @ViewComponent
    private JmixButton actionButton;
    @ViewComponent
    private NativeLabel fighterTitleLabel;
    @ViewComponent
    private JmixButton nextMatchButton;
    @ViewComponent
    private JmixButton endGameButton;
    private FighterAI fighterAI;

    private int move(int amount, FighterSide side) {
        return switch (side) {
            case LEFT -> amount;
            case RIGHT -> -amount;
        };
    }
    /**
     * Does the expected movement valid for action process?
     * @param friendlyPosition Player's position
     * @param enemyPosition AI's position
     * @return boolean
     */
    private boolean isOverlap(int friendlyPosition, int enemyPosition) {
        return switch (fighterState.getFriendlySide()) {
            case LEFT -> enemyPosition <= friendlyPosition;
            case RIGHT -> enemyPosition >= friendlyPosition;
        };
    }
    private int getMovementUnitFromAction(FighterAction fighterAction) {
        return switch (fighterAction) {
            case UNKNOWN -> 0;
            case DEFEND -> 1;
            case ATTACK -> 2;
            case DASH -> 3;
        };
    }
    private String getActionStringFromEnum(FighterAction fighterAction) {
        return switch (fighterAction) {
            case ATTACK -> "ATTACK";
            case DEFEND -> "DEFEND";
            case DASH -> "DASH";
            case UNKNOWN -> "UNKNOWN";
        };
    }
    private Button getButtonFromPosition(int position) {
        if (position < 0 || position > 13)
            throw new IndexOutOfBoundsException("Button position not supported: " + position);

        return buttonList.get(position);
    }
    private void setActionButtonsState(boolean enabled) {
        attackButton.setEnabled(enabled);
        defendButton.setEnabled(enabled);
        dashButton.setEnabled(enabled);
    }
    private void setFriendlyColor(Button component) {
        component.setClassName("fighter-friendly");
    }
    private void setEnemyColor(Button component) {
        component.setClassName("fighter-enemy");
    }
    private void updateFriendlyUI(int oldPosition, int newPosition) {
        int deltaUIPlacement = 2;

        setFriendlyColor(getButtonFromPosition(newPosition + deltaUIPlacement));
        getButtonFromPosition(newPosition + deltaUIPlacement).setIcon(new Icon(VaadinIcon.USER));
        getButtonFromPosition(newPosition + deltaUIPlacement).setClassName("fighter-friendly");
    }
    private void updateEnemyUI(int oldPosition, int newPosition) {
        int deltaUIPlacement = 2;

        setEnemyColor(getButtonFromPosition(newPosition + deltaUIPlacement));
        getButtonFromPosition(newPosition + deltaUIPlacement).setIcon(new Icon(VaadinIcon.USER));
        getButtonFromPosition(newPosition + deltaUIPlacement).setClassName("fighter-enemy");
    }
    private void updateActionButtonUI() {
        actionButton.setEnabled(false);
        switch (fighterState.getStatus()) {
            case PENDING:
                actionButton.setEnabled(true);
                actionButton.setVisible(true);
                nextMatchButton.setEnabled(false);
                nextMatchButton.setVisible(false);
                endGameButton.setVisible(false);
                endGameButton.setVisible(false);
                break;
            case WON, LOST:
                setActionButtonsState(false);
                if (fighterState.getEnemyMatchPoint() >= 3 || fighterState.getFriendlyMatchPoint() >= 3) {
                    actionButton.setEnabled(false);
                    actionButton.setVisible(false);
                    nextMatchButton.setEnabled(false);
                    nextMatchButton.setVisible(false);
                    endGameButton.setVisible(true);
                    endGameButton.setVisible(true);
                }
                else {
                    actionButton.setEnabled(false);
                    actionButton.setVisible(false);
                    nextMatchButton.setEnabled(true);
                    nextMatchButton.setVisible(true);
                    endGameButton.setVisible(false);
                    endGameButton.setVisible(false);
                }
                break;
        }
    }
    private void reward() {
        fighterState.setFriendlyMatchPoint(fighterState.getFriendlyMatchPoint() + 1);
    }
    private void finalizeReward() {
        fighterState.setMultiplier(3.0d);

        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);
        user.setPoints(
                user.getPoints() + CarnivalToolbox.floorLongFromDouble(
                        fighterState.getPointsGiven() * fighterState.getMultiplier()
                )
        );
        dataManager.save(user);
        updateSession();

        notifications.show(
                messageBundle.getMessage("winner"),
                "You earned " + fighterState.getPointsGiven() * 3 + " (300%)"
        );
    }
    private void finalizePunish() {
        notifications.show(
                messageBundle.getMessage("loser"),
                "You lost " + fighterState.getPointsGiven() + " (100%)"
        );
        updateSession();
    }
    private void punish() {
        fighterState.setEnemyMatchPoint(fighterState.getEnemyMatchPoint() + 1);
        updateResult();
    }
    private void updateTitleAndDescription(int deltaFriendlyPosition, int deltaEnemyPosition) {
        fighterTitleLabel.setText(fighterState.getFriendlyMatchPoint() + " - " + fighterState.getEnemyMatchPoint());
        String prefix = switch (fighterState.getStatus()) {
            case PENDING -> "";
            case WON -> messageBundle.getMessage("nativeLabel1.text.prefix.won");
            case LOST -> messageBundle.getMessage("nativeLabel1.text.prefix.lost");
        };

        String friendlyAction = getActionStringFromEnum(fighterState.getFriendlyAction());
        String enemyAction = getActionStringFromEnum(fighterState.getEnemyAction());

        fighterDescriptionMatchLabel.setText(
                prefix + " " + messageBundle.formatMessage("nativeLabel1.text.status", friendlyAction, enemyAction) + " (" + deltaFriendlyPosition + ", " + deltaEnemyPosition + ")");
    }
    private long getDeltaPoints() {
        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);

        if (CarnivalToolbox.isNullOrEmpty(fighterPointsInput.getValue()))
            throw new NullPointerException(CarnivalToolbox.getError(messages, "noPointsValue"));

        return user.getPoints() - Long.parseLong(fighterPointsInput.getValue());
    }
    private void changeVisualMode(FighterMode mode) {
        switch (mode) {
            case FighterMode.PRESTART:
                fighterTitleLabel.setText(
                        messageBundle.getMessage("startupTitle")
                );
                postStartBox.setEnabled(false);
                postStartBox.setVisible(false);
                preStartBox.setEnabled(true);
                preStartBox.setVisible(true);
                break;
            case FighterMode.POSTSTART:
                preStartBox.setEnabled(false);
                preStartBox.setVisible(false);
                postStartBox.setEnabled(true);
                postStartBox.setVisible(true);
                break;
        }
    }
    private void processAction() {
        int friendlyCalculatedPosition = fighterState.getFriendlyPosition() + move(getMovementUnitFromAction(fighterState.getFriendlyAction()), fighterState.getFriendlySide());
        int enemyCalculatedPosition = fighterState.getEnemyPosition() + move(getMovementUnitFromAction(fighterState.getEnemyAction()), fighterState.getEnemySide());

        if (isOverlap(friendlyCalculatedPosition, enemyCalculatedPosition)) {
            switch (fighterState.getFriendlyAction()) {
                case ATTACK -> processActionAttack();
                case DEFEND -> processActionDefend();
                case DASH -> processActionDash();
            }
        } else {
            fighterState.setFriendlyPosition(
                    friendlyCalculatedPosition
            );
            fighterState.setEnemyPosition(
                    enemyCalculatedPosition
            );
        }
    }
    private void processActionAttack() {
        switch (fighterState.getEnemyAction()) {
            case ATTACK:
                fighterState.setFriendlyPosition(
                        fighterState.getFriendlyPosition() + move(0, fighterState.getFriendlySide())
                );
                fighterState.setEnemyPosition(
                        fighterState.getEnemyPosition() + move(0, fighterState.getEnemySide())
                );
                break;
            case DEFEND:
                fighterState.setEnemyPosition(
                        fighterState.getEnemyPosition() + move(1, fighterState.getEnemySide())
                );
                if (fighterState.getFriendlySide() == FighterSide.LEFT) {
                    fighterState.setFriendlyPosition(fighterState.getEnemyPosition() - 1);
                }
                else {
                    fighterState.setFriendlyPosition(fighterState.getEnemyPosition() + 1);
                }
                break;
            case DASH:
                fighterState.setFriendlyPosition(
                        fighterState.getFriendlyPosition() + move(1, fighterState.getFriendlySide())
                );
                fighterState.setEnemyPosition(
                        fighterState.getEnemyPosition() + move(-2, fighterState.getEnemySide())
                );
                break;
        }
    }
    private void processActionDefend() {
        switch (fighterState.getEnemyAction()) {
            case ATTACK:
                fighterState.setFriendlyPosition(
                        fighterState.getFriendlyPosition() + move(1, fighterState.getFriendlySide())
                );
                if (fighterState.getEnemySide() == FighterSide.LEFT) {
                    fighterState.setEnemyPosition(fighterState.getFriendlyPosition() - 1);
                }
                else {
                    fighterState.setEnemyPosition(fighterState.getEnemyPosition() + 1);
                }
                break;
            case DEFEND:
                fighterState.setFriendlyPosition(
                        fighterState.getFriendlyPosition() + move(0, fighterState.getFriendlySide())
                );
                fighterState.setEnemyPosition(
                        fighterState.getEnemyPosition() + move(0, fighterState.getEnemySide())
                );
                break;
            case DASH:
                fighterState.setFriendlyPosition(
                        fighterState.getFriendlyPosition() + move(-2, fighterState.getFriendlySide())
                );
                fighterState.setEnemyPosition(
                        fighterState.getEnemyPosition() + move(1, fighterState.getEnemySide())
                );
                break;
        }
    }
    private void processActionDash() {
        switch (fighterState.getEnemyAction()) {
            case ATTACK:
                fighterState.setFriendlyPosition(
                        fighterState.getFriendlyPosition() + move(-2, fighterState.getFriendlySide())
                );
                fighterState.setEnemyPosition(
                        fighterState.getEnemyPosition() + move(1, fighterState.getEnemySide())
                );
                break;
            case DEFEND:
                fighterState.setFriendlyPosition(
                        fighterState.getFriendlyPosition() + move(1, fighterState.getFriendlySide())
                );
                fighterState.setEnemyPosition(
                        fighterState.getEnemyPosition() + move(-2, fighterState.getEnemySide())
                );
                break;
            case DASH:
                fighterState.setFriendlyPosition(
                        fighterState.getFriendlyPosition() + move(-1, fighterState.getFriendlySide())
                );
                fighterState.setEnemyPosition(
                        fighterState.getEnemyPosition() + move(-1, fighterState.getEnemySide())
                );
                break;
        }
    }
    private void updateStatus() {
        FighterStatus status = switch (fighterState.getFriendlySide()) {
            case FighterSide.LEFT:
                if (fighterState.getFriendlyPosition() < 0) yield FighterStatus.LOST;
                else if (fighterState.getEnemyPosition() > buttonList.size() - 5) yield FighterStatus.WON;
                else yield FighterStatus.PENDING;
            case FighterSide.RIGHT:
                if (fighterState.getFriendlyPosition() > buttonList.size() - 5) yield FighterStatus.LOST;
                else if (fighterState.getEnemyPosition() < 0) yield FighterStatus.WON;
                else yield FighterStatus.PENDING;
            default:
                throw new InvalidParameterException();
        };

        fighterState.setStatus(status);
    }
    private void resetBoard() {
        for (int i = 0; i <= 1; i++) {
            buttonList.get(i).setEnabled(true);
            buttonList.get(i).setClassName("fighter-deadzone");
            buttonList.get(i).setIcon(new Icon(VaadinIcon.WARNING));
        }

        for (int i = 12; i <= 13; i++) {
            buttonList.get(i).setEnabled(true);
            buttonList.get(i).setClassName("fighter-deadzone");
            buttonList.get(i).setIcon(new Icon(VaadinIcon.WARNING));
        }

        for (int i = 2; i <= 11; i++) {
            buttonList.get(i).setEnabled(true);
            buttonList.get(i).setClassName("");
            buttonList.get(i).setIcon(null);
        }
    }
    private void setupPositions() {
        int side = ThreadLocalRandom.current().nextInt(0, 2);
        resetBoard();
        switch (side) {
            case 0: // Left side
                fighterState.setFriendlyPosition(1);
                fighterState.setEnemyPosition(8);
                fighterState.setFriendlySide(FighterSide.LEFT);
                fighterState.setEnemySide(FighterSide.RIGHT);
                updateFriendlyUI(
                        fighterState.getFriendlyPosition(),
                        fighterState.getFriendlyPosition()
                );
                updateEnemyUI(
                        fighterState.getEnemyPosition(),
                        fighterState.getEnemyPosition()
                );
                break;
            case 1: // Right side
                fighterState.setFriendlyPosition(8);
                fighterState.setEnemyPosition(1);
                fighterState.setFriendlySide(FighterSide.RIGHT);
                fighterState.setEnemySide(FighterSide.LEFT);
                updateFriendlyUI(
                        fighterState.getFriendlyPosition(),
                        fighterState.getFriendlyPosition()
                );
                updateEnemyUI(
                        fighterState.getEnemyPosition(),
                        fighterState.getEnemyPosition()
                );
                break;
            default:
                throw new InvalidParameterException("ThreadLocalRandom returns invalid number: " + side);
        }

        fighterDescriptionMatchLabel.setText(
                messageBundle.formatMessage(
                        "startupDescription",
                        fighterState.getFriendlySide() == FighterSide.LEFT
                                ? messageBundle.getMessage("startupDescription.left")
                                : messageBundle.getMessage("startupDescription.right")
                )
        );
    }
    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        fighterState = new FighterState();
        buttonList = Arrays.asList(
                button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
                button10, button11, button12, button13
        );
        fighterRuleText.setValue(
                messageBundle.getMessage("fighterRule.text")
        );
        fighterPointsInput.focus();

    }
    private void resetState() {
        fighterState.setStatus(FighterStatus.PENDING);
        fighterState.setFriendlyAction(FighterAction.UNKNOWN);
        fighterState.setEnemyAction(FighterAction.UNKNOWN);
        setupPositions();
        fighterAI = new FighterAI(
                fighterState.getEnemySide(),
                fighterState.getEnemyPosition(),
                fighterState.getFriendlyPosition()
        );
    }

    @Install(to = "fighterRuleText", subject = "validator")
    private void fighterRuleTextValidator(final String value) {
        if (fighterPointsInput.isEmpty()) {
            fighterStartButton.setEnabled(false);
            throw new ValidationException(CarnivalToolbox.getError(messages, "noValue"));
        }
        else {
            fighterStartButton.setEnabled(true);
        }
    }
    @Subscribe(id = "fighterStartButton", subject = "clickListener")
    public void onFighterStartButtonClick(final ClickEvent<JmixButton> event) {
        User user = CarnivalToolbox.getLoggedInUser(currentAuthentication);

        if (CarnivalToolbox.isNullOrEmpty(fighterPointsInput.getValue())) {
            notifications.show(
                    CarnivalToolbox.getError(messages, "invalidPointsTitle"),
                    CarnivalToolbox.getError(messages, "invalidPointsMessage")
            );
            return;
        }

        if (getDeltaPoints() < 0) {
            notifications.show(
                    CarnivalToolbox.getError(messages, "insuficientPointsTitle"),
                    CarnivalToolbox.getError(messages, "insuficientPointsMessage")
            );
        } else {
            long pointsGiven = Long.parseLong(fighterPointsInput.getValue());
            user.setPoints(user.getPoints() - pointsGiven);
            dataManager.save(user);

            fighterState.setMatchId(UUID.randomUUID());
            fighterState.setPointsGiven(pointsGiven);
            fighterState.setMultiplier(-1.0d);
            fighterState.setFriendlyMatchPoint(0);
            fighterState.setEnemyMatchPoint(0);
            resetBoard();
            resetState();
            setActionButtonsState(true);

            // Set debug values

            fighterTitleMatchLabel.setText(fighterState.getFriendlyMatchPoint() + " - " + fighterState.getEnemyMatchPoint());

            changeVisualMode(FighterMode.POSTSTART);

            notifications.show(
                    messageBundle.getMessage("gameStart"),
                    messageBundle.getMessage("glhf")
            );
        }
    }
    @Subscribe(id = "actionButton", subject = "clickListener")
    public void onActionButtonClick(final ClickEvent<JmixButton> event) {
        actionButton.setEnabled(false);

        attackButton.setThemeName("");
        defendButton.setThemeName("");
        dashButton.setThemeName("");

        fighterAI.setFriendlyPosition(fighterState.getFriendlyPosition());
        fighterAI.setEnemyPosition(fighterState.getEnemyPosition());
        fighterState.setEnemyAction(fighterAI.next());

        int oldFriendlyPosition = fighterState.getFriendlyPosition();
        int oldEnemyPosition = fighterState.getEnemyPosition();

        processAction();
        resetBoard();
        updateStatus();
        updateFriendlyUI(oldFriendlyPosition, fighterState.getFriendlyPosition());
        updateEnemyUI(oldEnemyPosition, fighterState.getEnemyPosition());

        fighterState.setFriendlyDelta(fighterState.getFriendlySide() == FighterSide.LEFT
                ? fighterState.getFriendlyPosition() - oldFriendlyPosition
                : oldFriendlyPosition - fighterState.getFriendlyPosition());

        fighterState.setEnemyDelta(fighterState.getEnemySide() == FighterSide.LEFT
                ? fighterState.getEnemyPosition() - oldEnemyPosition
                : oldEnemyPosition - fighterState.getEnemyPosition());

        updateResult();

        switch (fighterState.getStatus()) {
            case WON:
                reward();
                if (fighterState.getFriendlyMatchPoint() >= 3) {
                    finalizeReward();
                }
                attackButton.setThemeName("medium");
                defendButton.setThemeName("medium");
                dashButton.setThemeName("medium");
                break;
            case LOST:
                punish();
                if (fighterState.getEnemyMatchPoint() >= 3) {
                    finalizePunish();
                }
                attackButton.setThemeName("medium");
                defendButton.setThemeName("medium");
                dashButton.setThemeName("medium");
                break;
        }
        fighterTitleMatchLabel.setText(fighterState.getFriendlyMatchPoint() + " - " + fighterState.getEnemyMatchPoint());
        updateTitleAndDescription(
                fighterState.getFriendlyDelta(),
                fighterState.getEnemyDelta()
        );
        updateActionButtonUI();
    }
    @Subscribe(id = "attackButton", subject = "clickListener")
    public void onAttackButtonClick(final ClickEvent<JmixButton> event) {
        fighterState.setFriendlyAction(FighterAction.ATTACK);
        attackButton.setThemeName("primary");
        defendButton.setThemeName("");
        dashButton.setThemeName("");
        actionButton.setEnabled(true);
    }
    @Subscribe(id = "defendButton", subject = "clickListener")
    public void onDefendButtonClick(final ClickEvent<JmixButton> event) {
        fighterState.setFriendlyAction(FighterAction.DEFEND);
        attackButton.setThemeName("");
        defendButton.setThemeName("primary");
        dashButton.setThemeName("");
        actionButton.setEnabled(true);
    }
    @Subscribe(id = "dashButton", subject = "clickListener")
    public void onDashButtonClick(final ClickEvent<JmixButton> event) {
        fighterState.setFriendlyAction(FighterAction.DASH);
        attackButton.setThemeName("");
        defendButton.setThemeName("");
        dashButton.setThemeName("primary");
        actionButton.setEnabled(true);
    }
    @Subscribe(id = "nextMatchButton", subject = "clickListener")
    public void onNextMatchButtonClick(final ClickEvent<JmixButton> event) {
        resetBoard();
        resetState();
        nextMatchButton.setEnabled(false);
        nextMatchButton.setVisible(false);
        actionButton.setEnabled(false);
        actionButton.setVisible(true);
        setActionButtonsState(true);
    }
    @Subscribe(id = "endGameButton", subject = "clickListener")
    public void onEndGameButtonClick(final ClickEvent<JmixButton> event) {
        actionButton.setEnabled(true);
        actionButton.setVisible(true);
        nextMatchButton.setEnabled(false);
        nextMatchButton.setVisible(false);
        endGameButton.setVisible(false);
        changeVisualMode(FighterMode.PRESTART);
    }

    @Override
    public void updateSession() {
        Session session = dataManager.create(Session.class);
        session.setGameType(GameType.FIGHTER);
        session.setMatchId(fighterState.getMatchId());
        session.setUser(CarnivalToolbox.getLoggedInUser(currentAuthentication));
        session.setTime(LocalDateTime.now());
        session.setPointsChange(CarnivalToolbox.floorLongFromDouble(fighterState.getPointsGiven() * fighterState.getMultiplier()));
        dataManager.save(session);
    }

    @Override
    public void updateResult() {
        FighterResult fighterResult = dataManager.create(FighterResult.class);
        fighterResult.setMatchId(fighterState.getMatchId());
        fighterResult.setUser(CarnivalToolbox.getLoggedInUser(currentAuthentication));
        fighterResult.setTime(LocalDateTime.now());
        fighterResult.setPointsGiven(fighterState.getPointsGiven());
        fighterResult.setSide(fighterState.getFriendlySide());
        fighterResult.setStatus(fighterState.getStatus());
        fighterResult.setFriendlyPosition(fighterState.getFriendlyPosition());
        fighterResult.setEnemyPosition(fighterState.getEnemyPosition());
        fighterResult.setFriendlyAction(fighterState.getFriendlyAction());
        fighterResult.setEnemyAction(fighterState.getEnemyAction());
        fighterResult.setFriendlyDelta(fighterState.getFriendlyDelta());
        fighterResult.setEnemyDelta(fighterState.getEnemyDelta());
        fighterResult.setFriendlyMatchPoint(fighterState.getFriendlyMatchPoint());
        fighterResult.setEnemyMatchPoint(fighterState.getEnemyMatchPoint());
        fighterResult.setMultiplier(fighterState.getMultiplier());
        dataManager.save(fighterResult);
    }
}
package com.rashlight.carnival.communication.grpc;

import com.rashlight.carnival.entity.CrashMulState;
import com.rashlight.carnival.entity.GuessNumMode;
import com.rashlight.carnival.view.crashmul.CrashMulView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.kit.event.EventBus;
import io.jmix.flowui.view.ViewComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CrashMulTask extends BackgroundTask<Double, Double> {
    private final CrashMulServiceClient crashMulServiceClient;
    private final CrashMulView crashMulView;

    public CrashMulTask(
            CrashMulServiceClient crashMulServiceClient,
            CrashMulView crashMulView) {
        super(1, TimeUnit.HOURS);
        this.crashMulServiceClient = crashMulServiceClient;
        this.crashMulView = crashMulView;
    }

    @Override
    public void done(@Nonnull Double result) {
        super.done(result);
        crashMulView.handleFinalize(result);
    }

    @Override
    public void progress(@Nonnull List<Double> changes) {
        super.progress(changes);
        crashMulView.handleMultiplierUpdate(changes.getLast());
    }

    @Override
    @Nonnull
    public Double run(@Nonnull TaskLifeCycle<Double> taskLifeCycle) throws Exception {
        crashMulServiceClient.initiate();
        taskLifeCycle.publish(crashMulServiceClient.getCurrentMultiplier());
        while (!crashMulServiceClient.bump()) {
            Thread.sleep(Duration.ofMillis(Math.clamp(200 - crashMulServiceClient.getCurrentBumpTime() * 2L, 30, Integer.MAX_VALUE)));
            taskLifeCycle.publish(crashMulServiceClient.getCurrentMultiplier());
        }
        return crashMulServiceClient.getCurrentMultiplier();
    }

    @Override
    public boolean handleTimeoutException() {
        return crashMulView.handleTaskTimeout();
    }

    @Override
    public boolean handleException(@Nonnull Exception ex) {
        return crashMulView.handleUnhandledException(ex);
    }
}

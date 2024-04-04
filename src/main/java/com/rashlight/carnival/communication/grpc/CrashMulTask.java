package com.rashlight.carnival.communication.grpc;

import com.rashlight.carnival.view.crashmul.CrashMulView;
import com.vaadin.flow.component.html.NativeLabel;
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
    private final NativeLabel crashMulMatchLabel;
    private final CrashMulServiceClient crashMulServiceClient;

    public CrashMulTask(
            CrashMulServiceClient crashMulServiceClient,
            NativeLabel crashMulMatchLabel) {
        super(1, TimeUnit.MINUTES);
        this.crashMulServiceClient = crashMulServiceClient;
        this.crashMulMatchLabel = crashMulMatchLabel;
    }

    @Override
    public void done(@Nonnull Double result) {
        super.done(result);
        crashMulMatchLabel.setText("Done, " + result);
    }

    @Override
    public void progress(@Nonnull List<Double> changes) {
        super.progress(changes);
        crashMulMatchLabel.setText(changes.getLast() + "");
    }

    @Override
    @Nonnull
    public Double run(@Nonnull TaskLifeCycle<Double> taskLifeCycle) throws Exception {
        crashMulServiceClient.initiate();
        while (!crashMulServiceClient.bump()) {
            Thread.sleep(Duration.ofMillis(30));
            taskLifeCycle.publish(crashMulServiceClient.getCurrentMultiplier());
        }
        return crashMulServiceClient.getCurrentMultiplier();
    }

    @Override
    public boolean handleTimeoutException() {
        throw new ValidationException("CrashMulTask timeout is too long");
    }

    @Override
    public boolean handleException(@Nonnull Exception ex) {
        throw new ValidationException(ex);
    }
}

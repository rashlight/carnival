package com.rashlight.carnival.communication.grpc;

import com.rashlight.carnival.view.crashmul.CrashMulView;
import com.vaadin.flow.component.html.NativeLabel;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.kit.event.EventBus;
import io.jmix.flowui.view.ViewComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CrashMulTask extends BackgroundTask<Double, Double> {
    @ViewComponent
    private NativeLabel crashMulMatchLabel;
    @Autowired
    private CrashMulServiceClient crashMulServiceClient;

    public CrashMulTask() {
        super(30, TimeUnit.MILLISECONDS);
    }

    @Override
    public void done(@Nonnull Double result) {
        crashMulMatchLabel.setText("Done, " + result);
        super.done(result);
    }

    @Override
    public void progress(@Nonnull List<Double> changes) {
        crashMulMatchLabel.setText(changes.getLast() + "");
        super.progress(changes);
    }

    @Override
    @Nonnull
    public Double run(@Nonnull TaskLifeCycle<Double> taskLifeCycle) throws Exception {
        while (crashMulServiceClient.bump()) {
            Thread.sleep(Duration.ofMillis(30));

        }
        return crashMulServiceClient.getCurrentMultiplier();
    }
}

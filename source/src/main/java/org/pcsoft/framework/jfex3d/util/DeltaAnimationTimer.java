package org.pcsoft.framework.jfex3d.util;

import javafx.animation.AnimationTimer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public abstract class DeltaAnimationTimer extends AnimationTimer {
    private final AtomicBoolean canceled = new AtomicBoolean(false);
    private final AtomicLong lastTick = new AtomicLong(-1L);

    @Override
    public void handle(long now) {
        if (lastTick.get() < 0L) {
            lastTick.set(now);
            return;
        }

        final long delatTick = now - lastTick.get();

        try {
            interpolate(delatTick);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lastTick.set(now);
    }

    protected abstract void interpolate(long nanoDelta);
}

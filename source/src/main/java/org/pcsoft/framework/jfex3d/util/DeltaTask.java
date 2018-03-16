package org.pcsoft.framework.jfex3d.util;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DeltaTask implements Runnable {
    private final AtomicBoolean canceled = new AtomicBoolean(false);

    @Override
    public final void run() {
        long lastTick = System.nanoTime();
        while (!canceled.get()) {
            final long currentTick = System.nanoTime();
            final long delatTick = currentTick - lastTick;

            try {
                interpolate(delatTick);
            } catch (Exception e) {
                e.printStackTrace();
            }

            lastTick = currentTick;
        }
    }

    protected abstract void interpolate(long nanoDelta);

    public void cancel() {
        canceled.set(true);
    }

    public boolean isCanceled() {
        return canceled.get();
    }
}

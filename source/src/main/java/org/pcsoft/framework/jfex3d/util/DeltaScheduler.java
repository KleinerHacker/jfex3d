package org.pcsoft.framework.jfex3d.util;

import java.util.function.Supplier;

public final class DeltaScheduler {
    private long scheduleNanos = 0L;
    private long elapsedNanos = 0L;

    private final Supplier<Long> calculateNextSchedule;

    public DeltaScheduler(Supplier<Long> calculateNextSchedule) {
        this.calculateNextSchedule = calculateNextSchedule;
        scheduleNanos = calculateNextSchedule.get();
    }

    public boolean isScheduled(final long deltaNanos) {
        elapsedNanos += deltaNanos;

        if (elapsedNanos >= scheduleNanos) {
            scheduleNanos = calculateNextSchedule.get();
            elapsedNanos = 0L;
            return true;
        }

        return false;
    }

    public long getScheduleNanos() {
        return scheduleNanos;
    }

    public void setScheduleNanos(long scheduleNanos) {
        this.scheduleNanos = scheduleNanos;
    }

    public long getElapsedNanos() {
        return elapsedNanos;
    }

    public void setElapsedNanos(long elapsedNanos) {
        this.elapsedNanos = elapsedNanos;
    }
}

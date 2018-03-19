package org.pcsoft.framework.jfex3d.util;

import java.util.Random;

public final class RandomUtils {
    private static final Random RANDOM = new Random();

    public static long nextLong(long minVal, long maxVal) {
        final long difVal = maxVal - minVal;
        if (difVal < 0L)
            throw new IllegalArgumentException("Min bigger than Max");

        return (difVal == 0 ? minVal : RANDOM.nextLong() % difVal) + minVal;
    }

    public static int nextInt(int minVal, int maxVal) {
        final int difVal = maxVal - minVal;
        if (difVal < 0)
            throw new IllegalArgumentException("Min bigger than Max");

        return (difVal == 0 ? minVal : RANDOM.nextInt() % difVal) + minVal;
    }

    public static double nextDouble(double minVal, double maxVal) {
        final double difVal = maxVal - minVal;
        if (difVal < 0)
            throw new IllegalArgumentException("Min bigger than Max");

        return RANDOM.nextDouble() * difVal + minVal;
    }
}

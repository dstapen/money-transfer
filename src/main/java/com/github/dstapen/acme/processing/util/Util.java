package com.github.dstapen.acme.processing.util;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

public final class Util {

    @Nonnull
    public static BigDecimal randomOrZero(long from, long to) {
        return fiftyFifty() ? randomBigDecimal(from, to) : BigDecimal.ZERO;
    }


    @Nonnull
    public static BigDecimal randomBigDecimal(long from, long to) {
        return new BigDecimal(ThreadLocalRandom.current().nextLong(from, to));
    }


    public static boolean fiftyFifty() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    @Nonnull
    public static String randomString() {
        return Long.toHexString(ThreadLocalRandom.current().nextLong());
    }

    private Util() {
    }
}

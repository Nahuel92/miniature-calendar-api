package org.nahuelrodriguez.miniaturecalendarapi.utils;

import java.util.concurrent.Callable;

public class Utils {
    public static <T> T tryAndGetOrThrow(final Callable<T> action) {
        try {
            return action.call();
        } catch (final IllegalArgumentException e) {
            throw e;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}

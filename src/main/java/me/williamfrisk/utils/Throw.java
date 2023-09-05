package me.williamfrisk.utils;

public class Throw {

    public static void asUnchecked(Exception e) {
        throw new UncheckedException(e);
    }

    private static class UncheckedException extends RuntimeException {
        private UncheckedException(Exception cause) {
            super(cause);
        }
    }
}

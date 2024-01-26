package com.github.zrdj.java.primitives.exception;

import java.util.function.Function;


class RethrowingExceptionHandler<T> implements FunctionExceptionHandler<T>, RunnableExceptionHandler {

    private final Function<Exception, RuntimeException> _rethrow;

    RethrowingExceptionHandler(final Function<Exception, RuntimeException> rethrow) {
        _rethrow = rethrow;
    }

    @Override
    public final T execute(final ExceptionFunction<T> function) {
        try {
            return function.run();
        } catch (Exception e) {
            throw _rethrow.apply(e);
        }
    }

    @Override
    public void execute(final ExceptionRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw _rethrow.apply(e);
        }
    }
}

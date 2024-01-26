package com.github.zrdj.java.exception;

import java.util.function.Function;


class SwallowingFunctionExceptionHandler<T> implements FunctionExceptionHandler<T>, RunnableExceptionHandler {

    private final Function<Exception, T> _swallow;

    SwallowingFunctionExceptionHandler(final Function<Exception, T> swallow) {
        _swallow = swallow;
    }

    @Override
    public final T execute(final ExceptionFunction<T> exceptionFunction) {
        try {
            return exceptionFunction.run();
        } catch (Exception e) {
            return _swallow.apply(e);
        }
    }

    @Override
    public void execute(final ExceptionRunnable exceptionFunction) {
        try {
            exceptionFunction.run();
        } catch (Exception e) {
            _swallow.apply(e);
        }
    }
}

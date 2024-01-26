package com.github.zrdj.java.exception;

import java.util.function.Function;

public interface Exceptions {
    static void rethrow(final ExceptionRunnable runnable, final Function<Exception, RuntimeException> mapper) {
        new RethrowingExceptionHandler<Void>(mapper).execute(runnable);
    }

    static RunnableExceptionHandler rethrowing(final Function<Exception, RuntimeException> mapper) {
        return new RethrowingExceptionHandler<Void>(mapper);
    }

    static <T> T rethrowFunction(final ExceptionFunction<T> function, final Function<Exception, RuntimeException> mapper) {
        return new RethrowingExceptionHandler<T>(mapper).execute(function);
    }

    static <T> FunctionExceptionHandler<T> rethrowingFunction(final Function<Exception, RuntimeException> mapper) {
        return new RethrowingExceptionHandler<T>(mapper);
    }

    static <T> T fallbackFunction(final ExceptionFunction<T> function, final Function<Exception, T> mapper) {
        return new SwallowingFunctionExceptionHandler<T>(mapper).execute(function);
    }

    static <T> FunctionExceptionHandler<T> fallbackingFunction(final Function<Exception, T> mapper) {
        return new SwallowingFunctionExceptionHandler<T>(mapper);
    }
}

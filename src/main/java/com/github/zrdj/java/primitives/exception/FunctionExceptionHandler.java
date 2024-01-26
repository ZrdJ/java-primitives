package com.github.zrdj.java.primitives.exception;

public interface FunctionExceptionHandler<T> {

    T execute(ExceptionFunction<T> exceptionFunction);
}

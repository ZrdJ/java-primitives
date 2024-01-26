package com.github.zrdj.java.exception;

public interface FunctionExceptionHandler<T> {

    T execute(ExceptionFunction<T> exceptionFunction);
}

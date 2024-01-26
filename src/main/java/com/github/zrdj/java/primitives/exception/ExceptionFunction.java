package com.github.zrdj.java.primitives.exception;

public interface ExceptionFunction<T> {
    T run() throws Exception;
}

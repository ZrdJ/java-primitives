package com.github.zrdj.java.exception;

public interface ExceptionFunction<T> {
    T run() throws Exception;
}

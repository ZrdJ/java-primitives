package com.github.zrdj.java.bytes.hash;

import com.github.zrdj.java.bytes.Bytes;
import com.github.zrdj.java.exception.Exceptions;

import java.security.MessageDigest;


public class HashedBytes extends Bytes.AbstractBytes {
    public HashedBytes(final Bytes value, final String algorithm) {
        super(Exceptions.rethrowFunction(() -> MessageDigest.getInstance(algorithm), IllegalStateException::new).digest(value.bytes()));
    }
}

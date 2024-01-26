package com.github.zrdj.java.primitives.bytes.hash;

import com.github.zrdj.java.primitives.bytes.Bytes;
import com.github.zrdj.java.primitives.exception.Exceptions;

import java.security.MessageDigest;


public class HashedBytes extends Bytes.AbstractBytes {
    public HashedBytes(final Bytes value, final String algorithm) {
        super(Exceptions.rethrowFunction(() -> MessageDigest.getInstance(algorithm), IllegalStateException::new).digest(value.bytes()));
    }
}

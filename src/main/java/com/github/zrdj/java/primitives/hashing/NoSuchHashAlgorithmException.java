package com.github.zrdj.java.primitives.hashing;

public class NoSuchHashAlgorithmException extends RuntimeException {
    public NoSuchHashAlgorithmException(final String algorithm, final Throwable cause) {
        super(String.format("Hash algorithm %s seems to be unavailable", algorithm), cause);
    }

    public NoSuchHashAlgorithmException(final Throwable cause) {
        super(cause);
    }
}

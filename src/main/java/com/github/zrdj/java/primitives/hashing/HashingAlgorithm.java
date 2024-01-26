package com.github.zrdj.java.primitives.hashing;

public interface HashingAlgorithm {
    String algorithm();

    boolean isAvailable();

    byte[] hash(byte[] input);
}

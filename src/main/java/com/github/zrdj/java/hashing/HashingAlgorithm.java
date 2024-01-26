package com.github.zrdj.java.hashing;

public interface HashingAlgorithm {
    String algorithm();

    boolean isAvailable();

    byte[] hash(byte[] input);
}

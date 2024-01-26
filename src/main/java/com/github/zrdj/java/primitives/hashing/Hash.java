package com.github.zrdj.java.primitives.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum Hash implements HashingAlgorithm {
    SHA("SHA"),
    SHA_224("SHA-224"),
    SHA_256("SHA-256"),
    SHA_384("SHA-384"),
    SHA_512("SHA-512"),
    SHA_512_224("SHA-512/224"),
    SHA_512_256("SHA-512/256"),
    SHA3_224("SHA3-224"),
    SHA3_256("SHA3-256"),
    SHA3_384("SHA3-384"),
    SHA3_512("SHA3-512"),
    MD2("MD2"),
    MD5("MD5"),
    ;
    private final String _algorithm;
    private MessageDigest _digest = null;
    private NoSuchAlgorithmException _error = null;

    Hash(final String algorithm) {
        _algorithm = algorithm;
        try {
            _digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            _error = e;
        }
    }

    @Override
    public String algorithm() {
        return _algorithm;
    }

    @Override
    public boolean isAvailable() {
        return _error == null;
    }

    @Override
    public byte[] hash(final byte[] input) {
        if (!isAvailable()) {
            throw new NoSuchHashAlgorithmException(_algorithm, _error);
        }
        return _digest.digest(input);
    }
}

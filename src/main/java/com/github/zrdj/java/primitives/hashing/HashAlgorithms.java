package com.github.zrdj.java.primitives.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum HashAlgorithms implements HashingAlgorithm {
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
    private final boolean _available;

    HashAlgorithms(final String algorithm) {
        _algorithm = algorithm;
        boolean available;
        try {
            MessageDigest.getInstance(algorithm);
            available = true;
        } catch (NoSuchAlgorithmException e) {
            available = false;
        }
        _available = available;
    }

    @Override
    public String algorithm() {
        return _algorithm;
    }

    @Override
    public boolean isAvailable() {
        return _available;
    }

    @Override
    public Hash hash(final byte[] input) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(_algorithm);
            return new Hash.Of(digest.digest(input), this);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchHashAlgorithmException(_algorithm, e);
        }
    }
}
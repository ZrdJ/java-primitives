package com.github.zrdj.java.primitives.bytes.key;

import com.github.zrdj.java.primitives.bytes.Bytes;

public class LowEncryptedKeyBytes extends PKDF2EncryptedKeyBytes {
    public LowEncryptedKeyBytes(final Bytes key, final Bytes salt) {
        super(key, salt, 10_000, 256);
    }
}

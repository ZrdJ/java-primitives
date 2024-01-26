package com.github.zrdj.java.bytes.key;

import com.github.zrdj.java.bytes.Bytes;

public class HighEncryptedKeyBytes extends PKDF2EncryptedKeyBytes {
    public HighEncryptedKeyBytes(final Bytes key, final Bytes salt) {
        super(key, salt, 100_000, 256);
    }
}

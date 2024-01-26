package com.github.zrdj.java.bytes.key;

import com.github.zrdj.java.bytes.Bytes;

public class MediumEncryptedKeyBytes extends PKDF2EncryptedKeyBytes {
    public MediumEncryptedKeyBytes(final Bytes key, final Bytes salt) {
        super(key, salt, 50_000, 256);
    }
}

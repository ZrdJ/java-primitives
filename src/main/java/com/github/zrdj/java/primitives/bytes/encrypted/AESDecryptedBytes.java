package com.github.zrdj.java.primitives.bytes.encrypted;


import com.github.zrdj.java.primitives.bytes.Bytes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESDecryptedBytes extends Bytes.AbstractBytes {

    public AESDecryptedBytes(final Bytes value, final Bytes key, final Bytes salt) {
        super(decrypt(value, key, salt));
    }

    private static byte[] decrypt(final Bytes value, final Bytes key, final Bytes salt) {
        try {
            final Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(key.bytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new PBEParameterSpec(salt.bytes(), 20));
            return cipher.doFinal(value.bytes());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

package com.github.zrdj.java.bytes.encrypted;


import com.github.zrdj.java.bytes.Bytes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptedBytes extends Bytes.AbstractBytes {

    public AESEncryptedBytes(final Bytes value, final Bytes key, final Bytes salt) {
        super(encrypt(value, key, salt));
    }

    private static byte[] encrypt(final Bytes value, final Bytes key, final Bytes salt) {
        try {
            final Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(key.bytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new PBEParameterSpec(salt.bytes(), 20));
            return cipher.doFinal(value.bytes());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

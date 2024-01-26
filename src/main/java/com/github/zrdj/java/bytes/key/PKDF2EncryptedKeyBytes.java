package com.github.zrdj.java.bytes.key;

import com.github.zrdj.java.bytes.Bytes;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class PKDF2EncryptedKeyBytes extends Bytes.AbstractBytes {

    public PKDF2EncryptedKeyBytes(final Bytes key, final Bytes salt, final int iterations, final int hashsize) {
        super(hash(key, salt, iterations, hashsize));
    }

    private static byte[] hash(final Bytes key, final Bytes salt, final int iterations, final int hashsize) {
        try {
            final PBEKeySpec spec = new PBEKeySpec(key.asString().toCharArray(), salt.bytes(), iterations, hashsize);
            final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}

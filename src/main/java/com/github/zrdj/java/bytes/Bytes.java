package com.github.zrdj.java.bytes;

import com.github.zrdj.java.bytes.encrypted.AESDecryptedBytes;
import com.github.zrdj.java.bytes.encrypted.AESEncryptedBytes;
import com.github.zrdj.java.codec.Codec;
import com.github.zrdj.java.hashing.HashingAlgorithm;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public interface Bytes {
    byte[] bytes();

    default long length() {
        return bytes().length;
    }

    default ByteArrayInputStream toInputStream() {
        return new ByteArrayInputStream(bytes());
    }

    default String asString() {
        return new String(bytes());
    }

    void destroy();

    default Bytes encode(final Codec codec) {
        return new Bytes.Of(codec.encode(this.bytes()));
    }

    default Bytes decode(final Codec codec) {
        return new Bytes.Of(codec.decode(this.bytes()));
    }

    default Bytes hash(final HashingAlgorithm algorithm) {
        return new Bytes.Of(algorithm.hash(this.bytes()));
    }

    default Bytes encryptAES(final Bytes key, final Bytes salt) {
        return new AESEncryptedBytes(this, key, salt);
    }

    default Bytes decryptAES(final Bytes key, final Bytes salt) {
        return new AESDecryptedBytes(this, key, salt);
    }

    abstract class AbstractBytes implements Bytes {

        private final byte[] _value;

        protected AbstractBytes(final byte[] value) {
            _value = value;
        }

        @Override
        public final byte[] bytes() {
            return _value;
        }

        @Override
        public void destroy() {
            Arrays.fill(_value, (byte) 0);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !Bytes.class.isAssignableFrom(o.getClass())) {
                return false;
            }
            final Bytes that = (Bytes) o;
            return Arrays.equals(_value, that.bytes());
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(_value);
        }
    }

    final class Of extends AbstractBytes {

        public Of(final String value) {
            this(value.getBytes(StandardCharsets.UTF_8));
        }

        public Of(final byte[] value) {
            super(value);
        }

        public Of() {
            super(new byte[0]);
        }
    }

}

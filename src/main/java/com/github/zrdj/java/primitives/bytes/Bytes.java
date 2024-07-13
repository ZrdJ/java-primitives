package com.github.zrdj.java.primitives.bytes;

import com.github.zrdj.java.primitives.bytes.encrypted.AESDecryptedBytes;
import com.github.zrdj.java.primitives.bytes.encrypted.AESEncryptedBytes;
import com.github.zrdj.java.primitives.codec.Codec;
import com.github.zrdj.java.primitives.hashing.HashingAlgorithm;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Arrays;


public interface Bytes {
    Bytes empty = new Bytes.Of();

    final class Of extends AbstractBytes {

        public Of(final String value, Charset charset) {
            this(value.getBytes(charset));
        }

        public Of(final String value) {
            this(value.getBytes());
        }

        public Of(final byte[] value) {
            super(value);
        }

        public Of() {
            super(new byte[0]);
        }
    }

    static Bytes of(final byte[] value) {
        return new Bytes.Of(value);
    }

    static Bytes empty() {
        return empty;
    }

    static Bytes of(String value, Charset charset) {
        return new Bytes.Of(value, charset);
    }


    byte[] bytes();

    default long length() {
        return bytes().length;
    }

    ByteSize size();

    default ByteArrayInputStream toInputStream() {
        return new ByteArrayInputStream(bytes());
    }

    default String asString() {
        return new String(bytes());
    }

    void destroy();

    default Bytes encode(final Codec codec) {
        return Bytes.of(codec.encode(this.bytes()));
    }

    default Bytes decode(final Codec codec) {
        return Bytes.of(codec.decode(this.bytes()));
    }

    static Bytes of(String value) {
        return new Bytes.Of(value);
    }

    default Bytes encryptAES(final Bytes key, final Bytes salt) {
        return new AESEncryptedBytes(this, key, salt);
    }

    default Bytes decryptAES(final Bytes key, final Bytes salt) {
        return new AESDecryptedBytes(this, key, salt);
    }

    abstract class AbstractBytes implements Bytes {
        private final byte[] _value;
        private final ByteSize _size;

        protected AbstractBytes(final byte[] value) {
            _value = value;
            _size = ByteSize.of(value.length);
        }

        @Override
        public ByteSize size() {
            return _size;
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

    default Bytes hash(final HashingAlgorithm algorithm) {
        return Bytes.of(algorithm.hash(this.bytes()).value());
    }

}

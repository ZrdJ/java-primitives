package com.github.zrdj.java.primitives.hashing;

import com.github.zrdj.java.primitives.codec.Codecs;

public interface Hash {
    final class Of implements Hash {
        private final byte[] _value;
        private final HashAlgorithms _algorithm;

        public Of(final byte[] value, final HashAlgorithms algorithm) {
            _value = value;
            _algorithm = algorithm;
        }

        public byte[] value() {
            return _value;
        }

        @Override
        public HashingAlgorithm algorithm() {
            return _algorithm;
        }
    }

    byte[] value();

    HashingAlgorithm algorithm();

    default String toHex() {
        return Codecs.Hex.encodeToString(value());
    }

    default String toBase64() {
        return Codecs.Base64.encodeToString(value());
    }

    default String toBase64Url() {
        return Codecs.Base64Url.encodeToString(value());
    }

}

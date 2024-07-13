package com.github.zrdj.java.primitives.codec.hex;

import com.github.zrdj.java.primitives.codec.Codec;

public final class HexCodec implements Codec {
    private final String _hexCharacters = "0123456789abcdef";

    private StringBuilder internalEncode(final byte[] bytes) {
        StringBuilder result = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            int val = b & 0xff;
            result.append(_hexCharacters.charAt(val / 16));
            result.append(_hexCharacters.charAt(val % 16));
        }
        return result;
    }

    @Override
    public byte[] encode(final byte[] value) {
        final StringBuilder builder = internalEncode(value);
        final int length = builder.length();
        final byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) builder.charAt(i);
        }
        return result;
    }

    @Override
    public String encodeToString(final byte[] value) {
        final StringBuilder builder = internalEncode(value);
        return builder.toString();
    }

    @Override
    public byte[] decode(final byte[] value) {
        if (value.length % 2 != 0) {
            throw new IllegalArgumentException("Expected a byte array of even length");
        }
        int size = value.length / 2;
        byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            int hi = Character.digit(value[2 * i], 16);
            int lo = Character.digit(value[2 * i + 1], 16);
            if ((hi == -1) || (lo == -1)) {
                throw new IllegalArgumentException("input is not hexadecimal");
            }
            result[i] = (byte) (16 * hi + lo);
        }
        return result;
    }
}

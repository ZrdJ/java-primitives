package com.github.zrdj.java.codec.base64;

import com.github.zrdj.java.codec.Codec;

import java.util.Base64;

abstract class Base64BaseCodec implements Codec {
    private final Base64.Decoder _decoder;
    private final Base64.Encoder _encoder;

    Base64BaseCodec(final Base64.Decoder decoder, final Base64.Encoder encoder) {
        _decoder = decoder;
        _encoder = encoder;
    }

    @Override
    public final byte[] encode(final byte[] value) {
        return _encoder.encode(value);
    }

    @Override
    public final byte[] decode(final byte[] value) {
        return _decoder.decode(value);
    }

}

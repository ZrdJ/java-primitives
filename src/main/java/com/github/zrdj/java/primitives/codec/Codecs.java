package com.github.zrdj.java.primitives.codec;

import com.github.zrdj.java.primitives.codec.base64.Base64Codec;
import com.github.zrdj.java.primitives.codec.base64.Base64UrlCodec;
import com.github.zrdj.java.primitives.codec.hex.HexCodec;

public enum Codecs implements Codec {
    Base64(new Base64Codec()),
    Base64Url(new Base64UrlCodec()),
    Hex(new HexCodec()),
    ;
    private Codec _codec;

    Codecs(final Codec codec) {
        _codec = codec;
    }

    @Override
    public byte[] encode(final byte[] value) {
        return _codec.encode(value);
    }

    @Override
    public byte[] decode(final byte[] value) {
        return _codec.decode(value);
    }

    public Codec codec() {
        return _codec;
    }
}

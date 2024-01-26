package com.github.zrdj.java.primitives.codec.base64;

import java.util.Base64;

public final class Base64Codec extends Base64BaseCodec {
    public Base64Codec() {
        super(Base64.getDecoder(), Base64.getEncoder());
    }
}

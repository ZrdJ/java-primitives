package com.github.zrdj.java.codec.base64;

import java.util.Base64;

public final class Base64UrlCodec extends Base64BaseCodec {
    public Base64UrlCodec() {
        super(Base64.getUrlDecoder(), Base64.getUrlEncoder());
    }
}

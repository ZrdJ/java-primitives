package com.github.zrdj.java.codec;

public interface Codec {
    byte[] encode(byte[] value);

    byte[] decode(byte[] value);
}

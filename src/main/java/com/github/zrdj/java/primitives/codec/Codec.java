package com.github.zrdj.java.primitives.codec;

public interface Codec {
    byte[] encode(byte[] value);

    byte[] decode(byte[] value);
}

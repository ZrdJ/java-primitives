package com.github.zrdj.java.primitives.bytes;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;


public class BytesTest extends TestCase {

    public void testEquality() {
        assertThat(new Bytes.Of("hello")).isEqualTo(new Bytes.Of("hello"));
    }
}
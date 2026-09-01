package com.github.zrdj.java.primitives.codec;

import com.github.zrdj.java.primitives.codec.hex.HexCodec;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CodecsTest {

    // [impl->req~codec.available-codecs~1]
    @Test
    public void hexDelegatesToHexCodec() {
        final byte[] bytes = {(byte) 0xAB, 0x01};

        assertThat(Codecs.Hex.encodeToString(bytes)).isEqualTo(new HexCodec().encodeToString(bytes));
    }

    // [impl->req~codec.available-codecs~1]
    @Test
    public void base64VsBase64UrlDifferForUrlUnsafeInput() {
        final byte[] bytes = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        final String standard = Codecs.Base64.encodeToString(bytes);
        final String urlSafe = Codecs.Base64Url.encodeToString(bytes);

        assertThat(standard).isNotEqualTo(urlSafe);
        assertThat(standard).contains("/");
        assertThat(urlSafe).contains("_");
    }
}

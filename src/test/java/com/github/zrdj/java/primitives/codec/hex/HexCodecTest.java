package com.github.zrdj.java.primitives.codec.hex;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HexCodecTest {

    // [impl->req~codec.hex-format~1]
    @Test
    public void encodingIsLowercase() {
        final byte[] input = {(byte) 0xAB};

        assertThat(new HexCodec().encodeToString(input)).isEqualTo("ab");
    }

    // [impl->req~codec.hex-format~1]
    @Test
    public void oddLengthInputIsRejected() {
        assertThatThrownBy(() -> new HexCodec().decode(new byte[]{'a'}))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // [impl->req~codec.hex-format~1]
    @Test
    public void nonHexCharactersAreRejected() {
        assertThatThrownBy(() -> new HexCodec().decode("zz".getBytes()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

package com.github.zrdj.java.primitives.hashing;

import com.github.zrdj.java.primitives.codec.Codecs;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HashTest {

    // [impl->req~hashing.digest-text-rendering~1]
    @Test
    public void toHexDelegatesToTheHexCodec() {
        final Hash hash = HashAlgorithms.SHA_256.hash("hello".getBytes());

        assertThat(hash.toHex()).isEqualTo(Codecs.Hex.encodeToString(hash.value()));
    }
}

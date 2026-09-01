package com.github.zrdj.java.primitives.bytes.hash;

import com.github.zrdj.java.primitives.bytes.Bytes;
import com.github.zrdj.java.primitives.hashing.HashAlgorithms;
import com.github.zrdj.java.primitives.hashing.NoSuchHashAlgorithmException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HashedBytesTest {

    // [impl->req~hashing.bytes-hashing-entry-points~1]
    @Test
    public void bytesHashViaTheCuratedEnumMatchesTheDirectDigest() {
        final Bytes value = Bytes.of("hello");

        final Bytes hashed = value.hash(HashAlgorithms.SHA_256);

        assertThat(hashed.bytes()).isEqualTo(HashAlgorithms.SHA_256.hash(value.bytes()).value());
    }

    // [impl->req~hashing.bytes-hashing-entry-points~1]
    @Test
    public void unavailableRawAlgorithmNameThrowsIllegalStateException() {
        final Bytes value = Bytes.of("hello");

        assertThatThrownBy(() -> new HashedBytes(value, "NOT-A-REAL-ALGORITHM"))
                .isInstanceOf(IllegalStateException.class)
                .isNotInstanceOf(NoSuchHashAlgorithmException.class);
    }
}

package com.github.zrdj.java.primitives.bytes;

import com.github.zrdj.java.primitives.bytes.hash.HashedBytes;
import com.github.zrdj.java.primitives.codec.Codecs;
import junit.framework.TestCase;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;


public class BytesTest extends TestCase {

    // [impl->req~bytes-value.equality~1]
    public void testEquality() {
        assertThat(new Bytes.Of("hello")).isEqualTo(new Bytes.Of("hello"));
    }

    // [impl->req~bytes-value.equality~1]
    public void testEqualContentAcrossDifferentBytesImplementations() throws NoSuchAlgorithmException {
        final byte[] digest = MessageDigest.getInstance("SHA-256").digest("hello".getBytes());
        final Bytes plain = Bytes.of(digest);
        final Bytes hashed = new HashedBytes(Bytes.of("hello"), "SHA-256");

        assertThat(plain).isEqualTo(hashed);
        assertThat(plain.hashCode()).isEqualTo(hashed.hashCode());
    }

    // [impl->req~bytes-value.equality~1]
    public void testDifferentContentIsNotEqual() {
        assertThat(Bytes.of("hello")).isNotEqualTo(Bytes.of("world"));
    }

    // [impl->req~bytes-value.construction~1]
    public void testFromStringDefaultCharset() {
        assertThat(Bytes.of("hello").bytes()).isEqualTo("hello".getBytes());
    }

    // [impl->req~bytes-value.construction~1]
    public void testFromStringExplicitCharset() {
        assertThat(Bytes.of("hello", StandardCharsets.UTF_16).bytes())
                .isEqualTo("hello".getBytes(StandardCharsets.UTF_16));
    }

    // [impl->req~bytes-value.construction~1]
    public void testEmptyReturnsTheSharedEmptyInstance() {
        assertThat(Bytes.empty()).isSameAs(Bytes.empty());
    }

    // [impl->req~bytes-value.destroy~1]
    public void testDestroyZeroFillsTheContent() {
        final Bytes value = Bytes.of("secret");

        value.destroy();

        assertThat(value.bytes()).containsOnly((byte) 0);
    }

    // [impl->req~bytes-value.conversion~1]
    public void testAsString() {
        final byte[] raw = {72, 101, 108, 108, 111};

        assertThat(Bytes.of(raw).asString()).isEqualTo(new String(raw));
    }

    // [impl->req~bytes-value.conversion~1]
    public void testToInputStream() throws IOException {
        final Bytes value = Bytes.of("hello");

        assertThat(value.toInputStream().readAllBytes()).isEqualTo(value.bytes());
    }

    // [impl->req~bytes-value.conversion~1]
    public void testSize() {
        assertThat(Bytes.of(new byte[7]).size().bytes()).isEqualByComparingTo(new BigDecimal(7));
    }

    // [impl->req~codec.bytes-integration~1]
    public void testRoundTripThroughACodec() {
        final Bytes original = Bytes.of("hello world");

        final Bytes roundTripped = original.encode(Codecs.Base64).decode(Codecs.Base64);

        assertThat(roundTripped).isEqualTo(original);
    }
}

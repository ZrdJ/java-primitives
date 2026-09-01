package com.github.zrdj.java.primitives.bytes.encrypted;

import com.github.zrdj.java.primitives.bytes.Bytes;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * `req~cryptographic-byte-transforms.aes-encrypt-decrypt~1` documents two scenarios: a
 * successful round trip, and a wrapped failure. Only the second is testable here.
 *
 * {@code Cipher.getInstance("AES")} resolves to ECB mode under the SunJCE provider (confirmed on
 * both Temurin 11 and Temurin 25 in this repo's devcontainer), and ECB's {@code Cipher.init}
 * rejects any non-null {@code AlgorithmParameterSpec} -- including the {@code PBEParameterSpec}
 * that {@link AESEncryptedBytes}/{@link AESDecryptedBytes} unconditionally construct from the
 * salt. The result: {@code encryptAES} throws on every call, for any key, before decryption is
 * ever reached -- there is no key for which the documented round trip currently succeeds, and no
 * way to isolate "decrypting with the wrong key" as a distinct trigger from "encrypting at all".
 *
 * This is the concrete, reproducible shape of the already-flagged, deliberately-undocumented
 * finding ("the JCE provider decides the mode, not this repo") -- not a new defect. Fixing it
 * means choosing a cipher mode/padding/parameter scheme, a security-relevant API decision left to
 * the maintainer; see the worklog for the reported finding. The round-trip scenario is therefore
 * left without a test rather than one that could never pass without touching that decision.
 */
public class AESEncryptedBytesTest {

    // [impl->req~cryptographic-byte-transforms.aes-encrypt-decrypt~1]
    @Test
    public void cipherFailureIsWrapped() {
        final Bytes key = Bytes.of("0123456789abcdef");
        final Bytes salt = Bytes.of("saltsalt");
        final Bytes value = Bytes.of("top secret message");

        assertThatThrownBy(() -> value.encryptAES(key, salt))
                .isInstanceOf(IllegalStateException.class)
                .hasCauseInstanceOf(Exception.class);
    }
}

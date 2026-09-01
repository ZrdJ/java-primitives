package com.github.zrdj.java.primitives.bytes.key;

import com.github.zrdj.java.primitives.bytes.Bytes;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PKDF2EncryptedKeyBytesTest {

    // [impl->req~cryptographic-byte-transforms.pbkdf2-key-derivation~1]
    @Test
    public void presetIterationCounts() {
        final Bytes key = Bytes.of("password");
        final Bytes salt = Bytes.of("salt1234");

        assertThat(new LowEncryptedKeyBytes(key, salt))
                .isEqualTo(new PKDF2EncryptedKeyBytes(key, salt, 10_000, 256));
        assertThat(new MediumEncryptedKeyBytes(key, salt))
                .isEqualTo(new PKDF2EncryptedKeyBytes(key, salt, 50_000, 256));
        assertThat(new HighEncryptedKeyBytes(key, salt))
                .isEqualTo(new PKDF2EncryptedKeyBytes(key, salt, 100_000, 256));

        // 256 bits = 32 bytes, fixed regardless of preset
        assertThat(new LowEncryptedKeyBytes(key, salt).bytes()).hasSize(32);
        assertThat(new MediumEncryptedKeyBytes(key, salt).bytes()).hasSize(32);
        assertThat(new HighEncryptedKeyBytes(key, salt).bytes()).hasSize(32);
    }

    // [impl->req~cryptographic-byte-transforms.pbkdf2-key-derivation~1]
    @Test
    public void deterministicForTheSameInput() {
        final Bytes key = Bytes.of("password");
        final Bytes salt = Bytes.of("salt1234");

        assertThat(new LowEncryptedKeyBytes(key, salt)).isEqualTo(new LowEncryptedKeyBytes(key, salt));
    }
}

package com.github.zrdj.java.primitives.hashing;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * `algorithm-availability` and `unavailable-algorithm` both describe behavior that, in a normal
 * test run, never actually occurs -- every {@code HashAlgorithms} constant is available on every
 * JDK this library targets (all 13 are served by the JVM's "SUN" provider in this devcontainer).
 * To observe the documented "cached, not a fresh probe" and "re-resolves at call time" contrast
 * for real, both tests below temporarily remove the security provider that actually serves the
 * chosen algorithm and restore it in a finally block.
 */
public class HashAlgorithmsAvailabilityTest {

    // [impl->req~hashing.algorithm-availability~1]
    // [impl->req~hashing.unavailable-algorithm~1]
    @Test
    public void isAvailableStaysCachedWhileHashReResolvesAndFailsLive() throws Exception {
        final HashAlgorithms algorithm = HashAlgorithms.MD2;
        final Provider provider = MessageDigest.getInstance(algorithm.algorithm()).getProvider();
        final int position = positionOf(provider);

        Security.removeProvider(provider.getName());
        try {
            // Cached at enum init time, before the provider was removed -- still true.
            assertThat(algorithm.isAvailable()).isTrue();

            // hash() re-resolves MessageDigest.getInstance at call time, so it observes the
            // removal and wraps the resulting NoSuchAlgorithmException.
            assertThatThrownBy(() -> algorithm.hash("data".getBytes()))
                    .isInstanceOf(NoSuchHashAlgorithmException.class)
                    .hasMessageContaining(algorithm.algorithm());
        } finally {
            Security.insertProviderAt(provider, position);
        }
    }

    private static int positionOf(final Provider provider) {
        final Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            if (providers[i].getName().equals(provider.getName())) {
                return i + 1;
            }
        }
        throw new IllegalStateException("Provider not found among current providers: " + provider.getName());
    }
}

package com.github.zrdj.java.primitives.exception;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionsTest {

    // [impl->req~exception-adapters.rethrow~1]
    @Test
    public void rethrowSuccessPassesTheResultThrough() {
        final AtomicBoolean mapperCalled = new AtomicBoolean(false);

        final String result = Exceptions.rethrowFunction(() -> "ok", e -> {
            mapperCalled.set(true);
            return new RuntimeException(e);
        });

        assertThat(result).isEqualTo("ok");
        assertThat(mapperCalled).isFalse();
    }

    // [impl->req~exception-adapters.rethrow~1]
    @Test
    public void rethrowFailureIsMappedAndThrown() {
        final RuntimeException mapped = new IllegalArgumentException("mapped");

        assertThatThrownBy(() -> Exceptions.rethrowFunction(() -> {
            throw new Exception("boom");
        }, e -> mapped)).isSameAs(mapped);
    }

    // [impl->req~exception-adapters.fallback~1]
    @Test
    public void fallbackSuccessPassesTheResultThrough() {
        final AtomicBoolean mapperCalled = new AtomicBoolean(false);

        final String result = Exceptions.fallbackFunction(() -> "ok", e -> {
            mapperCalled.set(true);
            return "fallback";
        });

        assertThat(result).isEqualTo("ok");
        assertThat(mapperCalled).isFalse();
    }

    // [impl->req~exception-adapters.fallback~1]
    @Test
    public void fallbackFailureProducesTheMappersFallbackValue() {
        final String result = Exceptions.fallbackFunction(() -> {
            throw new Exception("boom");
        }, e -> "fallback");

        assertThat(result).isEqualTo("fallback");
    }
}

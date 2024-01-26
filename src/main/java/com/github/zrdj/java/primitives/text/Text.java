package com.github.zrdj.java.primitives.text;

import com.github.zrdj.java.primitives.bytes.Bytes;

import java.nio.charset.StandardCharsets;

public interface Text {
    Text empty = new CaseInsensitiveText(null);

    static Text caseSensitive(final String value) {
        return new CaseSensitiveText(value);
    }

    static Text caseInsensitive(final String value) {
        return new CaseInsensitiveText(value);
    }

    default Bytes asBytes() {
        if (isAbsent()) {
            return Bytes.empty;
        }
        return Bytes.of(asString().getBytes(StandardCharsets.UTF_8));
    }

    String asString();

    boolean contains(final String text);

    boolean endsWith(String text);

    Text findFirst(final String regex);

    default boolean isAbsent() {
        return asString() == null || asString().trim().isEmpty();
    }

    default boolean isNull() {
        return asString() == null;
    }

    default boolean isPresent() {
        return !isAbsent();
    }

    default String orElse(final String alternative) {
        return isAbsent() ? alternative : asString();
    }

    boolean startsWith(String text);

    Text toCaseInsensitive();

    Text toCaseSensitive();
}

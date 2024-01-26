package com.github.zrdj.java.text;

import java.util.Objects;

public class CaseInsensitiveText extends AbstractText {

    private final String originalValue;

    public CaseInsensitiveText(final String value) {
        super(value == null ? null : value.toLowerCase());
        originalValue = value;
    }

    @Override
    public boolean contains(final String text) {
        if (isNull() || text == null) {
            return false;
        }
        return _value.contains(text.toLowerCase());
    }

    @Override
    public boolean endsWith(final String text) {
        if (isNull() || text == null) {
            return false;
        }
        return _value.endsWith(text.toLowerCase());
    }

    @Override
    public int hashCode() {
        if (isNull()) {
            return 0;
        }
        return Objects.hashCode(asString().toLowerCase());
    }

    @Override
    public boolean startsWith(final String text) {
        if (isNull() || text == null) {
            return false;
        }
        return _value.startsWith(text.toLowerCase());
    }

    @Override
    public Text toCaseInsensitive() {
        return this;
    }

    @Override
    public Text toCaseSensitive() {
        return new CaseSensitiveText(originalValue);
    }

    @Override
    protected boolean isEqualTo(final String other) {
        if (isNull()) {
            return false;
        }
        return _value.equalsIgnoreCase(other);
    }
}

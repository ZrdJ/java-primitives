package com.github.zrdj.java.text;

import java.util.Objects;

public class CaseSensitiveText extends AbstractText {

    public CaseSensitiveText(final String value) {
        super(value);
    }

    @Override
    public boolean contains(final String text) {
        if (isNull() || text == null) {
            return false;
        }
        return asString().contains(text);
    }

    @Override
    public boolean endsWith(final String text) {
        if (isNull() || text == null) {
            return false;
        }
        return asString().endsWith(text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(asString());
    }

    @Override
    public boolean startsWith(final String text) {
        if (isNull() || text == null) {
            return false;
        }
        return asString().startsWith(text);
    }

    @Override
    public Text toCaseInsensitive() {
        return new CaseInsensitiveText(asString());
    }

    @Override
    public Text toCaseSensitive() {
        return this;
    }

    @Override
    protected boolean isEqualTo(final String other) {
        if (isNull()) {
            return false;
        }
        return Objects.equals(asString(), other);
    }
}

package com.github.zrdj.java.primitives.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractText implements Text {
    final String _value;

    AbstractText(final String value) {
        _value = value;
    }

    @Override
    public String asString() {
        return _value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !Text.class.isAssignableFrom(o.getClass())) {
            return false;
        }

        final Text that = (Text) o;

        return isEqualTo(that.asString());
    }

    @Override
    public Text findFirst(final String regex) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(orElse(""));

        return matcher.find() ? new CaseInsensitiveText(matcher.group(0)) : empty;
    }

    @Override
    public abstract int hashCode();

    protected abstract boolean isEqualTo(final String other);
}

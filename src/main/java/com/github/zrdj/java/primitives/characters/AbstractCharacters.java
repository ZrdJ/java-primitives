package com.github.zrdj.java.primitives.characters;

import java.util.Arrays;

abstract class AbstractCharacters implements Characters {

    private final char[] _value;

    AbstractCharacters(final char[] value) {
        _value = value;
    }

    @Override
    public final char[] characters() {
        return _value;
    }

    @Override
    public final void destroy() {
        Arrays.fill(_value, '0');
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !Characters.class.isAssignableFrom(o.getClass())) {
            return false;
        }
        final Characters that = (Characters) o;
        return Arrays.equals(_value, that.characters());
    }

    @Override
    public String toString() {
        return String.valueOf(characters());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(_value);
    }
}

package com.github.zrdj.java.primitives.characters;


public interface Characters extends CharSequence {
    static Characters of(final String value) {
        return new StandardCharacters(value);
    }

    static Characters of(final char[] value) {
        return new StandardCharacters(value);
    }

    @Override
    default char charAt(int index) {
        return characters()[index];
    }

    char[] characters();

    void destroy();

    @Override
    default int length() {
        return characters().length;
    }

    default Characters replace(final String actual, final String expected) {
        final StringBuilder builder = new StringBuilder().append(characters());
        if (builder.indexOf(actual) == -1) {
            return this;
        }

        int actualStart = builder.indexOf(actual);
        int actualEnd = actualStart + actual.length();
        builder.replace(actualStart, actualEnd, expected);
        final char[] chars = new char[builder.length()];
        builder.getChars(0, builder.length(), chars, 0);
        return new StandardCharacters(chars);
    }

    default Characters replaceAll(final String actual, final String expected) {
        final StringBuilder builder = new StringBuilder().append(characters());
        if (builder.indexOf(actual) == -1) {
            return this;
        }

        while (builder.indexOf(actual) > -1) {
            int actualStart = builder.indexOf(actual);
            int actualEnd = actualStart + actual.length();
            builder.replace(actualStart, actualEnd, expected);
        }

        final char[] chars = new char[builder.length()];
        builder.getChars(0, builder.length(), chars, 0);
        return new StandardCharacters(chars);
    }

    @Override
    default CharSequence subSequence(int start, int end) {
        return new StringBuilder().append(characters()).subSequence(start, end);
    }

}

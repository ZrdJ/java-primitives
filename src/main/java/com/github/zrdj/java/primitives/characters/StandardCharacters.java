package com.github.zrdj.java.primitives.characters;

final class StandardCharacters extends AbstractCharacters {

    StandardCharacters(final char[] characters) {
        super(characters);
    }

    public StandardCharacters(final String text) {
        super(text.toCharArray());
    }
}

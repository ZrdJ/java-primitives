package com.github.zrdj.java.characters;

final class StandardCharacters extends AbstractCharacters {

    StandardCharacters(final char[] characters) {
        super(characters);
    }

    public StandardCharacters(final String text) {
        super(text.toCharArray());
    }
}

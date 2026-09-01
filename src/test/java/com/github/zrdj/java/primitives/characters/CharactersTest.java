package com.github.zrdj.java.primitives.characters;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CharactersTest {

    // [impl->req~characters-value.destroy~1]
    @Test
    public void destroyFillsWithTheCharacterZero() {
        final Characters value = Characters.of("secret");

        value.destroy();

        assertThat(value.characters()).containsOnly('0');
    }

    // [impl->req~characters-value.replace-first-vs-all~1]
    @Test
    public void replaceReplacesOnlyTheFirstOccurrence() {
        final Characters value = Characters.of("banana");

        assertThat(value.replace("a", "X").toString()).isEqualTo("bXnana");
    }

    // [impl->req~characters-value.replace-first-vs-all~1]
    @Test
    public void replaceAllReplacesEveryOccurrence() {
        final Characters value = Characters.of("banana");

        assertThat(value.replaceAll("a", "X").toString()).isEqualTo("bXnXnX");
    }

    // [impl->req~characters-value.replace-first-vs-all~1]
    @Test
    public void noOccurrenceFoundReturnsTheSameInstance() {
        final Characters value = Characters.of("banana");

        assertThat(value.replace("z", "X")).isSameAs(value);
        assertThat(value.replaceAll("z", "X")).isSameAs(value);
    }

    // [impl->req~characters-value.equality~1]
    @Test
    public void equalContentIsEqual() {
        final Characters a = Characters.of("secret");
        final Characters b = Characters.of("secret".toCharArray());

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }

    // [impl->req~characters-value.subsequence-return-type~1]
    @Test
    public void subSequenceIsNotACharactersValue() {
        final Characters value = Characters.of("banana");

        final CharSequence sub = value.subSequence(1, 3);

        assertThat(sub.toString()).isEqualTo("an");
        assertThat(sub).isNotInstanceOf(Characters.class);
    }
}

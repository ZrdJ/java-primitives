package com.github.zrdj.java.primitives.text;

import com.github.zrdj.java.primitives.bytes.Bytes;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class TextTest {

    // [impl->req~text-value.presence~1]
    @Test
    public void blankValueIsAbsentButNotNull() {
        final Text value = Text.caseSensitive("   ");

        assertThat(value.isNull()).isFalse();
        assertThat(value.isAbsent()).isTrue();
    }

    // [impl->req~text-value.presence~1]
    @Test
    public void orElseSubstitutesForBlankAsWellAsNull() {
        final Text value = Text.caseSensitive("   ");

        assertThat(value.orElse("fallback")).isEqualTo("fallback");
    }

    // [impl->req~text-value.null-value-equality~1]
    @Test
    public void twoNullValuedTextInstancesAreNotEqual() {
        assertThat(Text.caseSensitive(null)).isNotEqualTo(Text.caseSensitive(null));
    }

    // [impl->req~text-value.case-insensitive-matching~1]
    @Test
    public void startsWithIsCaseInsensitive() {
        assertThat(Text.caseInsensitive("onetwo").startsWith("ONE")).isTrue();
    }

    // [impl->req~text-value.case-insensitive-matching~1]
    @Test
    public void toCaseSensitiveRestoresTheOriginalCasing() {
        assertThat(Text.caseInsensitive("OneTwo").toCaseSensitive().asString()).isEqualTo("OneTwo");
    }

    // [impl->req~text-value.case-insensitive-matching~1]
    @Test
    public void caseSensitiveTextDoesNotLowercase() {
        assertThat(Text.caseSensitive("onetwo").startsWith("ONE")).isFalse();
    }

    // [impl->req~text-value.find-first~1]
    @Test
    public void matchFoundReturnsACaseInsensitiveResult() {
        final Text result = Text.caseSensitive("order 42 confirmed").findFirst("[0-9]+");

        assertThat(result).isInstanceOf(CaseInsensitiveText.class);
        assertThat(result.asString()).isEqualTo("42");
    }

    // [impl->req~text-value.find-first~1]
    @Test
    public void noMatchFoundReturnsTheSharedEmptyValue() {
        assertThat(Text.caseSensitive("no digits here").findFirst("[0-9]+")).isSameAs(Text.empty);
    }

    // [impl->req~text-value.as-bytes~1]
    @Test
    public void absentValueYieldsEmptyBytes() {
        assertThat(Text.caseSensitive(null).asBytes()).isSameAs(Bytes.empty);
    }

    // [impl->req~text-value.as-bytes~1]
    @Test
    public void presentValueIsUtf8Encoded() {
        final String text = "café"; // non-ASCII, so a wrong charset would actually differ

        assertThat(Text.caseSensitive(text).asBytes()).isEqualTo(Bytes.of(text, StandardCharsets.UTF_8));
    }
}

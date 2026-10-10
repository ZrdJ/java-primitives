---
type: spec
title: Text Value
updated: 2026-09-01
---

## Purpose

Wraps a `String` as a `Text` value that carries its own case sensitivity
— `CaseSensitiveText` or `CaseInsensitiveText` — so `contains`/
`startsWith`/`endsWith`/equality behave consistently with how the value
was declared, plus null/blank-aware presence checks and a regex-based
`findFirst`, instead of a caller repeating `toLowerCase()` or
null-checking at every call site.

## Requirements

### Requirement: A value is absent when null or blank after trimming; null-checking is a separate, narrower question
`req~text-value.presence~1`

`isNull()` is `true` only when the backing `String` is `null`.
`isAbsent()` is `true` when the backing `String` is `null` **or**
blank after trimming — a whitespace-only value is absent but not null.
`isPresent()` is `!isAbsent()`. `orElse(alternative)` substitutes
`alternative` whenever `isAbsent()` is true, not only when the value is
`null`.

#### Scenario: A blank value is absent but not null

- **WHEN** `isNull()` and `isAbsent()` are both called on
  `Text.caseSensitive("   ")`
- **THEN** `isNull()` returns `false` and `isAbsent()` returns `true`

#### Scenario: orElse substitutes for blank as well as null

- **WHEN** `orElse("fallback")` is called on `Text.caseSensitive("   ")`
- **THEN** it returns `"fallback"`, even though the backing value is not
  `null`

### Requirement: Two Text values with a null backing string are never equal to each other
`req~text-value.null-value-equality~1`

`AbstractText.equals` delegates to a subclass's `isEqualTo(String)`, and
every `isEqualTo` implementation returns `false` unconditionally when the
receiver `isNull()` — including when the other `Text`'s value is also
`null`. Two "null-valued" `Text` instances are therefore never equal to
each other via `.equals()`.

#### Scenario: Two null-valued Text instances are not equal

- **WHEN** `Text.caseSensitive(null).equals(Text.caseSensitive(null))` is
  called
- **THEN** it returns `false`

### Requirement: CaseInsensitiveText compares and matches case-insensitively; converting to case-sensitive restores the original casing
`req~text-value.case-insensitive-matching~1`

`CaseInsensitiveText` lowercases its backing value at construction time,
but separately keeps the original, unlowercased text. `contains`,
`startsWith` and `endsWith` lowercase their argument before comparing;
`isEqualTo` uses `equalsIgnoreCase`. `toCaseSensitive()` builds a new
`CaseSensitiveText` from the preserved original text, not the lowercased
one. `CaseSensitiveText`'s equivalents do none of this lowercasing.

#### Scenario: startsWith is case-insensitive

- **WHEN** `startsWith("ONE")` is called on
  `Text.caseInsensitive("onetwo")`
- **THEN** it returns `true`

#### Scenario: toCaseSensitive restores the original casing

- **WHEN** `Text.caseInsensitive("OneTwo").toCaseSensitive().asString()`
  is called
- **THEN** it returns `"OneTwo"`, not `"onetwo"`

#### Scenario: CaseSensitiveText does not lowercase

- **WHEN** `startsWith("ONE")` is called on
  `Text.caseSensitive("onetwo")`
- **THEN** it returns `false`

### Requirement: findFirst always returns a case-insensitive result, or the shared empty value
`req~text-value.find-first~1`

`findFirst(regex)` compiles `regex` as a `java.util.regex.Pattern` and
matches it against `orElse("")` (an absent value is searched as empty
text). On a match, it returns `new CaseInsensitiveText(matcher.group(0))`
— always case-insensitive, even when called on a `CaseSensitiveText`.
When there is no match, it returns the shared `Text.empty` value.

#### Scenario: Match found

- **WHEN** `findFirst("[0-9]+")` is called on a `Text` containing digits
- **THEN** it returns a `CaseInsensitiveText` wrapping the first matched
  substring

#### Scenario: No match found

- **WHEN** `findFirst(regex)` is called and the pattern does not match
- **THEN** the shared `Text.empty` value is returned

### Requirement: asBytes() is empty for an absent value, UTF-8 otherwise
`req~text-value.as-bytes~1`

`asBytes()` returns `Bytes.empty` (see `bytes-value`) when `isAbsent()`
is true, and otherwise `Bytes.of(asString().getBytes(StandardCharsets.UTF_8))`
— always UTF-8, regardless of the platform default charset.

#### Scenario: Absent value yields empty Bytes

- **WHEN** `asBytes()` is called on an absent `Text` value
- **THEN** it returns `Bytes.empty`

#### Scenario: Present value is UTF-8 encoded

- **WHEN** `asBytes()` is called on a present `Text` value
- **THEN** the result equals `Bytes.of(text, StandardCharsets.UTF_8)`

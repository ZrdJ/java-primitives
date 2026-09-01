---
type: spec
title: Characters Value
updated: 2026-09-01
---

## Purpose

Wraps a `char[]` as a `Characters` value — a `CharSequence` with a
wipeable lifecycle via `destroy()` and literal substring replacement —
for secret-like text a caller wants to clear from memory once done with
it, instead of holding it only as an immutable `String`.

## Requirements

### Requirement: destroy() overwrites the underlying array with the character '0'
`req~characters-value.destroy~1`

`destroy()` overwrites every element of the underlying `char[]` with the
character `'0'` (the digit zero, not the null character `'\0'`), via
`Arrays.fill`.

#### Scenario: destroy() fills with '0'

- **WHEN** `destroy()` is called on a `Characters` value
- **THEN** every character returned by a subsequent `characters()` call
  is `'0'`

### Requirement: replace() replaces only the first occurrence; replaceAll() replaces every occurrence
`req~characters-value.replace-first-vs-all~1`

Despite the name matching `String.replace` (which replaces every
occurrence), `Characters.replace(actual, expected)` locates `actual` with
one `indexOf` call and replaces only that first occurrence.
`Characters.replaceAll(actual, expected)` is the one that loops until no
further occurrence remains — the two method names are the inverse of
`java.lang.String`'s `replace`/`replaceAll` in this respect. Both are
literal substring replacement, not regex-based (unlike `String.replaceAll`).
Neither method treats `actual` as a regular expression. When `actual` is
not found, both return the same `this` instance unchanged.

#### Scenario: replace() replaces only the first occurrence

- **WHEN** `replace("a", "X")` is called on `Characters.of("banana")`
- **THEN** the result is `"bXnana"`, not `"bXnXnX"`

#### Scenario: replaceAll() replaces every occurrence

- **WHEN** `replaceAll("a", "X")` is called on `Characters.of("banana")`
- **THEN** the result is `"bXnXnX"`

#### Scenario: No occurrence found returns the same instance

- **WHEN** `replace(actual, expected)` or `replaceAll(actual, expected)`
  is called with an `actual` text not present in the value
- **THEN** the exact same `Characters` instance is returned unchanged

### Requirement: Two Characters values are equal when their underlying char arrays are equal, across any implementation
`req~characters-value.equality~1`

`AbstractCharacters.equals` compares `Arrays.equals` on the two
`characters()` arrays as soon as the other object is any subtype of
`Characters`. `hashCode()` is `Arrays.hashCode` of the same array.

#### Scenario: Equal content is equal

- **WHEN** two `Characters` values hold the same character content
- **THEN** they are equal to each other, and share a hash code

### Requirement: subSequence returns a plain CharSequence, not a Characters value
`req~characters-value.subsequence-return-type~1`

Unlike `replace`/`replaceAll`, `subSequence(start, end)` returns
`new StringBuilder().append(characters()).subSequence(start, end)` — a
plain `CharSequence`, not a new `Characters` instance, so its result
cannot be `destroy()`ed.

#### Scenario: subSequence is not a Characters value

- **WHEN** `subSequence(start, end)` is called on a `Characters` value
- **THEN** the result is a `CharSequence` but not a `Characters` instance

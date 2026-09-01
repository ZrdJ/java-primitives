---
type: spec
title: Bytes Value
updated: 2026-09-01
---

## Purpose

Wraps a `byte[]` as a `Bytes` value with a consistent identity across every
implementation (plain, hashed, encrypted, key-derived — see `hashing` and
`cryptographic-byte-transforms`), a wipeable lifecycle via `destroy()`, and
conversion to a `String`, an `InputStream` or a `ByteSize` — instead of a
caller juggling raw `byte[]` arrays and `Arrays.equals`/`Arrays.fill`
itself.

## Requirements

### Requirement: A Bytes value can be constructed from a byte array, a String or nothing
`req~bytes-value.construction~1`

`Bytes.of(byte[])` wraps the array as-is. `Bytes.of(String)` and
`Bytes.of(String, Charset)` encode the text via `String.getBytes()` /
`String.getBytes(charset)` — the platform default charset when none is
given. `Bytes.empty()` returns the same shared zero-length instance every
time.

#### Scenario: From a String, default charset

- **WHEN** `Bytes.of("hello")` is constructed
- **THEN** its `bytes()` equal `"hello".getBytes()` (platform default
  charset)

#### Scenario: From a String, explicit charset

- **WHEN** `Bytes.of("hello", charset)` is constructed
- **THEN** its `bytes()` equal `"hello".getBytes(charset)`

#### Scenario: empty() returns the shared empty instance

- **WHEN** `Bytes.empty()` is called
- **THEN** it returns the same zero-length `Bytes` instance every time

### Requirement: Two Bytes values are equal when their underlying byte arrays are equal, across any implementation
`req~bytes-value.equality~1`

`AbstractBytes.equals` compares `Arrays.equals` on the two `bytes()`
arrays as soon as the other object is any subtype of `Bytes` (not only the
same concrete class) — so a plain `Bytes.Of` and, for example, a
`HashedBytes` holding the same content are equal. `hashCode()` is
`Arrays.hashCode` of the same array.

#### Scenario: Equal content across different Bytes implementations

- **WHEN** two `Bytes` values of different concrete types hold the same
  byte content
- **THEN** they are equal to each other, and share a hash code

#### Scenario: Different content is not equal

- **WHEN** two `Bytes` values hold different byte content
- **THEN** they are not equal

### Requirement: destroy() zero-fills the underlying array in place
`req~bytes-value.destroy~1`

`destroy()` overwrites every byte of the underlying array with `0` via
`Arrays.fill`. Because `bytes()` returns that same array (not a copy), the
value is unusable for its original content after `destroy()`.

#### Scenario: destroy() zero-fills the content

- **WHEN** `destroy()` is called on a `Bytes` value
- **THEN** every byte returned by a subsequent `bytes()` call is `0`

### Requirement: A Bytes value converts to a String, an InputStream and a ByteSize
`req~bytes-value.conversion~1`

`asString()` decodes the bytes with `new String(bytes())` (platform
default charset). `toInputStream()` wraps them in a
`ByteArrayInputStream`. `size()` returns a `ByteSize` (see
`byte-size-conversion`) reflecting `bytes().length`.

#### Scenario: asString

- **WHEN** `asString()` is called
- **THEN** it returns `new String(bytes())`

#### Scenario: toInputStream

- **WHEN** `toInputStream()` is called
- **THEN** it returns a `ByteArrayInputStream` over the same bytes

#### Scenario: size

- **WHEN** `size()` is called
- **THEN** the returned `ByteSize`'s byte count equals `bytes().length`

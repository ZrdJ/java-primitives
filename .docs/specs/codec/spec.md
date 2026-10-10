---
type: spec
title: Codec
updated: 2026-09-01
---

## Purpose

Lets a caller transcode bytes to and from text in hex, Base64 or
Base64-URL form through one `Codec` contract and a `Codecs` enum naming
the three implementations, and lets a `Bytes` value encode/decode itself
via `Bytes.encode(codec)`/`decode(codec)` — instead of reaching for
`java.util.Base64` or hand-rolling hex conversion directly.

## Requirements

### Requirement: Codecs names the three available transcodings
`req~codec.available-codecs~1`

`Codecs.Hex` wraps a `HexCodec`; `Codecs.Base64` wraps a `Base64Codec`
(`java.util.Base64`'s standard encoder/decoder); `Codecs.Base64Url` wraps
a `Base64UrlCodec` (`java.util.Base64`'s URL-safe encoder/decoder). Each
`Codecs` constant's `encode`/`encodeToString`/`decode` forward to its
wrapped `Codec`.

#### Scenario: Hex

- **WHEN** `Codecs.Hex.encodeToString(bytes)` is called
- **THEN** it returns the same text a `HexCodec` would produce directly

#### Scenario: Base64 vs Base64Url differ for URL-unsafe input

- **WHEN** the same bytes containing a `+` or `/` in their standard
  Base64 form are encoded with `Codecs.Base64` and with
  `Codecs.Base64Url`
- **THEN** the two resulting text values differ

### Requirement: Hex encoding is lowercase; decoding rejects odd length or non-hex characters
`req~codec.hex-format~1`

`HexCodec.encodeToString` produces lowercase hex digits (`0-9a-f`).
`decode` throws `IllegalArgumentException` when the input's length is
odd, and again when any two-character pair is not valid hex (via
`Character.digit(_, 16) == -1`).

#### Scenario: Encoding is lowercase

- **WHEN** `HexCodec` encodes a byte whose value is `0xAB`
- **THEN** the resulting text is `"ab"`, not `"AB"`

#### Scenario: Odd-length input is rejected

- **WHEN** `HexCodec.decode` is called with a byte array of odd length
- **THEN** `IllegalArgumentException` is thrown

#### Scenario: Non-hex characters are rejected

- **WHEN** `HexCodec.decode` is called with bytes that are not valid hex
  digits
- **THEN** `IllegalArgumentException` is thrown

### Requirement: A Bytes value can encode or decode itself with a given Codec
`req~codec.bytes-integration~1`

`Bytes.encode(codec)` returns `Bytes.of(codec.encode(this.bytes()))`;
`Bytes.decode(codec)` returns `Bytes.of(codec.decode(this.bytes()))`.

#### Scenario: Round trip through a Codec

- **WHEN** a `Bytes` value is encoded with `Codecs.Base64` and the result
  is decoded with the same codec
- **THEN** the decoded `Bytes` equals the original

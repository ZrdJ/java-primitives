---
type: spec
title: Byte Size Conversion
updated: 2026-09-01
---

## Purpose

Lets a caller convert a byte count to kilobytes, megabytes, gigabytes or
terabytes, and compare two `ByteSize` values, through `ByteSize` instead
of hand-rolling the division and choosing a unit base itself.

## Requirements

### Requirement: Unit conversion divides by 1000 per step (decimal/SI units, not binary)
`req~byte-size-conversion.decimal-units~1`

`kilobytes()` divides `bytes()` by `1000`; `megabytes()` divides
`kilobytes()` by `1000` again; `gigabytes()` divides `megabytes()` by
`1000`; `terrabytes()` (sic) divides `gigabytes()` by `1000`. Each
division uses scale 8 with `RoundingMode.HALF_UP`. This is the decimal
(SI, base-1000) convention, not the binary (base-1024) one.

#### Scenario: kilobytes

- **WHEN** `kilobytes()` is called on a `ByteSize` of `1000` bytes
- **THEN** it returns `1` (not the `1024`-byte binary kilobyte)

#### Scenario: megabytes is two divisions by 1000

- **WHEN** `megabytes()` is called on a `ByteSize` of `1_000_000` bytes
- **THEN** it returns `1`

### Requirement: ByteSize values compare and combine via the shared Calculatable contract
`req~byte-size-conversion.calculatable~1`

`ByteSize.OfBytes` implements `Calculatable<ByteSize>` (see
`numeric-calculation`) over its internal `BigDecimal2` byte count:
`plus`/`minus`/`divide` combine two sizes' byte counts, and
`compareTo`/`isGreaterThan`/etc. compare them.

#### Scenario: Adding two sizes

- **WHEN** `ByteSize.of(500).plus(ByteSize.of(500))` is called
- **THEN** the result's `bytes()` equal `1000`

#### Scenario: Comparing two sizes

- **WHEN** `ByteSize.of(2000).isGreaterThan(ByteSize.of(1000))` is called
- **THEN** it returns `true`

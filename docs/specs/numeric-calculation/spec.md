---
type: spec
title: Numeric Calculation
updated: 2026-09-01
---

## Purpose

Gives `BigDecimal`/`BigInteger` a fluent, operator-like API — `plus`,
`minus`, `divide`, `isGreaterThan`, `isEqualTo`, etc. — via `BigDecimal2`,
`BigInteger2` and the shared `Calculatable<T>` contract, instead of the
verbose `add`/`subtract`/`compareTo(...) > 0` form.

## Requirements

### Requirement: All comparisons, including isEqualTo, are based on compareTo, not equals
`req~numeric-calculation.comparison-uses-compareto~1`

`Calculatable`'s `isEqualTo`, `isNotEqualTo`, `isGreaterThan`,
`isGreaterThanOrEqualTo`, `isLessThan` and `isLessThanOrEqualTo` are all
default methods built on `compareTo`. Because `BigDecimal.compareTo`
(unlike `BigDecimal.equals`) ignores scale, `BigDecimal2.isEqualTo`
considers two values with different scales but the same numeric value
equal — a difference from `BigDecimal.equals` a caller coming from plain
`BigDecimal` should expect.

#### Scenario: isEqualTo ignores scale

- **WHEN** `new BigDecimal2(new BigDecimal("1.0")).isEqualTo(new BigDecimal2(new BigDecimal("1.00")))` is called
- **THEN** it returns `true`, even though `new BigDecimal("1.0").equals(new BigDecimal("1.00"))` is `false`

#### Scenario: isGreaterThan

- **WHEN** `new BigDecimal2(new BigDecimal("2")).isGreaterThan(new BigDecimal2(new BigDecimal("1")))` is called
- **THEN** it returns `true`

### Requirement: BigDecimal2 division defaults to scale 8, half-up; BigInteger2 division is plain integer division
`req~numeric-calculation.division-semantics~1`

`BigDecimal2.divide(other)` calls `divide(other, 8, RoundingMode.HALF_UP)`.
An explicit `divide(other, scale, mode)`,
`divideHalfUp(other, scale)` and `divideHalfDown(other, scale)` are also
available. `BigInteger2.divide(other)` instead delegates directly to
`BigInteger.divide` — integer division that truncates toward zero, with
no rounding mode to choose.

#### Scenario: BigDecimal2 default division rounds half-up at 8 decimal places

- **WHEN** `new BigDecimal2(new BigDecimal(1)).divide(new BigDecimal2(new BigDecimal(3)))` is called
- **THEN** the result is `0.33333333` (scale 8, half-up)

#### Scenario: BigInteger2 division truncates

- **WHEN** `new BigInteger2(BigInteger.valueOf(7)).divide(new BigInteger2(BigInteger.valueOf(2)))` is called
- **THEN** the result is `3`, not `3.5`

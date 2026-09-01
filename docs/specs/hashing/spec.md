---
type: spec
title: Hashing
updated: 2026-09-01
---

## Purpose

Lets a caller compute a message digest of a `Bytes` value — either
through a curated `HashAlgorithms` enum that reports whether each
algorithm is actually available on the running JVM, or by naming a JCE
algorithm directly — and render the digest as hex or base64 text, without
handling `NoSuchAlgorithmException` at the call site.

## Requirements

### Requirement: Each HashAlgorithms constant checks its own JVM availability once, at enum initialization
`req~hashing.algorithm-availability~1`

Each of the 13 `HashAlgorithms` constants (`SHA`, `SHA_224`, `SHA_256`,
`SHA_384`, `SHA_512`, `SHA_512_224`, `SHA_512_256`, `SHA3_224`,
`SHA3_256`, `SHA3_384`, `SHA3_512`, `MD2`, `MD5`) calls
`MessageDigest.getInstance(algorithm)` once in its constructor and caches
whether that succeeded; `isAvailable()` returns that cached result rather
than probing the JVM again on every call.

#### Scenario: isAvailable reflects the cached JVM check

- **WHEN** `isAvailable()` is called on a `HashAlgorithms` constant
- **THEN** it returns the result `MessageDigest.getInstance` produced for
  that algorithm's name at enum initialization, not a fresh probe

### Requirement: Hashing with an unavailable algorithm throws a NoSuchHashAlgorithmException naming it
`req~hashing.unavailable-algorithm~1`

`HashAlgorithms.hash(input)` re-resolves `MessageDigest.getInstance` at
call time; if that throws `NoSuchAlgorithmException`, it is wrapped as an
unchecked `NoSuchHashAlgorithmException` whose message names the
algorithm.

#### Scenario: Unavailable algorithm

- **WHEN** `hash(input)` is called on a `HashAlgorithms` constant whose
  `MessageDigest.getInstance` call fails
- **THEN** a `NoSuchHashAlgorithmException` naming that algorithm is
  thrown, not the underlying checked `NoSuchAlgorithmException`

### Requirement: A digest renders as hex, Base64 or Base64Url text via the shared codecs
`req~hashing.digest-text-rendering~1`

`Hash.toHex()`, `toBase64()` and `toBase64Url()` delegate to
`Codecs.Hex.encodeToString`, `Codecs.Base64.encodeToString` and
`Codecs.Base64Url.encodeToString` (see `codec`) on the digest's raw
bytes.

#### Scenario: toHex

- **WHEN** `toHex()` is called on a `Hash`
- **THEN** it returns `Codecs.Hex.encodeToString(value())`

### Requirement: Bytes can be hashed by a curated algorithm or by a raw algorithm name
`req~hashing.bytes-hashing-entry-points~1`

`Bytes.hash(algorithm)` calls the given `HashingAlgorithm.hash(bytes())`
and re-wraps the digest as `Bytes`. `HashedBytes(value, algorithm)`
instead accepts any JCE algorithm name as a `String`, resolving it via
`Exceptions.rethrowFunction` (see `exception-adapters`) so that an
unavailable algorithm surfaces as `IllegalStateException` rather than the
curated `NoSuchHashAlgorithmException`.

#### Scenario: Hashing via the curated enum

- **WHEN** `bytes.hash(HashAlgorithms.SHA_256)` is called
- **THEN** the result's content equals
  `HashAlgorithms.SHA_256.hash(bytes.bytes()).value()`

#### Scenario: Hashing via a raw algorithm name that is unavailable

- **WHEN** `new HashedBytes(value, algorithmName)` is constructed with an
  algorithm name the JVM does not provide
- **THEN** an `IllegalStateException` is thrown, not
  `NoSuchHashAlgorithmException`

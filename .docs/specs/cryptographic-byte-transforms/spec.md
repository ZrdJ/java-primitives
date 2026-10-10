---
type: spec
title: Cryptographic Byte Transforms
updated: 2026-09-01
---

## Purpose

Lets a caller AES-encrypt/decrypt a `Bytes` value with a key and salt, and
derive a key from a password-like `Bytes` value and a salt via PBKDF2 at
one of three preset strengths — through `Bytes.encryptAES`/`decryptAES`
and the `*EncryptedKeyBytes` classes — instead of assembling
`javax.crypto` `Cipher`/`SecretKeyFactory` calls by hand.

## Requirements

### Requirement: AES encryption and decryption build their key and parameters from the given Bytes, and wrap any failure
`req~cryptographic-byte-transforms.aes-encrypt-decrypt~1`

`Bytes.encryptAES(key, salt)` and `decryptAES(key, salt)` construct a
`SecretKeySpec` from `key.bytes()` with algorithm name `"AES"`, and a
`PBEParameterSpec` from `salt.bytes()` with an iteration count of `20`,
then call `Cipher.getInstance("AES")` and run `doFinal` on the payload's
bytes in `ENCRYPT_MODE`/`DECRYPT_MODE` respectively. Any exception during
setup or execution — including a wrong key on decryption — is wrapped as
an unchecked `IllegalStateException`.

#### Scenario: Round trip with the same key and salt

- **WHEN** a `Bytes` value is encrypted with `encryptAES(key, salt)` and
  the result is decrypted with `decryptAES(key, salt)` using the same
  key and salt
- **THEN** the decrypted `Bytes` equals the original

#### Scenario: Cipher failure is wrapped

- **WHEN** encryption or decryption fails for any reason (for example
  decrypting with the wrong key)
- **THEN** an `IllegalStateException` is thrown, wrapping the original
  cause, not the original checked exception

### Requirement: PBKDF2 key derivation offers three preset iteration strengths at a fixed 256-bit output
`req~cryptographic-byte-transforms.pbkdf2-key-derivation~1`

`PKDF2EncryptedKeyBytes(key, salt, iterations, hashsize)` derives a key
via `SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")`, using
`key.asString().toCharArray()` as the password material and `salt.bytes()`
as the salt. `LowEncryptedKeyBytes`, `MediumEncryptedKeyBytes` and
`HighEncryptedKeyBytes` are presets over it: `10_000`, `50_000` and
`100_000` iterations respectively, all at `256`-bit output. Any failure
is wrapped as an unchecked `IllegalStateException`.

#### Scenario: Preset iteration counts

- **WHEN** `LowEncryptedKeyBytes`, `MediumEncryptedKeyBytes` and
  `HighEncryptedKeyBytes` are each constructed with the same key and salt
- **THEN** they derive their key via `PBKDF2WithHmacSHA512` with
  `10_000`, `50_000` and `100_000` iterations respectively, each
  producing a 256-bit key

#### Scenario: Deterministic for the same input

- **WHEN** the same preset is constructed twice with the same key and
  salt
- **THEN** both derived keys are equal

---
type: index
title: java-primitives — Knowledge Layer
lang: en
updated: 2026-08-31
---

# java-primitives — Knowledge Layer

Collection of extended primitive convenience types — bytes (with AES encryption,
hashing, key derivation), characters, base64/hex codecs, numbers and text — for cases
the JDK's own primitives and wrappers don't cover.

Repo-specific knowledge. What concerns more than this repo lives in the knowledge layer
of the WS root (`~/workspaces/personal/docs/`).

## Folders

- `project/decisions/` — why things are the way they are (ADRs)
- `project/worklog/` — work logs, one file per day
- `project/research/` — self-collected material
- `project/sources/` — material delivered by others
- `wayfinding/` — undertakings whose path is not yet settled
- `changes/` — ongoing undertakings whose path is settled
- `archive/` — completed changes
- `specs/` — current state per capability

## Entry points

- `pom.xml` — coordinates (`com.github.zrdj:java-primitives`), Java 11, JUnit4/AssertJ test deps
- `src/main/java/com/github/zrdj/java/primitives/bytes/Bytes.java` — extended byte array type
- `src/main/java/com/github/zrdj/java/primitives/text/Text.java` — case-sensitive/insensitive text type
- `src/main/java/com/github/zrdj/java/primitives/codec/Codecs.java` — base64/hex codec factory
- `src/main/java/com/github/zrdj/java/primitives/hashing/Hash.java` — hashing entry point
- `README.md` (repo root) — Maven coordinates (usage section not yet written)

This repo does not (yet) have its own `CLAUDE.md` — working rules apply from
`zrdj/CLAUDE.md` and the provider levels above it.

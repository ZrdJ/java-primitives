---
type: research
title: README vs. code drift
updated: 2026-09-01
---

Found while deriving the as-is spec (see `../../specs/`).

## Version mismatch

`README.md`'s Maven snippet pins `<version>0.3.1</version>`; `pom.xml`
itself declares `<version>0.3.0</version>`. Same pattern as the other
three repos: `.github/workflows/release.yml` only rewrites `README.md`'s
`<version>` elements on release, never `pom.xml`'s own `<version>`.

## README has no usage section

`README.md`'s "Motivation" section is empty (matches what
`docs/README.md` already notes: "usage section not yet written"). Unlike
the other three repos, there is no worked example to compare the spec
against — nothing to drift.

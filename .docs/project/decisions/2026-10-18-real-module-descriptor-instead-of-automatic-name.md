---
type: decision
title: A real module descriptor, not an automatic module name
updated: 2026-10-18
status: accepted
origin: []
---

## Context

`tibbots/bots` is cut into named JPMS modules and wants to use this library from one of them. A
named module can only `requires` another named module. An automatic module (one derived from a
plain jar without a descriptor) works there too, but its name is derived from the jar filename, not
chosen by the library.

Checked directly against the published artifact
(`https://jitpack.io/com/github/zrdj/java-primitives/0.3.1/java-primitives-0.3.1.jar`): no
`module-info.class` and no `Automatic-Module-Name` manifest entry. Absent both, the JPMS filename
algorithm turns `java-primitives-0.3.1.jar` into the automatic module name `java.primitives` — a
name in the `java.*` namespace the JDK reserves for platform modules.

The same situation was resolved in `zrdj/java-properties` (pull request #18) for the same consumer,
and in the same way for `zrdj/java-filesearch` and `zrdj/java-identifiers`. In `java-properties` it
was measured that the reserved name compiles and runs today, but only because the consumer packages
into a single jar and starts from the classpath, so the runtime module system never resolves the
name.

## Decision

**Ship a real `module-info.java`, module name `com.github.zrdj.java.primitives`, matching the root
package.** No `Automatic-Module-Name` fallback: that manifest entry would need the same
non-`java.*` value anyway, and a real descriptor additionally states the module's dependencies and
its public surface explicitly.

Exported packages are all twelve that exist, since each of them holds public types:

- `com.github.zrdj.java.primitives.bytes` — `Bytes`, `ByteSize`
- `com.github.zrdj.java.primitives.bytes.encrypted` — `AESEncryptedBytes`, `AESDecryptedBytes`
- `com.github.zrdj.java.primitives.bytes.hash` — `HashedBytes`
- `com.github.zrdj.java.primitives.bytes.key` — `PKDF2EncryptedKeyBytes` and its `Low`, `Medium`
  and `High` presets
- `com.github.zrdj.java.primitives.characters` — `Characters`
- `com.github.zrdj.java.primitives.codec` — `Codec`, `Codecs`
- `com.github.zrdj.java.primitives.codec.base64` — `Base64Codec`, `Base64UrlCodec`
- `com.github.zrdj.java.primitives.codec.hex` — `HexCodec`
- `com.github.zrdj.java.primitives.exception` — `Exceptions`, `ExceptionFunction`,
  `ExceptionRunnable`, `FunctionExceptionHandler`, `RunnableExceptionHandler`
- `com.github.zrdj.java.primitives.hashing` — `Hash`, `HashingAlgorithm`, `HashAlgorithms`,
  `NoSuchHashAlgorithmException`
- `com.github.zrdj.java.primitives.numbers` — `Calculatable`, `BigDecimal2`, `BigInteger2`
- `com.github.zrdj.java.primitives.text` — `Text`, `AbstractText`, `CaseSensitiveText`,
  `CaseInsensitiveText`

No `requires` clause beyond the implicit `java.base`: the library has no compile or runtime
dependency, and everything it uses from the JDK — `java.math`, `java.security`, `javax.crypto`,
`java.util.regex`, `java.nio.charset` — lives in `java.base`.

## Rationale

- **The trigger is a latent failure, not a current one.** A consumer can use this library from a
  named module today; what it cannot do is choose the name it uses. The derived name lands in the
  namespace the JDK reserves for itself, and the first module-path start, `jlink` image, or JDK
  that tightens the check turns a green build red. No test in either repo would catch that
  beforehand, because the descriptor is the only place the name appears.
- **A descriptor is also documentation.** `requires java.primitives;` in a consumer reads as a
  platform module. `requires com.github.zrdj.java.primitives;` reads as what it is.
- **Every package is exported.** The implementation packages under `bytes` and `codec` are mostly
  reached through facades such as `Bytes.hash(...)`, `Bytes.encryptAES(...)` and `Codecs`, but their
  classes are `public` and reachable on the classpath today. Hiding any of them would break a
  consumer that instantiates `HexCodec` or `HighEncryptedKeyBytes` directly, and this change is
  meant to remove a latent problem, not to narrow the API.
- **No dependency means no module name to vouch for.** The descriptor requires nothing outside
  `java.base`, so it cannot be broken by a third-party library renaming or dropping its module name.
  The three dependencies in `pom.xml` (JUnit, AssertJ, `org.jusecase:builders`) are all in `test`
  scope and never reach a consumer.

## Consequences

- `pom.xml`'s `<version>` moves from `0.3.0` to `0.4.0`, catching up with the already-tagged
  `0.3.1` and going one further because this changes what JPMS consumers write, not a patch. The
  README's dependency snippet moves to `0.4.0` as well.
- `maven.compiler.source`/`target` stay at `11`, and no other build configuration changes:
  `maven-compiler-plugin` 3.16.0 detects `module-info.java` on its own, and the JUnit 4 tests keep
  running under the default surefire.
- Classpath consumers (an unnamed module, or a plain `<dependency>` without a `module-info.java` of
  its own) are unaffected: a modular jar keeps working unchanged when read from the classpath.
- A new package with public types has to be added to the descriptor, or it stays invisible to
  module-path consumers while classpath consumers still see it.
- Consumers already on `0.3.1` keep working. This release removes a latent problem; nothing is
  urgent about adopting it.

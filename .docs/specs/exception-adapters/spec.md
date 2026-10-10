---
type: spec
title: Exception Adapters
updated: 2026-09-01
---

## Purpose

Lets a caller run code that throws a checked `Exception` and either turn
any failure into an unchecked exception via a caller-supplied mapper, or
swallow it and produce a fallback value — through `Exceptions` — instead
of writing a `try`/`catch` around every checked call.

## Requirements

### Requirement: rethrow* runs the given code and maps any Exception to an unchecked one via the caller's mapper
`req~exception-adapters.rethrow~1`

`Exceptions.rethrow(runnable, mapper)`, `rethrowFunction(function, mapper)`,
`rethrowing(mapper)` and `rethrowingFunction(mapper)` all run the given
`ExceptionRunnable`/`ExceptionFunction` and, if it throws, call
`mapper.apply(exception)` and throw the resulting `RuntimeException`
instead of letting the checked exception propagate. On success, the
function variants return the wrapped call's result unchanged.

#### Scenario: Success passes the result through

- **WHEN** `Exceptions.rethrowFunction(function, mapper)` is called and
  `function.run()` completes normally
- **THEN** that same result is returned, and `mapper` is not called

#### Scenario: Failure is mapped and thrown

- **WHEN** `Exceptions.rethrowFunction(function, mapper)` is called and
  `function.run()` throws
- **THEN** `mapper.apply(exception)`'s `RuntimeException` is thrown
  instead of the original checked exception

### Requirement: fallback* runs the given code and swallows any Exception into a fallback value via the caller's mapper
`req~exception-adapters.fallback~1`

`Exceptions.fallbackFunction(function, mapper)` and
`fallbackingFunction(mapper)` run the given `ExceptionFunction` and, if it
throws, return `mapper.apply(exception)` instead of propagating. No
exception ever escapes these two entry points.

#### Scenario: Success passes the result through

- **WHEN** `Exceptions.fallbackFunction(function, mapper)` is called and
  `function.run()` completes normally
- **THEN** that same result is returned, and `mapper` is not called

#### Scenario: Failure produces the mapper's fallback value

- **WHEN** `Exceptions.fallbackFunction(function, mapper)` is called and
  `function.run()` throws
- **THEN** `mapper.apply(exception)` is returned, and no exception
  propagates

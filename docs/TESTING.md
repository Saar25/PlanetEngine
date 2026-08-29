# Testing

## Framework

Tests are written in Kotlin and use `kotlin.test` from the Kotlin standard
library. No third-party test frameworks (JUnit, MockK, AssertJ, ...) are used.

## Setup

Add the following to the module's `build.gradle.kts`:

```kotlin
dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
```

Run the tests with the Gradle wrapper:

```sh
./gradlew test
```

Run a single module's tests:

```sh
./gradlew :pe-math:test
```

## Location & naming

- Tests live in `src/test/kotlin/`, mirroring the package of the code under
  test (same layout as `src/main/kotlin/`).
- One test file per tested class/object, named `<Name>Test.kt` (e.g.
  `Vector3Test.kt`).
- Prefer plain backtick test method names that read like a sentence:

```kotlin
@Test
fun `of creates a vector with the given components`() { ... }
```

## AAA pattern

Every test is structured in three blocks:

1. **Arrange** — set up the inputs, objects and any expected value.
2. **Act** — invoke the code under test (exactly one call).
3. **Assert** — verify the outcome with `kotlin.test` assertions.

### `given*` fixture factories

In the **Arrange** block, create inputs through helper functions named with the
`given` prefix instead of raw constructors:

```kotlin
// Instead of:  val vec = Vector3f(1f, 2f, 0f)
val vec = givenVector3f(1f, 2f, 0f)
```

- Name each factory after the type it builds: `givenVector2f(x, y)`,
  `givenVector3f(x, y, z)`, `givenMatrix4f(...)`, etc.
- A factory takes plain values and returns a fresh instance, so every fixture is
  built with a single, deterministic call.
- Keep the factories small and define them in the test file, when might be used
  by other test files, put them in a shared test utility file; reuse them across
  tests instead of repeating the construction expression.

### `expected*` and `actual*` prefixes

- The value produced by the code under test is always stored in a variable
  with `actual` prefix.
- The reference value it is compared against is always stored in a variable
  with `expected` prefix.
- Assert with `assertEquals(expected*, actual*)` — the order matters,
  `expected` comes first.

## Assertions

Use `kotlin.test` assertions only:

| Assertion                              | Use when                               |
|----------------------------------------|----------------------------------------|
| `assertEquals(expected, actual)`       | comparing the produced value           |
| `assertTrue(condition)` / `assertFalse` | a boolean outcome                      |
| `assertNull(value)` / `assertNotNull(value)` | null-safety                      |
| `assertFailsWith<Type> { ... }`        | verifying an exception is thrown       |

Use the standard assertion functions (`assertNotEquals`, `assertSame`,
`assertContains`, `expect`, etc.) over hand-rolled checks.

## Guidelines

- Test one behavior per test method.
- Avoid mock; build real objects for the **Arrange** step.
- Tests must be deterministic — no randomness, no wall-clock timing, no I/O.
- Do not change production code to accommodate tests beyond what is reasonable
  (dependency injection, explicit constructor parameters).
- Prefer immutable `val`s, in line with the project's Kotlin conventions.
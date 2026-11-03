# Smooth Archon SpellPower Compat

Fabric 1.20.1 compatibility patch for Archon Renewed 1.1.5 and Spell Power 1.6.x. Redirects legacy `SpellSchool.attribute` access to the modern attribute API, preventing the affected startup crash without changing spell balance.

## Build

Use JDK 21 with the included wrapper; output targets Java 17.

```sh
./gradlew build
```

Install the regular JAR from `build/libs/` alongside Fabric API, Archon Renewed and Spell Power. Dependency versions are in `gradle.properties` and mod metadata.

## Source layout

`src/main/java` contains the guarded bytecode adapter and API bridge; `src/main/resources` contains mod and mixin metadata. The patch is version-specific; it is not a general Archon replacement.

## License

By Marj4n. See [LICENSE.txt](LICENSE.txt).

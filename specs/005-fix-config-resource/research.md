# Research: Fix Missing config.yml Resource

## Unknowns & Investigations

### Investigation 1: Why is config.yml missing from the JAR?
- **Finding**: I checked `src/main/resources/` earlier and it only contains `plugin.yml`.
- **Decision**: A default `config.yml` file must be created in `src/main/resources/`.
- **Rationale**: `JavaPlugin.saveDefaultConfig()` expects the resource to exist within the JAR. If the file isn't in the source resources directory, Gradle won't include it in the build.

### Investigation 2: Gradle processResources configuration
- **Finding**: The current `build.gradle` has `processResources` configured to filter `plugin.yml`. This is standard.
- **Decision**: No major changes needed to `build.gradle` for resource inclusion, as Gradle's `java` plugin automatically includes everything in `src/main/resources/`. However, we should ensure `config.yml` doesn't need variable expansion that might break it if not handled correctly.
- **Rationale**: Simple inclusion is enough.

### Investigation 3: ConfigManager Implementation
- **Finding**: The error log indicates `ConfigManager.loadConfig(ConfigManager.java:31)` calls `saveDefaultConfig()`, which fails.
- **Decision**: Once the file is added to resources, the existing logic should work. We will also verify if `reloadConfig()` or other methods are properly implemented.
- **Rationale**: Standard Spigot config handling.

## Best Practices
- **Resource Management**: Always place default configuration files in `src/main/resources/`.
- **Safe Loading**: Use `saveDefaultConfig()` only if the file doesn't exist on disk, which is what the Bukkit API does internally.

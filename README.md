# Welcome

[![Build and Release](https://github.com/gjchentw/Welcome/actions/workflows/ci.yml/badge.svg)](https://github.com/gjchentw/Welcome/actions/workflows/ci.yml)

[繁體中文](README_zh_TW.md)

**Welcome** is a Spigot/Paper plugin designed for modern Minecraft servers, aiming to implement a "community-driven" whitelist management mechanism. Through a voting system, online players collectively decide on the admission of new members, with automated CI/CD ensuring code quality and release reliability.

---

## Project Purpose

In many semi-open servers, whitelist review often consumes a lot of administrators' time. **Welcome** changes this:
- **Voting Whitelist**: Online players can cast a "welcome (vote)" for new players.
- **Real-time Autocomplete**: When a non-whitelisted player logs in, the system automatically and instantly adds them to the command completion list.
- **Automated Management**: When the welcome ratio reaches the set threshold (reusing the server's `playersSleepingPercentage`), the system automatically adds the target player to the whitelist.
- **Seamless Migration**: When upgrading from older versions, the system automatically migrates folder contents (v1.1.0+).

---

## Development Workflow

This project strictly adopts **Spec-Kit** for **Specification-Driven Development (SDD)**, ensuring every feature is traceable from design to implementation:

1.  **Specification (`/speckit.specify`)**: Define user stories and acceptance criteria.
2.  **Technical Plan (`/speckit.plan`)**: Conduct technical research, architectural design, and constitution checks.
3.  **Implementation (`/speckit.implement`)**: Perform TDD implementation according to the task list.

---

## Development Environment

- **JDK**: Java 25 (Minimum requirement)
- **Build System**: Gradle (Groovy DSL)
- **API**: Paper API 1.21.4 (Backwards compatible with Spigot)
- **Testing**: JUnit 5 + MockBukkit + Mockito

### Local Build
```bash
git clone https://github.com/gjchentw/Welcome.git
cd welcome
./gradlew clean build
```

---

## Installation & Configuration

1.  Download the latest version JAR from [Releases](https://github.com/gjchentw/Welcome/releases).
2.  Place it into the server's `plugins/` folder.
3.  Restart the server to generate default configuration files.

### Migration Notes (v1.1.0+)
If you are upgrading from an older version, the plugin will automatically detect the old directory and rename it. You don't need to move any config files manually.

### Key Configuration

#### `config.yml`
```yaml
# Whether to check if target player is already whitelisted
check-whitelist: true
# Whether to broadcast a message after successful whitelisting
broadcast-on-whitelist: true
# Language to use (en_US, zh_TW, zh_CN, ja_JP, la_US)
language: en_US
# Autocomplete settings
autocomplete:
  max-players: 100
```

#### Localization (`lang/` folder)
Messages are now managed by individual language files in the `lang/` directory.

---

## Usage

| Command | Permission | Description |
| :--- | :--- | :--- |
| `/welcome <player>` | `welcome.use` | Cast a welcome vote for a specific player (supports autocomplete) |
| `/welcome help` | `welcome.use` | Show command help |
| `/welcome reload` | `welcome.admin` | Reload configuration and language files |

---

## Trust & Quality

- **Automated Testing**: Core logic (voting calculation, ratio checking, cache filtering, data migration) is covered by JUnit 5.
- **Performance Guarantee**: Autocomplete response time is guaranteed within 100ms, using asynchronous caching to avoid affecting server TPS.
- **Continuous Integration**: Uses GitHub Actions to run tests on every Push, ensuring the `main` branch is always in a releasable state.

---

## License

This project is licensed under the [MIT License](LICENSE).

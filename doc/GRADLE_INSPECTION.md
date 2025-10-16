# Gradle Configuration Inspection Guide

This document describes how to inspect the Gradle configuration for the GeoGebra workspace.

## Overview

The GeoGebra project uses a composite Gradle build with multiple included builds:
- `source/build-logic` - Build conventions and plugins
- `source/shared` - Shared libraries
- `source/desktop` - Desktop application
- `source/web` - Web applications

## Inspection Tools

### 1. Using the Inspection Script

The easiest way to inspect the Gradle configuration is using the `inspect-gradle.sh` script:

```bash
# Show workspace configuration
./inspect-gradle.sh config

# List all projects
./inspect-gradle.sh projects

# Show applied plugins
./inspect-gradle.sh plugins

# Show settings configuration
./inspect-gradle.sh settings

# Show help
./inspect-gradle.sh help
```

### 2. Using Gradle Init Script

For more detailed inspection without triggering full project configuration:

```bash
./gradlew --init-script inspect-config.gradle inspectWorkspace
```

This provides comprehensive workspace information including:
- Root project details
- Included builds
- Properties
- Applied plugins
- Repositories

### 3. Using Built-in Gradle Tasks

For root project inspection (when the build is working):

```bash
# Show root project tasks
./gradlew inspectConfig

# Show all projects in workspace
./gradlew inspectProjects

# Show plugins on root project
./gradlew inspectPlugins

# Show settings configuration
./gradlew inspectSettings
```

### 4. Standard Gradle Inspection Commands

You can also use standard Gradle commands:

```bash
# List all projects in the build
./gradlew projects

# Show properties of the root project
./gradlew properties

# Show tasks available in root project
./gradlew tasks

# Show tasks in all projects
./gradlew tasks --all

# Show dependencies for a specific project
./gradlew :shared:common:dependencies

# Show build environment
./gradlew buildEnvironment
```

## Inspecting Specific Projects

To inspect a specific subproject without triggering configuration of all projects:

```bash
# Show tasks for desktop project
./gradlew :desktop:tasks

# Show dependencies for shared common project
./gradlew :shared:common:dependencies

# Show properties for web project
./gradlew :web:properties
```

## Troubleshooting

### Build Configuration Issues

If you encounter errors during configuration due to dependency resolution:

1. **Use offline mode** if you have cached dependencies:
   ```bash
   ./gradlew inspectConfig --offline
   ```

2. **Use the init script** which bypasses some configuration issues:
   ```bash
   ./gradlew --init-script inspect-config.gradle inspectWorkspace
   ```

3. **Check specific projects** individually:
   ```bash
   ./gradlew :shared:common:properties --offline
   ```

### Network Issues

If you see errors about `repo.geogebra.net`, this indicates:
- Network connectivity issues
- Missing dependencies in local cache
- VPN or firewall restrictions

Solutions:
- Use `--offline` mode if dependencies are cached
- Check network connectivity
- Contact repository administrators

## Understanding the Build Structure

### Composite Build Layout

```
geogebra/
├── settings.gradle.kts          # Root settings
├── build.gradle.kts             # Root build file
├── source/
│   ├── build-logic/             # Build conventions (composite build)
│   │   └── convention/          # Convention plugins
│   ├── shared/                  # Shared libraries (composite build)
│   │   ├── common/
│   │   ├── common-jre/
│   │   └── ...
│   ├── desktop/                 # Desktop app (composite build)
│   │   ├── desktop/
│   │   └── ...
│   └── web/                     # Web apps (composite build)
│       ├── web/
│       └── ...
```

### Convention Plugins

The build uses convention plugins from `source/build-logic/convention`:
- `java-conventions.gradle.kts` - Java project configuration
- `java-library-conventions.gradle.kts` - Java library configuration
- `gwt-conventions.gradle.kts` - GWT configuration
- `inspect-gradle-config.gradle.kts` - **Inspection tasks (new)**
- And more...

## Adding New Inspection Features

To add new inspection capabilities:

1. Edit `source/build-logic/convention/src/main/kotlin/inspect-gradle-config.gradle.kts`
2. Add new tasks following the existing pattern
3. Test with `./gradlew inspectConfig`
4. Update this documentation

Example:
```kotlin
tasks.register("inspectNewFeature") {
    group = "help"
    description = "Inspects a new feature"
    
    doLast {
        println("Inspection output here")
    }
}
```

## See Also

- [Gradle Documentation](https://docs.gradle.org)
- [Gradle Composite Builds](https://docs.gradle.org/current/userguide/composite_builds.html)
- [Gradle Init Scripts](https://docs.gradle.org/current/userguide/init_scripts.html)

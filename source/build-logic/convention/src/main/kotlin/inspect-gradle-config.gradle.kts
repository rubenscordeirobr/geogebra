/**
 * Gradle configuration inspection plugin
 * Provides tasks to inspect and understand the Gradle build configuration
 */

// Only register tasks on root project to avoid triggering subproject configuration
if (project == rootProject) {
    tasks.register("inspectConfig") {
        group = "help"
        description = "Displays comprehensive Gradle configuration information for the workspace"
        
        doLast {
            println("\n=== Workspace Information ===")
            println("Root Project: ${rootProject.name}")
            println("Project Directory: ${rootProject.projectDir}")
            println("Build Directory: ${rootProject.layout.buildDirectory.get().asFile}")
            println("Gradle Version: ${gradle.gradleVersion}")
            println("Gradle Home: ${gradle.gradleHomeDir}")
            println("Gradle User Home: ${gradle.gradleUserHomeDir}")
            
            println("\n=== Included Builds ===")
            gradle.includedBuilds.forEach { build ->
                println("- ${build.name} (${build.projectDir})")
            }
            
            println("\n=== Root Project Properties ===")
            rootProject.properties.toSortedMap().forEach { (key, value) ->
                if (!key.startsWith("org.gradle") && !key.startsWith("systemProp") && !key.startsWith("ant")) {
                    println("$key = $value")
                }
            }
            
            println("\n=== Available Inspection Tasks ===")
            println("- ./gradlew inspectConfig - Show workspace configuration")
            println("- ./gradlew inspectProjects - Show all projects in workspace")
            println("- ./gradlew inspectPlugins - Show plugins applied to root project")
            println("- ./gradlew inspectSettings - Show settings.gradle.kts configuration")
        }
    }
    
    tasks.register("inspectProjects") {
        group = "help"
        description = "Lists all projects in the workspace"
        
        doLast {
            println("\n=== Workspace Projects ===")
            println("Root: ${rootProject.name}")
            
            println("\n=== Included Builds ===")
            gradle.includedBuilds.forEach { build ->
                println("\nBuild: ${build.name}")
                println("  Location: ${build.projectDir}")
                // Note: We can't easily inspect subprojects of included builds
                // without triggering their configuration
            }
            
            println("\n=== Configuration Tip ===")
            println("To inspect a specific project's configuration:")
            println("  ./gradlew :project-path:properties")
            println("  ./gradlew :project-path:dependencies")
            println("  ./gradlew :project-path:tasks")
        }
    }
    
    tasks.register("inspectPlugins") {
        group = "help"
        description = "Shows plugins applied to the root project"
        
        doLast {
            println("\n=== Root Project Plugins ===")
            println("Project: ${rootProject.name}")
            println()
            
            rootProject.plugins.forEach { plugin ->
                println("- ${plugin.javaClass.simpleName}")
                println("  Class: ${plugin.javaClass.name}")
            }
        }
    }
    
    tasks.register("inspectSettings") {
        group = "help"
        description = "Shows Gradle settings configuration"
        
        doLast {
            println("\n=== Gradle Settings ===")
            println("Root project name: ${rootProject.name}")
            println("Settings directory: ${rootProject.rootDir}")
            
            println("\n=== Included Builds ===")
            gradle.includedBuilds.forEach { build ->
                println("- ${build.name}")
                println("  Path: ${build.projectDir}")
            }
            
            println("\n=== Configuration Files ===")
            println("Root settings.gradle.kts: ${rootProject.rootDir}/settings.gradle.kts")
            println("Root build.gradle.kts: ${rootProject.rootDir}/build.gradle.kts")
        }
    }
}



#!/bin/bash
# Gradle Configuration Inspection Script
# This script provides quick access to Gradle configuration inspection tasks

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# Function to show usage
show_usage() {
    echo "Gradle Configuration Inspection Tool"
    echo ""
    echo "Usage: $0 [command]"
    echo ""
    echo "Commands:"
    echo "  config      - Display workspace configuration"
    echo "  projects    - List all projects in the workspace"
    echo "  plugins     - Show plugins applied to root project"
    echo "  settings    - Show Gradle settings configuration"
    echo "  help        - Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 config"
    echo "  $0 projects"
    echo "  ./gradlew inspectConfig  # Alternative way"
}

# Parse command
COMMAND="${1:-help}"

case "$COMMAND" in
    config)
        ./gradlew inspectConfig --offline 2>/dev/null || ./gradlew inspectConfig
        ;;
    projects)
        ./gradlew inspectProjects --offline 2>/dev/null || ./gradlew inspectProjects
        ;;
    plugins)
        ./gradlew inspectPlugins --offline 2>/dev/null || ./gradlew inspectPlugins
        ;;
    settings)
        ./gradlew inspectSettings --offline 2>/dev/null || ./gradlew inspectSettings
        ;;
    help|--help|-h)
        show_usage
        ;;
    *)
        echo "Unknown command: $COMMAND"
        echo ""
        show_usage
        exit 1
        ;;
esac

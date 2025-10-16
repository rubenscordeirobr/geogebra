# syntax=docker/dockerfile:1

FROM eclipse-temurin:8-jdk

LABEL maintainer="GeoGebra Contributors"
LABEL description="Containerized build environment for GeoGebra"

# Install required tooling: git (for fetching dependencies), curl (for Gradle wrapper),
# nodejs/npm (for front-end tooling) and Python3 (for helper scripts used by Gradle tasks).
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        git \
        curl \
        unzip \
        nodejs \
        npm \
        python3 \
        python3-venv \
        ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Pre-create Gradle directory to improve caching and prevent root-owned build artifacts on the host.
ENV GRADLE_USER_HOME=/home/gradle/.gradle
RUN useradd --create-home --shell /bin/bash gradle \
    && mkdir -p "$GRADLE_USER_HOME" \
    && chown -R gradle:gradle /home/gradle

WORKDIR /workspace/geogebra

# Drop privileges to the gradle user for better security and to match expectations of tools.
USER gradle

# Default command prints Gradle help so that container can be invoked without additional arguments.
CMD ["./gradlew", "help"]

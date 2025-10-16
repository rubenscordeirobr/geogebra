# Docker-based build environment

This directory contains a Dockerfile that provisions all tooling required to build GeoGebra without
touching the host machine. The container bundles a Java 8 runtime, Node.js, Python utilities and the
Gradle wrapper so that both the desktop and web variants can be compiled in a reproducible way.

## Prerequisites

* [Docker](https://docs.docker.com/engine/install/) 20.10 or newer
* At least 6 GB of available memory for Gradle / GWT compilation steps

## Building the image

From the repository root run:

```bash
docker build -t geogebra-dev -f docker/Dockerfile .
```

This produces an image named `geogebra-dev` that you can reuse for multiple builds.

## Running Gradle tasks inside the container

Mount your working copy into the container to share sources and build artifacts with the host. The
example below launches the development web server (port 8888) using the Docker image:

```bash
docker run --rm -it \
  -v "$(pwd)":/workspace/geogebra \
  -p 8888:8888 \
  geogebra-dev \
  ./gradlew :web:run
```

You can replace `:web:run` with any other Gradle task, for instance `:desktop:run` to start the
Swing desktop client or `build` to assemble all modules. To generate the standalone math keyboard
bundle that produces `mathkeyboard.js`, run:

```bash
docker run --rm -it \
  -v "$(pwd)":/workspace/geogebra \
  geogebra-dev \
  ./gradlew :keyboard-web:dist
```

The resulting files will be written to `source/web/keyboard-web/build/dist/` inside your working
tree on the host machine.

## Tips

* The container runs as the non-root `gradle` user to avoid permission issues on generated files.
* The `GRADLE_USER_HOME` directory is stored under `/home/gradle/.gradle`. Mounting a host
  directory at this location allows Gradle to reuse caches between runs:

  ```bash
  docker run --rm -it \
    -v "$(pwd)":/workspace/geogebra \
    -v geogebra-gradle-cache:/home/gradle/.gradle \
    geogebra-dev ./gradlew build
  ```

* When running long-lived tasks like `:web:run`, you can pass `-p 8888:8888` to expose the
development server so it is reachable from your host browser.

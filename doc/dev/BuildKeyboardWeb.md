# Building the web math keyboard bundle

The math keyboard used by the GeoGebra web apps is compiled with the Google Web Toolkit
(GWT). Running the `keyboard-web` Gradle module produces the optimized JavaScript payloads
`mathkeyboard.js` and `mathkeyboard.nocache.js`.

## Prerequisites

* JDK 8 (the GWT toolchain currently targets Java 8 bytecode)
* Node.js 18 or newer for processing web dependencies
* Python 3 for auxiliary tooling used by the Gradle build
* At least 4 GB of free memory for the GWT compiler (8 GB recommended)

You can either install these dependencies locally or use the Docker based environment
provided in [`docker/README.md`](../../docker/README.md).

## One-time repository setup

Some composite builds are optional. If you do not have the internal `openrewrite`
recipes checked out under `source/openrewrite`, the build will automatically skip
that include. No extra action is required.

Make sure all submodules/dependencies are fetched before running Gradle commands:

```bash
./gradlew --version
```

The first run downloads the Gradle wrapper and plugin metadata into
`~/.gradle/`.

## Compiling `mathkeyboard.nocache.js`

Execute the GWT compiler from the repository root:

```bash
./gradlew :keyboard-web:compileGwt
```

Gradle writes the compiled artifacts to
`source/web/keyboard-web/build/gwt/out/mathkeyboard/`. The directory contains
`mathkeyboard.nocache.js` together with permutation specific cache files that GWT
needs for development deployments.

You can speed up development builds with optional flags:

* `-Pgdraft` – produce a draft (non-optimized) build more quickly
* `-Pgworkers=<n>` – limit the number of parallel GWT workers, e.g. `-Pgworkers=4`
* `-Pgreport` – generate a detailed compilation report under
  `build/gwt/report/mathkeyboard/`

## Creating `mathkeyboard.js`

The `keyboard-web` module also ships a convenience distribution task that renames
`mathkeyboard.nocache.js` to `mathkeyboard.js` and removes auxiliary artifacts. Run:

```bash
./gradlew :keyboard-web:dist
```

This task depends on `compileGwt` and creates a clean distribution directory at
`source/web/keyboard-web/build/dist/mathkeyboard/`. The folder contains the
single-file payload `mathkeyboard.js` that you can embed in other projects.

To regenerate the bundle from scratch, delete the build directory and rerun the task:

```bash
rm -rf source/web/keyboard-web/build
./gradlew :keyboard-web:dist
```

## Using the Docker build environment

If you are building inside the provided Docker image, mount the repository and run
Gradle just like on the host:

```bash
docker run --rm -it \
  -v "$(pwd)":/workspace/geogebra \
  geogebra-dev \
  ./gradlew :keyboard-web:dist
```

The compiled JavaScript files will be written to the mounted working tree on the
host machine under `source/web/keyboard-web/build/`.

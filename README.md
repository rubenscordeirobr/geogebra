# Welcome to GeoGebra!


This repository contains source code of [GeoGebra](https://www.geogebra.org)'s math apps.
It is available on a private GitLab instance and mirrored to GitHub.

Please read https://www.geogebra.org/license about GeoGebra's
licensing.

## Running the web version
To start the web version from command line, run

```
./gradlew :web:run
```

This will start a development server on your machine where you can test the app. 
If you need to access the server from other devices, you can specify a binding address

```
./gradlew :web:run -Pgbind=A.B.C.D
```

where `A.B.C.D` is your IP address. 
Then you can access the dev server through `http://A.B.C.D:8888`.
You can also run `./gradlew :web:tasks` to list other options.

## Building the math keyboard bundle

The standalone math keyboard used by the web apps lives in the `keyboard-web`
module. To generate the JavaScript payload run:

```
./gradlew :keyboard-web:dist
```

The compiled files are written to
`source/web/keyboard-web/build/dist/mathkeyboard/`. See
[`doc/dev/BuildKeyboardWeb.md`](doc/dev/BuildKeyboardWeb.md) for detailed
instructions and troubleshooting tips, including how to obtain
`mathkeyboard.nocache.js` for development builds.

## Running the desktop version (Classic 5)
To start the desktop version from command line, run

```
./gradlew :desktop:run
```
You can also run `./gradlew :desktop:tasks` to list other options.

## Setup the development environment

* Open IntelliJ. If you don't have IntelliJ on your computer yet
then you can download and install it from [here](https://www.jetbrains.com/idea/download)
* In the menu select File / New / Project from Version Control / Git
* In the new window add the following path: `https://git.geogebra.org/ggb/geogebra.git`
* Click on ‘Checkout’, select your preferred destination folder, select Java 1.8 as the SDK, 
click on OK and wait…
* After the project is checked out, select the root folder of the project, 
open the Run Anything tool (Double ^ on Mac) and run the following command: 
`./gradlew :web:run`
* After a minute or two the GWT UI will appear
* After the Startup URLs are loaded on the UI, select the app that you wish start. For example, 
if you select `graphing.html` and click on Launch Default Browser 
then the Graphing Calculator app with the newest features
will load and start in your default browser

## Using a containerized build environment

If you prefer to keep your host system clean, you can run GeoGebra's Gradle tasks inside a Docker
container that bundles all required tooling (Java 8, Node.js, Python utilities and the Gradle
wrapper). See [`docker/README.md`](docker/README.md) for detailed instructions on building the image
and running tasks such as `:web:run` or `:desktop:run` from within the container.

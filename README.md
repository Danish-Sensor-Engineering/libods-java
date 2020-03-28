DSE ODS Sensor Library
----------------------

Work in progress.


## Requirements

Java 8 - [OpenJDK](https://adoptopenjdk.net/), Oracle JDK, or any other Java JDK-8, is required to build and run.

We use the cross-platform [jSerialComm](https://fazecast.github.io/jSerialComm/) library for serial port communication.

## Development

To build and test

    ./gradlew clean build

On Windows execute ```gradlew.bat  build``` in a terminal, or use an IDE that support Gradle projects.


## Library Usage

TODO.


### Gradle

To use the library in a Gradle build, add our bintray repository and add the dependency.

    repositories {
        maven { url 'https://dl.bintray.com/mnellemann/libs' }
    }

    dependencies {
        compile 'biz.nellemann.libs:libsensor:1+'
    }



### Examples


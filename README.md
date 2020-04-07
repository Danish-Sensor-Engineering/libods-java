DSE ODS Sensor Library
----------------------

Work in progress.


## Requirements

Java 8 - [OpenJDK](https://adoptopenjdk.net/), Oracle JDK, or any other Java JDK-8, is required to build and run.

We use the cross-platform [jSerialComm](https://fazecast.github.io/jSerialComm/) library for serial port communication.

## Development

To build and test the library, checkout the code from this repository and use the Gradle build tool as shown:

    ./gradlew clean build

On Windows execute ```gradlew.bat``` in a terminal, or use an IDE that support Gradle projects.


## Library Usage

Create a SerialSensor object, specify TelegramHandler and open the correct serial port:

```java
    SerialSensor serialSensor = new SerialSensor();
    serialSensor.setTelegramHandler(new TelegramHandler16Bit());
    serialSensor.openPort("ttyUSB0", 38400);
    // Setup event listener ...
    serialSensor.closePort();
```

Measurements are obtained by implementing our **TelegramListener** interface by providing a **onEvent(TelegramEvent event)** method, which will be callled on each measurement received:

```java
    public class MyTest implements TelegramListener {
        @Override
        public void onEvent(TelegramEvent event) {
            System.out.println("Measurement: " + event.getMeasurement());
        }
    }
```

#### Gradle

To use the library in a Gradle build, add our bintray repository and add the required compile dependencies:


```groovy
    repositories {
        maven { url 'https://dl.bintray.com/mnellemann/libs' }
    }

    dependencies {
        compile 'biz.nellemann.libs:libsensor:1.0.2'      // Include the DSE library
        compile('com.fazecast:jSerialComm:[2.0.0,3.0.0)') // Include the jSerialComm library
    }
```


#### Maven

TODO.


### Examples

See [our full example](https://bitbucket.org/nellemann_biz/libsensor/src/master/src/main/java/biz/nellemann/libsensor/SerialDemo.java) for communicating with an 16Bit ODS Sensor through the serial port.

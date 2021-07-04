DSE ODS Java Library
---------------------

Java library for communicating with, and decoding measurement telegrams from Optical Displacement Sensors (ODS) made by [Danish Sensor Engineering](https://www.danish-sensor-engineering.com) (DSE).

## Requirements

Java 8 - [OpenJDK](https://adoptopenjdk.net/), Oracle JDK, or any other Java JDK-8, is required to build and run.

We use the cross-platform [jSerialComm](https://fazecast.github.io/jSerialComm/) library for serial port communication, which must also be included when using this library.

## Library Usage

Create a SerialSensor object, specify TelegramHandler and open the correct serial port:

```java
SerialSensor serialSensor = new SerialSensor();
serialSensor.setTelegramHandler(new TelegramHandler16Bit());
serialSensor.openPort("ttyUSB0", 38400);
// Setup event listener ...
serialSensor.closePort();
```

You obtain measurement results by implementing the **TelegramListener** interface, providing a **onTelegramResultEvent(TelegramResultEvent event)** method, which will be called on each measurement received:

```java
public class MyTest implements TelegramListener {
    @Override
    public void onTelegramResultEvent(TelegramResultEvent event) {
        System.out.println("Result: " + event.getMeasurement());
    }
}
```

#### Gradle

To use the library in a Gradle build, download the libsensor jar into *libs/* or another local folder, and include as shown:


```groovy
dependencies {
    implementation files('libs/libsensor-x.y.z.jar')         // Include the DSE library
    implementation('com.fazecast:jSerialComm:[2.0.0,3.0.0)') // Include the jSerialComm library
}
```


#### Maven

TODO.


### Examples

See [our full example](https://bitbucket.org/nellemann_biz/libsensor/src/master/src/main/java/biz/nellemann/libsensor/SerialDemo.java) for communicating with an 16Bit ODS Sensor through the serial port.


## Development

To build and test the library, checkout the code from this repository and use the Gradle build tool as shown:

    ./gradlew clean build

On Windows execute ```gradlew.bat``` in a terminal, or use an IDE that support Gradle projects.

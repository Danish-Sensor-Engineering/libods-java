/*
 * Copyright 2020 Danish Sensor Engineering ApS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package biz.nellemann.libsensor;

public class SerialDemo implements TelegramListener {

    final SerialSensor serialSensor;

    public static void main(final String[] args) {

        if(args.length < 1) {
            SerialSensor.printSerialPorts();
            return;
        }

        // Pass 1st argument into the SerialDemo constructor
        new SerialDemo(args[0]);
    }


    // Initialize with serial port name as argument
    public SerialDemo(String portName) {

        serialSensor = new SerialSensor();
        serialSensor.setTelegramHandler(new TelegramHandler16Bit());
        serialSensor.addEventListener(this);
        serialSensor.doAverageOver = 15;

        // Setup serial port, start listener and add observer (this Observer class)
        serialSensor.openPort(portName, 38400);

        // Keep running for some seconds
        try {
            Thread.sleep(5000L);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

        // Stop listener and close port
        serialSensor.removeEventListener(this);
        serialSensor.closePort();

    }


    @Override
    public void onTelegramResultEvent(TelegramResultEvent event) {
        System.out.println("Result: " + event.getMeasurement());
    }

    @Override
    public void onTelegramErrorEvent(TelegramErrorEvent event) {
        System.err.println("Error: " + event.toString());
    }

}

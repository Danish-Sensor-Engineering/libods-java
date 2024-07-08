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

package dse.libods;

import com.fazecast.jSerialComm.SerialPort;

import java.util.ArrayList;
import java.util.List;

/**
 * This is mostly a wrapper for the jSerialComm library
 * <a href="https://fazecast.github.io/jSerialComm/">...</a>
 */
public class SerialSensor extends Sensor {


    /**
     * Serial Port Setup
     */

    protected SerialPort serialPort;

    public SerialPort getSerialPort() {
        return serialPort;
    }


    private Thread readerThread;
    private SerialReader serialReader;

    public static void printSerialPorts() {
        final SerialPort[] serialPorts = SerialPort.getCommPorts();
        for (final SerialPort port : serialPorts) {
            System.out.println("Serial port: " + port.getSystemPortName() + " (" + port.getDescriptivePortName() +")");
        }
    }

    public static List<String> getSerialPorts() {
        final SerialPort[] serialPorts = SerialPort.getCommPorts();
        List<String> serialPortNames = new ArrayList<>(serialPorts.length);
        for (final SerialPort port : serialPorts) {
            serialPortNames.add(port.getSystemPortName());
        }
        return serialPortNames;
    }

    public void openPort(final String portName, final int baudRate) {
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(baudRate);
        serialPort.openPort();
        startReaderThread();
    }

    public void closePort() {
        if(serialPort != null && serialPort.isOpen()) {
            stopReaderThread();
            serialPort.removeDataListener();
            serialPort.closePort();
        }
    }

    private void startReaderThread() {
        if(serialPort != null && serialPort.isOpen()) {
            serialReader = new SerialReader(this);
            serialReader.start();
            readerThread = new Thread(serialReader);
            readerThread.start();
        }
    }

    private void stopReaderThread() {
        if(serialPort != null && serialPort.isOpen()) {
            serialReader.stop();
            try {
                readerThread.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }



}

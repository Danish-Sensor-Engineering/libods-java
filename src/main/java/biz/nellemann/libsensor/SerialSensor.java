package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;

import java.util.ArrayList;
import java.util.List;

/**
 * This is mostly a wrapper for the jSerialComm library
 * https://fazecast.github.io/jSerialComm/
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
        final SerialPort serialPorts[] = SerialPort.getCommPorts();
        for (final SerialPort port : serialPorts) {
            System.out.println("Serial port: " + port.getSystemPortName() + " (" + port.getDescriptivePortName() +")");
        }
    }

    public static List<String> getSerialPorts() {
        final SerialPort serialPorts[] = SerialPort.getCommPorts();
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
                e.printStackTrace();
            }
        }
    }



}

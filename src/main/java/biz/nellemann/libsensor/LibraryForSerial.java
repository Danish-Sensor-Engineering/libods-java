package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;

import java.util.ArrayList;
import java.util.List;

/**
 * This is mostly a wrapper for the jSerialComm library
 * https://fazecast.github.io/jSerialComm/
 */
public class LibraryForSerial extends Library {

    // Serialport
    protected SerialPort comPort;

    public static void printSerialPorts() {
        final SerialPort serialPorts[] = SerialPort.getCommPorts();
        for (final SerialPort port : serialPorts) {
            System.out.println("Serial port: " + port.getSystemPortName() + " (" + port.getDescriptivePortName() +")");
        }
    }

    public List<String> getSerialPorts() {
        final SerialPort serialPorts[] = SerialPort.getCommPorts();
        List<String> serialPortNames = new ArrayList<>(serialPorts.length);
        for (final SerialPort port : serialPorts) {
            serialPortNames.add(port.getSystemPortName());
        }
        return serialPortNames;
    }

    public void openPort(final String portName, final int baudRate) {
        comPort = SerialPort.getCommPort(portName);
        comPort.setBaudRate(baudRate);
        comPort.openPort();
        startListener();
    }

    public void closePort() {
        if(comPort != null && comPort.isOpen()) {
            stopListener();
            comPort.removeDataListener();
            comPort.closePort();
        }
    }

    private void startListener() {
        if(comPort != null && comPort.isOpen()) {
            comPort.addDataListener(listener);
        }
    }

    private void stopListener() {
        if(comPort != null && comPort.isOpen()) {
            comPort.removeDataListener();
        }
    }

}

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

    private Thread readerThread;
    private SerialReader serialReader;

    public LibraryForSerial(Telegram telegram) {
        this.telegram = telegram;
    }

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
        comPort = SerialPort.getCommPort(portName);
        comPort.setBaudRate(baudRate);
        comPort.openPort();
        startReaderThread();
    }

    public void closePort() {
        if(comPort != null && comPort.isOpen()) {
            stopReaderThread();
            comPort.removeDataListener();
            comPort.closePort();
        }
    }

    private void startReaderThread() {
        if(comPort != null && comPort.isOpen()) {
            serialReader = new SerialReader(comPort, telegram, eventListeners);
            serialReader.start();
            readerThread = new Thread(serialReader);
            readerThread.start();
            //comPort.addDataListener(listener);
        }
    }

    private void stopReaderThread() {
        if(comPort != null && comPort.isOpen()) {
            serialReader.stop();
            try {
                readerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //comPort.removeDataListener();
        }
    }



}

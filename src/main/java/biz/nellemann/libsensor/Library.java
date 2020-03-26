package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Library {

    // Inject logger
    final static Logger log = LoggerFactory.getLogger(Library.class);

    // Serialport
    protected SerialPort comPort;

    // Queue of measurements
    final protected TelegramDeque<Integer> telegramDeque = new TelegramDeque<>(5000);


    public void printDeque() {
        for(Integer meas : telegramDeque) {
            System.out.println(meas.toString());
        }
    }

    public Integer getTelegram() {
        return telegramDeque.pop();
    }


    /**
     * Serial port related
     */

    public void printSerialPorts() {
        final SerialPort serialPorts[] = SerialPort.getCommPorts();
        for (final SerialPort port : serialPorts) {
            System.out.println("Serial port: " + port.getSystemPortName() + " - " + port.getDescriptivePortName());
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
    }


    public void closePort() {
        if(comPort != null && comPort.isOpen()) {
            comPort.removeDataListener();
            comPort.closePort();
        }
    }


    public void startListener() {
        if(comPort != null && comPort.isOpen()) {
            TelegramListener16Bit listener = new TelegramListener16Bit(telegramDeque);
            comPort.addDataListener(listener);
        }
    }


    public void stopListener() {
        if(comPort != null && comPort.isOpen()) {
            comPort.removeDataListener();
        }
    }

}

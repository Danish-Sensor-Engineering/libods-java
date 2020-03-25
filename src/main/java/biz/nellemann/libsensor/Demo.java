package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;

public class Demo {

    // Inject logger
    final static Logger log = LoggerFactory.getLogger(Logger.class);

    // Serial port
    private SerialPort comPort;

    // Queue of measurements
    Deque<Integer> arrayDeque = new ArrayDeque<Integer>();

    public static void main(final String args[]) {

        final Demo serial = new Demo();
        if(args.length < 1) {
            serial.printSerialPorts();
            return;
        }

        try {
            serial.openPort(args[0]);
            Thread.sleep(2500l);
            serial.printDeque();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } finally {
            serial.closePort();
        }

    }

    public void printSerialPorts() {

        final SerialPort serialPorts[] = SerialPort.getCommPorts();
        for (final SerialPort port : serialPorts) {
            System.out.println("Serial port: " + port.getSystemPortName() + " - " + port.getDescriptivePortName());
        }

    }


    public void openPort(final String portName) {

        final int baudRate = 38400;

        comPort = SerialPort.getCommPort(portName);
        comPort.setBaudRate(baudRate);
        comPort.openPort();

        TelegramListener16Bit listener = new TelegramListener16Bit(arrayDeque);
        //PacketListener listener = new PacketListener();
        comPort.addDataListener(listener);

    }


    public void closePort() {

        if(comPort != null && comPort.isOpen()) {
            comPort.removeDataListener();
            comPort.closePort();
        }

    }


    public void printDeque() {
        for(Integer meas : arrayDeque) {
            System.out.println(meas.toString());
        }
    }


}

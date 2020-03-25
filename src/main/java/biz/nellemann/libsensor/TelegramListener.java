package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

import java.util.Arrays;
import java.util.Deque;

public abstract class TelegramListener implements SerialPortMessageListener {

    protected TelegramListener() {
    }

    TelegramListener(Deque deque) {
        throw new UnsupportedOperationException("Use either the 16bit or 18bit implementation.");
    }

    // Implemented as either 16bit or 18bit
    abstract protected int convert(byte data1, byte data2, byte data3);

    protected Deque<Integer> deque;


    public int toInt(byte[] message) {

        // Incorrect message size
        if(message.length != 3) {
            return 0;
        }

        byte data1 = message[0];
        byte data2 = message[1];
        byte data3 = message[2];

        int number = convert(data1, data2, data3);
        //log.info("Measurement: " + number + " from " + String.format("%02X ", header) + String.format("%02X ", data1) + String.format("%02X ", data2));

        return number;
    }

    public double toDouble(byte[] message) {
        int number = toInt(message);
        return ((double)number) / 10.0;
    }

    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

    @Override
    public boolean delimiterIndicatesEndOfMessage() { return false; }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] delimitedMessage = event.getReceivedData();

        // We split into two Datagram
        if(delimitedMessage.length == 6) {
            deque.push(this.toInt(Arrays.copyOfRange(delimitedMessage, 0, 3)));
            deque.push(this.toInt(Arrays.copyOfRange(delimitedMessage, 3, 6)));
        }
    }

}

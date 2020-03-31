package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

import java.util.*;

public abstract class TelegramListener extends Observable implements SerialPortMessageListener {

    protected Configuration configuration;

    protected TelegramListener() {
    }

   /*TelegramListener(Deque deque) {
        throw new UnsupportedOperationException("Use either the 16bit or 18bit implementation.");
    }*/

    // Implemented as either 16bit or 18bit
    abstract protected int convert(byte data1, byte data2, byte data3);

    protected int process(byte[] message) {

        // Incorrect message size
        if(message.length != 3) {
            return 0;
        }

        byte data1 = message[0];
        byte data2 = message[1];
        byte data3 = message[2];

        int number = convert(data1, data2, data3);
        //log.info("Measurement: " + number + " from " + String.format("%02X ", header) + String.format("%02X ", data1) + String.format("%02X ", data2));

        // TODO: Option to only get n results, mean result, etc.
        notifyObservers(Integer.valueOf(number));
        setChanged();
        return number;
    }

    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

    @Override
    public boolean delimiterIndicatesEndOfMessage() { return false; }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] delimitedMessage = event.getReceivedData();

        // We split into two
        if(delimitedMessage.length == 6) {
            process(Arrays.copyOfRange(delimitedMessage, 0, 3));
            process(Arrays.copyOfRange(delimitedMessage, 3, 6));
        }
    }

}

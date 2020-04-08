package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class SerialReader implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(SerialReader.class);

    private final Sensor sensor;
    private final SerialPort comPort;

    private final ArrayDeque<Integer> readBuffer = new ArrayDeque<>();
    private AtomicBoolean running = new AtomicBoolean(false);

    SerialReader(SerialSensor sensor) {
        this.sensor = sensor;
        this.comPort = sensor.getSerialPort();
    }


    @Override
    public void run() {

        int avgIntCounter = 0;
        int[] avgIntArray = new int[sensor.doAverageOver];

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
        try {

            byte[] tmpBuffer = new byte[1];
            //int bytesRead = 0;

            while (running.get()) {

                //bytesRead = comPort.readBytes(tmpBuffer, tmpBuffer.length);
                comPort.readBytes(tmpBuffer, tmpBuffer.length);
                readBuffer.add((int) tmpBuffer[0] & 0xFF); // NOTE: To get unsigned int !!!

/*              // If we read more than 1byte each time
                if(bytesRead > 4) {
                    for(int i = 0; i < bytesRead; i++) {
                        readBuffer.add((int) tmpBuffer[i] & 0xFF); // NOTE: To get unsigned int !!!
                        //log.info("Read: " + String.format("0x%02X / %02d", tmpBuffer[i], tmpBuffer[i]));
                    }
                }
*/

                // Remove non-header bytes from start of queue
                if(!sensor.telegramHandler.isHeader(readBuffer.peek())) {
                    readBuffer.remove();
                    continue;
                }

                //log.info("Post: " + readBuffer.peek());
                while(readBuffer.size() > 3) {

                    int measurement = sensor.telegramHandler.process(readBuffer);
                    if(sensor.doAverageOver > 0) {
                        //log.info("Adding to avg. buffer: " + measurement);
                        avgIntArray[avgIntCounter] = measurement;
                        avgIntCounter++;
                    } else {
                        //log.info("Send event: " + measurement);
                        sendEvent(measurement);
                    }

                    if(sensor.doAverageOver > 0 && avgIntCounter >= avgIntArray.length) {
                        avgIntCounter = 0;
                        Double avg = Arrays.stream(avgIntArray).average().orElse(Double.NaN);
                        //log.info("Send event: " + avg.intValue());
                        sendEvent(avg.intValue());
                    }

                    //log.info("Read buffer size: " + readBuffer.size());
                }


            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private synchronized void sendEvent(int measurement) {
        TelegramEvent event = new TelegramEvent( this, measurement );
        Iterator listeners = sensor.eventListeners.iterator();
        while( listeners.hasNext() ) {
            ( (TelegramListener) listeners.next() ).onEvent( event );
        }
    }

    public void start() {
        running.set(true);
    }

    public void stop() {
        running.set(false);
    }

}

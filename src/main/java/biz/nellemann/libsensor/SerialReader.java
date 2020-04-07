package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Arrays;
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

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        try {
            while (running.get()) {
                byte[] tmpBuffer = new byte[1];
                comPort.readBytes(tmpBuffer, tmpBuffer.length);
                //log.info(String.format("0x%02x", tmpBuffer[0]));
                readBuffer.add((int) tmpBuffer[0]);

                if(!sensor.telegramHandler.isHeader(readBuffer.peek())) {
                    readBuffer.remove();
                    continue;
                }

                while(readBuffer.size() > 3) {

                    int measurement = sensor.telegramHandler.process(readBuffer);
                    if(sensor.doAverageOver > 0) {
                        //System.out.println("Adding to avgbuffer");
                        avgIntArray[avgIntCounter] = measurement;
                        avgIntCounter++;
                    } else {
                        sendEvent(measurement);
                    }

                    if(sensor.doAverageOver > 0 && avgIntCounter >= avgIntArray.length) {
                        avgIntCounter = 0;
                        Double avg = Arrays.stream(avgIntArray).average().orElse(Double.NaN);
                        //System.out.println("Avg: " + avg.toString());
                        sendEvent(avg.intValue());
                    }

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

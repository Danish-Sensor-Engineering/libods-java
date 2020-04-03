package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class SerialReader implements Runnable {

    private final Sensor sensor;
    private final SerialPort comPort;

    private final Stack<Byte> readStack = new Stack<>();
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
                byte[] readBuffer = new byte[1];
                comPort.readBytes(readBuffer, readBuffer.length);
                readStack.push(readBuffer[0]);

                if(readStack.size() < 3 && !sensor.telegramHandler.isHeader(readStack.elementAt(0))) {
                    //System.out.println("Wrong size or not header?" + readStack.elementAt(0));
                    readStack.pop();
                    continue;
                }

                if(readStack.size() > 3) {
                    int measurement = sensor.telegramHandler.process(readStack);
                    if(sensor.doAverageOver > 0) {
                        //System.out.println("Adding to avgbuffer");
                        avgIntArray[avgIntCounter] = measurement;
                        avgIntCounter++;
                    } else {
                        sendEvent(measurement);
                    }
                }

                // Simple average
                if(sensor.doAverageOver > 0 && avgIntCounter >= avgIntArray.length) {
                    avgIntCounter = 0;
                    Double avg = Arrays.stream(avgIntArray).average().orElse(Double.NaN);
                    //System.out.println("Avg: " + avg.toString());
                    sendEvent(avg.intValue());
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

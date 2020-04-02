package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

public class SerialReader implements Runnable {

    private final SerialPort comPort;
    private final Telegram telegram;
    private final List<?> eventListeners;
    private final Stack<Byte> readStack = new Stack<>();
    private AtomicBoolean running = new AtomicBoolean(false);

    SerialReader(SerialPort comPort, Telegram telegram, List<?> eventListeners) {
        this.comPort = comPort;
        this.telegram = telegram;
        this.eventListeners = eventListeners;
    }


    @Override
    public void run() {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        try {
            while (running.get()) {
                byte[] readBuffer = new byte[1];
                comPort.readBytes(readBuffer, readBuffer.length);
                readStack.push(Byte.valueOf(readBuffer[0]));
                if(readStack.size() > 3 && telegram.isHeader(readStack.elementAt(0))) {
                    sendEvent(telegram.process(readStack));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }


    private synchronized void sendEvent(int measurement) {
        TelegramEvent event = new TelegramEvent( this, measurement );
        Iterator listeners = eventListeners.iterator();
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

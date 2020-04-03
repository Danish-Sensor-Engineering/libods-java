package biz.nellemann.libsensor;

import com.fazecast.jSerialComm.SerialPort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sensor {


    /**
     *  Telegram Handler Setup
     */
    protected TelegramHandler telegramHandler;

    public void setTelegramHandler(TelegramHandler telegramHandler) {
        this.telegramHandler = telegramHandler;
    }

    public TelegramHandler getTelegramHandler() {
        return telegramHandler;
    }




    /**
     * Event Listener Configuration
     */

    protected List<TelegramListener> eventListeners = new ArrayList<>();

    public synchronized void addEventListener( TelegramListener l ) {
        eventListeners.add( l );
    }

    public synchronized void removeEventListener( TelegramListener l ) {
        eventListeners.remove( l );
    }



    /**
     * Various Options
     */

    public int doAverageOver = 10;

}

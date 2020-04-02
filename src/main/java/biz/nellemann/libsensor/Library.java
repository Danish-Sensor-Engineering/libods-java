package biz.nellemann.libsensor;

import java.util.ArrayList;
import java.util.List;

public class Library {

    protected Telegram telegram;
    protected List<TelegramListener> eventListeners = new ArrayList<>();

    protected Library() { }

    public Library(Telegram telegram) {
        this.telegram = telegram;
    }

    public synchronized void addEventListener( TelegramListener l ) {
        eventListeners.add( l );
    }

    public synchronized void removeEventListener( TelegramListener l ) {
        eventListeners.remove( l );
    }

}

package biz.nellemann.libsensor;

import java.util.EventObject;

public class TelegramErrorEvent extends EventObject {

    private TelegramError error;

    public TelegramErrorEvent(Object source, TelegramError error ) {
        super( source );
        this.error = error;
    }

    public int getId() {
        return error.getId();
    }
    public String getMessage() {
        return error.getMessage();
    }

    public String toString() {
        return(error.getId() + " - " + error.getMessage());
    }
}

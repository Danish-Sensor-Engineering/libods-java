package biz.nellemann.libsensor;

import java.util.EventObject;

public class TelegramResultEvent extends EventObject {

    private int measurement;

    public TelegramResultEvent(Object source, int measurement ) {
        super( source );
        this.measurement = measurement;
    }

    public int getMeasurement() {
        return measurement;
    }
}

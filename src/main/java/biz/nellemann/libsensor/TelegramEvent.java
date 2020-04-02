package biz.nellemann.libsensor;

import java.util.EventObject;

public class TelegramEvent extends EventObject {

    private int measurement;

    public TelegramEvent(Object source, int measurement ) {
        super( source );
        this.measurement = measurement;
    }

    public int getMeasurement() {
        return measurement;
    }
}

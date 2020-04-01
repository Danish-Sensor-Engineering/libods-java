package biz.nellemann.libsensor;

import java.util.EventObject;

public class SensorEvent extends EventObject {

    private int measurement;

    public SensorEvent(Object source, int measurement ) {
        super( source );
        this.measurement = measurement;
    }

    public int getMeasurement() {
        return measurement;
    }
}

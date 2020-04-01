package biz.nellemann.libsensor;

import java.io.Serializable;

public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean doAverage = false;
    private int doAverageOver = 10;


}

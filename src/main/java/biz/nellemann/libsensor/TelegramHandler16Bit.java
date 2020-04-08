package biz.nellemann.libsensor;

public class TelegramHandler16Bit extends TelegramHandler {

    protected int convert(final int d1, final int d2, final int d3) {
        return 256 * d3 + d2;
    }

    protected boolean isHeader(int h) {
        if(h == 85 || h == 170)
            return true;

        return false;
    }

    public String toString() {
        return "16bit";
    }
}

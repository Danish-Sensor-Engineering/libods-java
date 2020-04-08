package biz.nellemann.libsensor;

public class TelegramHandler18Bit extends TelegramHandler {

    protected int convert(final int d1, final int d2, final int d3) {
        return (1024 * d3) + (4 * d2) + (d1 & 3);
    }

    protected boolean isHeader(int h) {
        if(h == 168 || h == 169 || h == 170 || h == 171)
            return true;

        if(h == 84 || h == 85 || h == 86 || h == 87)
            return true;

        return false;
    }

    public String toString() {
        return "18bit";
    }
}

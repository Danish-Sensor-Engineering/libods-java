package biz.nellemann.libsensor;

public class TelegramHandler18Bit extends TelegramHandler {

    protected int convert(final int data1, final int data2, final int data3) {
        return (1024 * data3) + (4 * data2) + (data1 & 3);
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

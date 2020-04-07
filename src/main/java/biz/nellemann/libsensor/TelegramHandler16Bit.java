package biz.nellemann.libsensor;

public class TelegramHandler16Bit extends TelegramHandler {

    protected int convert(final int data1, final int data2, final int data3) {
        return 256 * data3 + data2;
    }

    protected boolean isHeader(int h) {
        if(h == 170 || h == 85)
            return true;

        return false;
    }

    public String toString() {
        return "16bit";
    }
}

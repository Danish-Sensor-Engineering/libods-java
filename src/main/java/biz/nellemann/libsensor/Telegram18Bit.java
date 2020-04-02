package biz.nellemann.libsensor;

public class Telegram18Bit extends Telegram {

    protected int convert(final byte data1, final byte data2, final byte data3) {
        return 1024 * (data1 & 3) + 256 * data2 + data3;
    }

    protected boolean isHeader(byte b) {
        if(b == (byte)0xAA || b == (byte)0x55)
            return true;

        return false;
    }


}

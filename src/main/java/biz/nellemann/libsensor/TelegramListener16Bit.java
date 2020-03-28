package biz.nellemann.libsensor;

public class TelegramListener16Bit extends TelegramListener {

    /*TelegramListener16Bit(Deque deque) {
        this.deque = deque;
    }*/

    protected int convert(final byte data1, final byte data2, final byte data3) {
        return 256 * data2 + data3;
    }

    @Override
    public byte[] getMessageDelimiter() { return new byte[] { (byte)0x55 }; } // Could also be 0xAA
}

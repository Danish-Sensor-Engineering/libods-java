package biz.nellemann.libsensor;

import java.util.Deque;

public class TelegramListener16Bit extends TelegramListener {

    TelegramListener16Bit(Deque deque) {
        this.deque = deque;
    }

    protected int convert(byte data1, byte data2, byte data3) {
        return 256 * data2 + data3;
    }

    @Override
    public byte[] getMessageDelimiter() { return new byte[] { (byte)0x55 }; } // Could also be 0xAA
}

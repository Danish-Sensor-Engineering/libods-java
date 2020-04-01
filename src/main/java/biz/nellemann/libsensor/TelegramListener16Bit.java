package biz.nellemann.libsensor;

import java.util.ArrayDeque;

public class TelegramListener16Bit extends TelegramListener {

    private TelegramListener16Bit() {
    }

    TelegramListener16Bit(ArrayDeque deque) {
        this.rollingDeque = deque;
    }

    protected int convert(final byte data1, final byte data2, final byte data3) {
        return 256 * data2 + data3;
    }

    @Override
    public byte[] getMessageDelimiter() { return new byte[] { (byte)0x55 }; } // Could also be 0xAA
}

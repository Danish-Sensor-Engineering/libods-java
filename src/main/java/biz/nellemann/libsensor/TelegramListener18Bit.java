package biz.nellemann.libsensor;

import java.util.ArrayDeque;

public class TelegramListener18Bit extends TelegramListener {

    private TelegramListener18Bit() {
    }

    TelegramListener18Bit(ArrayDeque deque) {
        this.rollingDeque = deque;
    }

    protected int convert(final byte data1, final byte data2, final byte data3) {
        return 1024 * (data1 & 3) + 256 * data2 + data3;
    }

    @Override
    public byte[] getMessageDelimiter() { return new byte[] { (byte)0xA9 }; }
}

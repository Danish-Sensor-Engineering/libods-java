package biz.nellemann.libsensor;

import java.util.Stack;

public abstract class Telegram {

    // Implemented as either 16bit or 18bit in inherited class
    abstract protected int convert(byte data1, byte data2, byte data3);
    abstract protected boolean isHeader(byte b);

    // Get's a Stack<Byte> (byte[]) of at least 4 elements (0-3)
    protected int process(Stack<Byte> readStack) {

        if(readStack.size() < 4) {
            return -1;
        }

        // Call 16/18 bit specific conversion method
        int number = convert(readStack.pop(), readStack.pop(), readStack.pop());
        return number;
    }

}

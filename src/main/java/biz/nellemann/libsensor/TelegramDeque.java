package biz.nellemann.libsensor;

import java.util.ArrayDeque;

public class TelegramDeque<T> extends ArrayDeque<T> {

    private static final long serialVersionUID = 1L;

    private final int maxSize;

    public TelegramDeque(int maxSize) {
        this.maxSize = maxSize;
    }

    private boolean trim() {
        boolean changed = super.size() > maxSize;
        while (super.size() > maxSize) {
            super.remove();
        }
        return changed;
    }

    @Override
    public boolean add(T o) {
        boolean changed = super.add(o);
        boolean trimmed = trim();
        return changed || trimmed;
    }

    @Override
    public void push(T o) {
        super.push(o);
        trim();
    }


}

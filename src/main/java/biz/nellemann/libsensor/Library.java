package biz.nellemann.libsensor;

public class Library {

    // Max size of deque
    final protected int rollingDequeMaxSize = 5000;
    final protected int averageDequeMaxSize = 100;

    // Storage for our measurements
    final protected TelegramDeque<Integer> rollingDeque;
    final protected TelegramDeque<Integer> averageDeque;

    // We use the 16bit listener - forced for now - TODO: make selectable/configurable later
    final protected TelegramListener16Bit listener;

    public Library() {
        rollingDeque = new TelegramDeque<>(rollingDequeMaxSize);
        averageDeque = new TelegramDeque<>(averageDequeMaxSize);

        listener = new TelegramListener16Bit(rollingDeque);
    }

    public int getLastMeasurement() {
        return rollingDeque.pop();
    }

    public int getLastAverageMeasurement() {
        return averageDeque.pop();
    }

    public synchronized void addEventListener( SensorListener l ) {
        listener.eventListeners.add( l );
    }
    public synchronized void removeEventListener( SensorListener l ) {
        listener.eventListeners.remove( l );
    }

}

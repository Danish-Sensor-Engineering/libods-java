package biz.nellemann.libsensor;

import java.util.Observable;
import java.util.Observer;

public class SerialDemo implements Observer {

    final SerialLibrary serialLibrary = new SerialLibrary();
    final protected TelegramDeque<Integer> telegramDeque = new TelegramDeque<>(5000);
    final protected TelegramListener16Bit listener = new TelegramListener16Bit();


    public static void main(final String args[]) {

        if(args.length < 1) {
            SerialLibrary.printSerialPorts();
            return;
        }

        // Pass 1st argument into the SerialDemo constructor
        new SerialDemo(args[0]);
    }


    // Initialize with serial port name as argument
    public SerialDemo(String portName) {

        // Setup serial port, start listener and add observer (this Observer class)
        serialLibrary.openPort(portName, 38400);
        serialLibrary.startListener(listener);
        listener.addObserver(this);

        // Keep running for some seconds and terminate
        try {
            Thread.sleep(2500L);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

        // Stop listener and close port
        serialLibrary.stopListener();
        serialLibrary.closePort();

    }


    // This method is called when measurements arrive
    public void update(Observable obj, Object arg ) {

        // Put into stack for later use ?
        telegramDeque.push((int)arg);

        // Print to screen
        System.out.println( arg );
    }

}

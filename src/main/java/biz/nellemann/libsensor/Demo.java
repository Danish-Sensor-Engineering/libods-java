package biz.nellemann.libsensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo {

    // Inject logger
    final static Logger log = LoggerFactory.getLogger(Logger.class);

    public static void main(final String args[]) {

        final Library library = new Library();
        if(args.length < 1) {
            library.printSerialPorts();
            return;
        }

        Thread t1 = new Thread();

        try {
            library.openPort(args[0], 38400);
            library.startListener();
            Thread.sleep(2500l);
            library.printDeque();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } finally {
            library.stopListener();
            library.closePort();
        }

    }

}

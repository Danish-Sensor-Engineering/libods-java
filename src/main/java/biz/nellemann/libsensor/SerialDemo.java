package biz.nellemann.libsensor;

public class SerialDemo implements TelegramListener {

    final Telegram telegram;
    final Configuration config;
    final LibraryForSerial libraryForSerial;


    public static void main(final String args[]) {

        if(args.length < 1) {
            LibraryForSerial.printSerialPorts();
            return;
        }

        // Pass 1st argument into the SerialDemo constructor
        new SerialDemo(args[0]);
    }


    // Initialize with serial port name as argument
    public SerialDemo(String portName) {

        config = new Configuration();
        config.doAverageOver = 25;

        telegram = new Telegram16Bit();
        libraryForSerial = new LibraryForSerial(telegram, config);

        // Setup serial port, start listener and add observer (this Observer class)
        libraryForSerial.openPort(portName, 38400);
        libraryForSerial.addEventListener(this);

        // Keep running for some seconds
        try {
            Thread.sleep(50000L);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

        // Stop listener and close port
        libraryForSerial.removeEventListener(this);
        libraryForSerial.closePort();

    }


    @Override
    public void onEvent(TelegramEvent event) {
        System.out.println("Measurement: " + event.getMeasurement());
    }

}

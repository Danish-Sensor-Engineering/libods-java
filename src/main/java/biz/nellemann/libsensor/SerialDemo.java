package biz.nellemann.libsensor;

public class SerialDemo implements TelegramListener {

    final SerialSensor serialSensor;

    public static void main(final String args[]) {

        if(args.length < 1) {
            SerialSensor.printSerialPorts();
            return;
        }

        // Pass 1st argument into the SerialDemo constructor
        new SerialDemo(args[0]);
    }


    // Initialize with serial port name as argument
    public SerialDemo(String portName) {

        serialSensor = new SerialSensor();
        serialSensor.setTelegramHandler(new TelegramHandler16Bit());
        serialSensor.addEventListener(this);
        serialSensor.doAverageOver = 15;

        // Setup serial port, start listener and add observer (this Observer class)
        serialSensor.openPort(portName, 38400);

        // Keep running for some seconds
        try {
            Thread.sleep(2000L);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

        // Stop listener and close port
        serialSensor.removeEventListener(this);
        serialSensor.closePort();

    }


    @Override
    public void onEvent(TelegramEvent event) {
        System.out.println("Measurement: " + event.getMeasurement());
    }

}

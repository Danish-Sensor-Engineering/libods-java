package biz.nellemann.libsensor

import com.fazecast.jSerialComm.SerialPort
import com.fazecast.jSerialComm.SerialPortEvent
import spock.lang.Shared
import spock.lang.Specification

class TelegramListenerTest extends Specification {

    def "test 16bit telegram to int measurement conversion"() {
        setup:
        def telegramListener16Bit = new TelegramListener16Bit(new ArrayDeque())
        byte[] message = [0x55, 0x27, 0x47]

        when:
        def result = telegramListener16Bit.getUnit(message)

        then:
        result == 10055
    }


    def "test 18bit telegram to int measurement conversion"() {
        setup:
        def telegramListener18Bit = new TelegramListener18Bit(new ArrayDeque())
        byte[] message = [0xA9, 0x27, 0x47]

        when:
        def result = telegramListener18Bit.getUnit(message)

        then:
        result == 11079
    }

    def "test event handler"() {
        setup:
        def serialPort = SerialPort.getCommPort("ttyS0")
        def deque = new TelegramDeque<Integer>(5)
        def telegramListener16Bit = new TelegramListener16Bit(deque)
        byte[] message = [0x55, 0x27, 0x47, 0xAA, 0x27, 0x47]

        when:
        telegramListener16Bit.serialEvent(
            new SerialPortEvent(serialPort, SerialPort.LISTENING_EVENT_DATA_RECEIVED, message)
        )

        then:
        deque.size() == 2

    }
}



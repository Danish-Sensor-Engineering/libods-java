package biz.nellemann.libsensor

import spock.lang.Specification

class TelegramListenerTest extends Specification {

    def "test 16bit telegram to int measurement conversion"() {
        setup:
        def telegramListener16Bit = new TelegramListener16Bit()
        byte[] message = [0x55, 0x27, 0x47]

        when:
        def result = telegramListener16Bit.toInt(message)

        then:
        result == 10055
    }


    def "test 18bit telegram to int measurement conversion"() {
        setup:
        def telegramListener18Bit = new TelegramListener18Bit()
        byte[] message = [0xA9, 0x27, 0x47]

        when:
        def result = telegramListener18Bit.toInt(message)

        then:
        result == 11079
    }

}



package biz.nellemann.libsensor

import spock.lang.Specification

class TelegramHandlerSpec extends Specification {

    def telegram16Bit = new TelegramHandler16Bit()

    def "test processing of correct byte stack"() {

        setup:
        Stack<Byte> telegram = new Stack<>();
        telegram.add((byte)0x55)
        telegram.add((byte)0x27)
        telegram.add((byte)0x47)
        telegram.add((byte)0xAA)

        when:
        def number = telegram16Bit.process(telegram)

        then:
        number > 0

    }

    def "test processing of small byte stack"() {

        setup:
        Stack<Byte> telegram = new Stack<>();
        telegram.add((byte)0x55)
        telegram.add((byte)0x27)
        telegram.add((byte)0x47)

        when:
        def number = telegram16Bit.process(telegram)

        then:
        number == -1

    }

}



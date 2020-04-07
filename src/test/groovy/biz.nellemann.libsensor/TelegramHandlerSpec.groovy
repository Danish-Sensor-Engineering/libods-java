package biz.nellemann.libsensor

import spock.lang.Specification

class TelegramHandlerSpec extends Specification {

    def telegram16Bit = new TelegramHandler16Bit()

    def "test processing of correct byte stack"() {

        setup:
        ArrayDeque<Integer> telegram = new ArrayDeque<>();
        telegram.add(85)
        telegram.add(139)
        telegram.add(78)
        telegram.add(170)

        when:
        def number = telegram16Bit.process(telegram)

        then:
        number > 0

    }

    def "test processing of small byte stack"() {

        setup:
        ArrayDeque<Integer> telegram = new ArrayDeque<>();
        telegram.add(85)
        telegram.add(139)
        telegram.add(78)

        when:
        def number = telegram16Bit.process(telegram)

        then:
        number == -1

    }

}



package biz.nellemann.libsensor

import spock.lang.Specification

class TelegramDequeTest extends Specification {

    def "make sure we do not grow above maxLimit"() {
        setup:
        def telegramDeque = new TelegramDeque<Integer>(3)
        telegramDeque.add(1)
        telegramDeque.add(2)
        telegramDeque.add(3)

        when:
        telegramDeque.add(4)

        then:
        telegramDeque.size() == 3
    }

}

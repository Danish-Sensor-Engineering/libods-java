package biz.nellemann.libsensor

import spock.lang.Specification

class TelegramDequeTest extends Specification {

    def "test our maxSize boundary"() {
        setup:
        def telegramDeque = new TelegramDeque<Integer>(3)
        telegramDeque.push(1)
        telegramDeque.push(2)
        telegramDeque.push(3)

        when:
        telegramDeque.push(4)

        then:
        telegramDeque.size() == 3
    }

}

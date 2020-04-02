package biz.nellemann.libsensor

import spock.lang.Specification

class Telegram18BitSpec extends Specification {

    def telegram18Bit = new Telegram18Bit()

    def "test 18bit telegram to int conversion"() {
        when:
        def result = telegram18Bit.convert((byte)0x55, (byte)0x27, (byte)0x47)

        then:
        result == 11079
    }

    /* TODO: Fix for correct 18bit headers
    def "test header detection"() {
        expect: 'Should return true on valid header'
        telegram18Bit.isHeader((byte)a) == b

        where:
        a    || b
        0x55 || false
        0xAA || false
    }*/

}

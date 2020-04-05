package biz.nellemann.libsensor

import spock.lang.Specification

class TelegramHandler18BitSpec extends Specification {

    def telegram18Bit = new TelegramHandler18Bit()

    def "test 18bit telegram to int conversion"() {
        when:
        def result = telegram18Bit.convert((byte)0x55, (byte)0x27, (byte)0x47)

        then:
        result == 11079
    }

    def "test header detection"() {
        expect: 'Should return true on valid header'
        telegram18Bit.isHeader((byte)a) == b

        where:
        a    || b
        0x55 || false
        0x84 || true
        0x85 || true
        0x86 || true
        0x87 || true
        0xAA || false
        0x168 || true
        0x169 || true
        0x170 || true
        0x171 || true
    }

}

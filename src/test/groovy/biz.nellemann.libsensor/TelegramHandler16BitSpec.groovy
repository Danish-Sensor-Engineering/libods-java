package biz.nellemann.libsensor;

import spock.lang.Specification;

class TelegramHandler16BitSpec extends Specification {

    def telegram16Bit = new TelegramHandler16Bit()

    def "test 16bit telegram to int conversion"() {
        expect: 'Should return correct distance based on telegram'
        telegram16Bit.convert(a, b, c) == d

        where:
        a   | b   | c  || d
        170 | 139 | 78 || 20107
         85 | 109 | 82 || 21101
        170 | 207 | 74 || 19151
        170 | 195 | 87 || 22467
    }

    def "test header detection"() {
        expect: 'Should return true on valid header'
        telegram16Bit.isHeader(a) == b

        where:
        a   || b
        170 || true
        85  || true
        72  || false
    }
}


package biz.nellemann.libsensor;

import spock.lang.Specification;

class TelegramHandler16BitSpec extends Specification {

    def telegram16Bit = new TelegramHandler16Bit()

    def "test 16bit telegram to int conversion"() {
        when:
        def result = telegram16Bit.convert((byte)0x55, (byte)0x27, (byte)0x47)

        then:
        result == 10055
    }

    def "test header detection"() {
        expect: 'Should return true on valid header'
        telegram16Bit.isHeader((byte)a) == b

        where:
        a    || b
        0x55 || true
        0xAA || true
        0x27 || false
    }
}


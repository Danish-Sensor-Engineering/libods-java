/*
 * Copyright 2020 Danish Sensor Engineering ApS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dse.libsensor;

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


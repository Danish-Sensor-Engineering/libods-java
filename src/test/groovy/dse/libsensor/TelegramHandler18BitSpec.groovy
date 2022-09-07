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

package dse.libsensor

import spock.lang.Specification

class TelegramHandler18BitSpec extends Specification {

    def telegram18Bit = new TelegramHandler18Bit()

    def "test 18bit telegram to int conversion"() {
        expect: 'Should return correct distance based on telegram'
        telegram18Bit.convert(a, b, c) == d

        where:
        a   | b   | c   || d
        169 | 192 | 121 || 124673
         87 | 122 | 121 || 124395
        168 | 150 | 120 || 123480
         85 |   0 | 120 || 122881
        171 |  21 | 123 || 126039
    }

    def "test header detection"() {
        expect: 'Should return true on valid header'
        telegram18Bit.isHeader(a) == b

        where:
        a    || b
        168 || true
        169 || true
        170 || true
        171 || true
         84 || true
         85 || true
         86 || true
         87 || true
        101 || false
        127 || false
         82 || false
    }

}

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



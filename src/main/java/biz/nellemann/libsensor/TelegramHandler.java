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

package biz.nellemann.libsensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;

public abstract class TelegramHandler {

    private final static Logger log = LoggerFactory.getLogger(TelegramHandler.class);

    // Implemented as either 16bit or 18bit in inherited class
    abstract protected int convert(int d1, int d2, int d3);
    abstract protected boolean isHeader(int h);

    // Get's a Stack<Byte> (byte[]) of at least 4 elements (0-3)
    protected synchronized int process(ArrayDeque<Integer> readBuffer) {

        if(readBuffer.size() < 4) {
            return -1;
        }

        int b1 = readBuffer.remove();
        int b2 = readBuffer.remove();
        int b3 = readBuffer.remove();
        //log.info("Bytes: " + b1 + ", " + b2 + ", " + b3);

        // Call 16/18 bit specific conversion method
        int distance = convert(b1, b2, b3);

        return distance;
    }

}

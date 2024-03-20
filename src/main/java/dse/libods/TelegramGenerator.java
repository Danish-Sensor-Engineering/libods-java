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

package dse.libods;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class TelegramGenerator implements Runnable {

    private final Sensor sensor;

    private final ArrayDeque<Integer> readBuffer = new ArrayDeque<>();
    private final AtomicBoolean running = new AtomicBoolean(false);

    TelegramGenerator(TestSensor sensor) {
        this.sensor = sensor;
    }


    @Override
    public void run() {

        int n = 100;

        while (running.get()) {

            // TODO: Generate better semi random telegrams
            readBuffer.add(170);
            readBuffer.add(139);
            readBuffer.add(n++);

            // Convert telegram to measurement
            int measurement = sensor.telegramHandler.process(readBuffer);

            // Sends event with measurement to any/all listeners
            sensor.onMeasurement(measurement);

            // Sleep for some time ...
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(n == Integer.MAX_VALUE) {
                n = 100;
            }

        }

    }


    public void start() {
        running.set(true);
    }

    public void stop() {
        running.set(false);
    }

}

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

import com.fazecast.jSerialComm.SerialPort;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class SerialReader implements Runnable {

    private final Sensor sensor;
    private final SerialPort comPort;

    private final ArrayDeque<Integer> readBuffer = new ArrayDeque<>();
    private final AtomicBoolean running = new AtomicBoolean(false);

    SerialReader(SerialSensor sensor) {
        this.sensor = sensor;
        this.comPort = sensor.getSerialPort();
    }


    @Override
    public void run() {

        sensor.clear();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
        try {

            // TODO: Make configurable ?
            byte[] tmpBuffer = new byte[1];
            //int bytesRead = 0;

            while (running.get()) {

                comPort.readBytes(tmpBuffer, tmpBuffer.length);
                readBuffer.add((int) tmpBuffer[0] & 0xFF); // NOTE: To get unsigned int !!!

                // Remove non-header bytes from start of queue
                if(!readBuffer.isEmpty() && !sensor.telegramHandler.isHeader(readBuffer.peek())) {
                    readBuffer.remove();
                    continue;
                }

                //log.info("Post: " + readBuffer.peek());
                while(readBuffer.size() > 3) {

                    int measurement = sensor.telegramHandler.process(readBuffer);
                    sensor.onMeasurement(measurement);
                    //log.info("Read buffer size: " + readBuffer.size());
                }

            }

        } catch (Exception e) { System.err.println(e.getMessage()); }
    }


    public void start() {
        running.set(true);
    }

    public void stop() {
        running.set(false);
    }

}

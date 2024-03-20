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
package dse.demo;

import dse.libods.*;

public class TestSensorDemo implements TelegramListener {

    final TestSensor testSensor;

    public static void main(final String[] args) {
        new TestSensorDemo();
    }


    public TestSensorDemo() {

        // Setup test sensor and listener
        testSensor = new TestSensor();
        //testSensor.setTelegramHandler(new TelegramHandler16Bit());
        testSensor.setTelegramHandler(new TelegramHandler18Bit());
        testSensor.addEventListener(this);
        testSensor.start();

        // Keep running for some seconds
        try {
            Thread.sleep(2 * 60_000L);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

        // Stop listener and test sensor
        testSensor.removeEventListener(this);
        testSensor.stop();

    }


    @Override
    public void onTelegramResultEvent(TelegramResultEvent event) {
        System.out.println("Result: " + event.getMeasurement());
    }

    @Override
    public void onTelegramErrorEvent(TelegramErrorEvent event) {
        System.err.println("Error: " + event.toString());
    }

}

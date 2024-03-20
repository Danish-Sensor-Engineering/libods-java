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

import java.util.EventObject;

public class TelegramResultEvent extends EventObject {

    private final int measurement;
    private final int movingAvg;
    private final int movingMin;
    private final int movingMax;


    public TelegramResultEvent(Object source, int measurement ) {
        super( source );
        this.measurement = measurement;
        this.movingMin = 0;
        this.movingMax = 0;
        this.movingAvg = 0;
    }

    public TelegramResultEvent(Object source, int measurement, int avg, int min, int max ) {
        super( source );
        this.measurement = measurement;
        this.movingAvg = avg;
        this.movingMin = min;
        this.movingMax = max;
    }


    public int getMeasurement() {
        return measurement;
    }

    public int getAverage() {
        return (movingAvg > 0) ? movingAvg : measurement;
    }

    public int getMinimum() {
        return (movingMin > 0) ? movingMin : measurement;
    }

    public int getMaximum() {
        return (movingMax > 0) ? movingMax : measurement;
    }

}

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sensor {


    /**
     * Various Configurable Options
     */

    public int movingPoints = 100; // Size of moving data points to calculate average on
    public int interval = 10;  // Every n interval we send measurement event


    /**
     * Private Fields
     */

    private int intervalCounter = 0;
    private int[] movingPointsArray  = new int[movingPoints];
    private int pointsObserved = 0;  // We want to have data for avg. before observing x data points
    private int movingPointsAvg = 0;
    private int movingPointsMin = 0;
    private int movingPointsMax = 0;



    /**
     *  Telegram Handler Setup
     */
    protected TelegramHandler telegramHandler;

    public void setTelegramHandler(TelegramHandler telegramHandler) {
        this.telegramHandler = telegramHandler;
    }

    public TelegramHandler getTelegramHandler() {
        return telegramHandler;
    }




    /**
     * Event Listener Configuration
     */

    protected final List<TelegramListener> eventListeners = new ArrayList<>();

    public synchronized void addEventListener( TelegramListener l ) {
        eventListeners.add( l );
    }

    public synchronized void removeEventListener( TelegramListener l ) {
        eventListeners.remove( l );
    }




    /**
     * Measurement Handling
     */


    protected void clear() {
        intervalCounter = 0;
        pointsObserved = 0;
        movingPointsArray = new int[this.movingPoints];
    }


    protected void onMeasurement(int measurement) {

        if(measurement < 99) {
            sendError(measurement);
            return;
        }

        movingPointsArray[pointsObserved++] = measurement;
        if(pointsObserved >= movingPoints) {
            movingPointsAvg = (int) Arrays.stream(movingPointsArray).average().orElse(measurement);
            movingPointsMin = Arrays.stream(movingPointsArray).min().orElse(measurement);
            movingPointsMax = Arrays.stream(movingPointsArray).max().orElse(measurement);
            pointsObserved = 0;
        }

        if(++intervalCounter > interval) {
            intervalCounter = 0;
            TelegramResultEvent event = new TelegramResultEvent( this, measurement, movingPointsAvg, movingPointsMin, movingPointsMax);
            sendEvent(event);
        }
    }


    private synchronized void sendEvent(TelegramResultEvent event) {
        for (TelegramListener eventListener : eventListeners) {
            eventListener.onTelegramResultEvent(event);
        }
    }


    private synchronized void sendError(int errorCode) {

        TelegramError error;
        switch (errorCode) {
            case 0:
                error = TelegramError.TARGET_0;
                break;
            case 1:
                error = TelegramError.TARGET_1;
                break;
            case 2:
                error = TelegramError.TARGET_2;
                break;
            case 4:
                error = TelegramError.LIGHT_4;
                break;
            case 5:
                error = TelegramError.LIGHT_5;
                break;
            case 6:
                error = TelegramError.LIGHT_6;
                break;
            default:
                error = TelegramError.UNKNOWN;
                break;
        }

        TelegramErrorEvent event = new TelegramErrorEvent( this, error );
        for (TelegramListener eventListener : eventListeners) {
            eventListener.onTelegramErrorEvent(event);
        }
    }

}

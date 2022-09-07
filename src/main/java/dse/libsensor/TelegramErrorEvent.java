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

import java.util.EventObject;

public class TelegramErrorEvent extends EventObject {

    private final TelegramError error;

    public TelegramErrorEvent(Object source, TelegramError error ) {
        super( source );
        this.error = error;
    }

    public int getId() {
        return error.getId();
    }
    public String getMessage() {
        return error.getMessage();
    }

    public String toString() {
        return(error.getId() + " - " + error.getMessage());
    }
}

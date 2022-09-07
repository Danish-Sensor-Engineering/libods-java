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

public enum TelegramError {

    UNKNOWN(99, "An unknown error occurred."),
    LIGHT_6(6, "Too little light returned or there is no target at all."),
    LIGHT_5(5, "Too much light returned/blinding or false light."),
    LIGHT_4(4, "False light or an undefined spot recorded."),
    TARGET_2(2, "A target is observed but outside the measuring range."),
    TARGET_1(1, "A target is observed but outside the measuring range."),
    TARGET_0(0, "A target is observed but outside the measuring range.");

    private final int id;
    private final String message;

    TelegramError(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() { return id; }
    public String getMessage() { return message; }

}

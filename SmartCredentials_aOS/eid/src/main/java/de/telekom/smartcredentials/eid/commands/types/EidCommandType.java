/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.eid.commands.types;

/**
 * Created by Alex.Graur@endava.com at 2/18/2020
 */
public enum EidCommandType {

    GET_INFO("GET_INFO"),
    GET_API_LEVEL("GET_API_LEVEL"),
    SET_API_LEVEL("SET_API_LEVEL"),
    GET_READER("GET_READER"),
    GET_READER_LIST("GET_READER_LIST"),
    RUN_AUTH("RUN_AUTH"),
    GET_ACCESS_RIGHTS("GET_ACCESS_RIGHTS"),
    SET_ACCESS_RIGHTS("SET_ACCESS_RIGHTS"),
    GET_CERTIFICATE("GET_CERTIFICATE"),
    CANCEL("CANCEL"),
    ACCEPT("ACCEPT"),
    SET_PIN("SET_PIN"),
    SET_CAN("SET_CAN"),
    SET_PUK("SET_PUK"),
    RUN_CHANGE_PIN("RUN_CHANGE_PIN"),
    SET_NEW_PIN("SET_NEW_PIN");

    private final String mCommandType;

    EidCommandType(String type) {
        mCommandType = type;
    }

    public String getCommandType() {
        return mCommandType;
    }
}

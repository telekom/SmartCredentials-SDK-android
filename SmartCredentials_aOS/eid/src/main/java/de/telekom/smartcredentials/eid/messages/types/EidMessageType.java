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
package de.telekom.smartcredentials.eid.messages.types;

/**
 * Created by Alex.Graur@endava.com at 2/18/2020
 */
public enum EidMessageType {

    ACCESS_RIGHTS("ACCESS_RIGHTS"),
    API_LEVEL("API_LEVEL"),
    AUTH("AUTH"),
    BAD_STATE("BAD_STATE"),
    CERTIFICATE("CERTIFICATE"),
    ENTER_CAN("ENTER_CAN"),
    ENTER_PIN("ENTER_PIN"),
    ENTER_PUK("ENTER_PUK"),
    INFO("INFO"),
    INSERT_CARD("INSERT_CARD"),
    INTERNAL_ERROR("INTERNAL_ERROR"),
    INVALID("INVALID"),
    READER("READER"),
    READER_LIST("READER_LIST"),
    SDK_CONNECTED("SDK_CONNECTED"),
    SDK_DISCONNECTED("SDK_DISCONNECTED"),
    SDK_NOT_CONNECTED("SDK_NOT_CONNECTED"),
    SDK_NOT_INITIALIZED("SDK_NOT_INITIALIZED"),
    SESSION_DISCONNECTED("SESSION_DISCONNECTED"),
    SESSION_GENERATED("SESSION_GENERATED"),
    CHANGE_PIN("CHANGE_PIN"),
    ENTER_NEW_PIN("ENTER_NEW_PIN"),
    UNKNOWN_COMMAND("UNKNOWN_COMMAND");

    private final String mMessageType;

    EidMessageType(String messageType) {
        mMessageType = messageType;
    }

    public String getMessageType() {
        return mMessageType;
    }
}

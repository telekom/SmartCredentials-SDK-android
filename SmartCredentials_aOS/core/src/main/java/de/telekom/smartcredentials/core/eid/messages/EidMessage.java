/*
 * Copyright (c) 2019 Telekom Deutschland AG
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

package de.telekom.smartcredentials.core.eid.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
public class EidMessage {

    public static final String ACCESS_RIGHTS = "ACCESS_RIGHTS";
    public static final String API_LEVEL = "API_LEVEL";
    public static final String AUTH = "AUTH";
    public static final String BAD_STATE = "BAD_STATE";
    public static final String BINDED = "BINDED";
    public static final String CERTIFICATE = "CERTIFICATE";
    public static final String ENTER_CAN = "ENTER_CAN";
    public static final String ENTER_PIN = "ENTER_PIN";
    public static final String ENTER_PUK = "ENTER_PUK";
    public static final String INFO = "INFO";
    public static final String INSERT_CARD = "INSERT_CARD";
    public static final String INTERNAL_ERROR = "INTERNAL_ERROR";
    public static final String INVALID = "INVALID";
    public static final String NOT_BINDED = "NOT_BINDED";
    public static final String READER = "READER";
    public static final String READER_LIST = "READER_LIST";
    public static final String SDK_NOT_INITIALIZED = "SDK_NOT_INITIALIZED";
    public static final String UNKNOWN_COMMAND = "UNKNOWN_COMMAND";

    @SerializedName("msg")
    @Expose
    private String mName;

    public EidMessage() {

    }

    public EidMessage(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public String toString() {
        return "EidMessage{" +
                "name='" + mName + '\'' +
                '}';
    }
}

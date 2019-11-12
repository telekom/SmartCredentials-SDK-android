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

package de.telekom.smartcredentials.core.eid.commands;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
public abstract class EidCommand {

    public static final String GET_INFO = "GET_INFO";
    public static final String GET_API_LEVEL = "GET_API_LEVEL";
    public static final String SET_API_LEVEL = "SET_API_LEVEL";
    public static final String GET_READER = "GET_READER";
    public static final String GET_READER_LIST = "GET_READER_LIST";
    public static final String RUN_AUTH = "RUN_AUTH";
    public static final String GET_ACCESS_RIGHTS = "GET_ACCESS_RIGHTS";
    public static final String SET_ACCESS_RIGHTS = "SET_ACCESS_RIGHTS";
    public static final String GET_CERTIFICATE = "GET_CERTIFICATE";
    public static final String CANCEL = "CANCEL";
    public static final String ACCEPT = "ACCEPT";
    public static final String SET_PIN = "SET_PIN";
    public static final String SET_CAN = "SET_CAN";
    public static final String SET_PUK = "SET_PUK";

    @SerializedName("cmd")
    @Expose
    private String mName;

    public EidCommand(String name) {
        mName = name;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Override
    public String toString() {
        return "EidCommand{" +
                "mName='" + mName + '\'' +
                '}';
    }
}

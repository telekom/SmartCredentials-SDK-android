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

package de.telekom.smartcredentials.eid.commands;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
public class SetCanCommand extends SmartEidCommand {

    @SerializedName("value")
    @Expose
    private String mValue;

    public SetCanCommand(String can) {
        super(EidCommandType.SET_CAN.getCommandType());
        mValue = can;
    }

    public String getValue() {
        return mValue;
    }
}
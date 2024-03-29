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

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.eid.commands.builder.CommandBuilder;
import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
@SuppressWarnings("unused")
public class SetCanCommand extends SmartEidCommand {

    @SerializedName("value")
    @Expose
    private String mValue;

    public SetCanCommand(Builder builder) {
        super(EidCommandType.SET_CAN.getCommandType());
        mValue = builder.mValue;
    }

    public String getValue() {
        return mValue;
    }

    @NonNull
    @Override
    public String toString() {
        return "SetCanCommand{" +
                "mValue='" + mValue + '\'' +
                '}';
    }

    public static class Builder implements CommandBuilder<SetCanCommand> {

        private final String mValue;

        public Builder(String value) {
            mValue = value;
        }

        @Override
        public SetCanCommand build() {
            return new SetCanCommand(this);
        }
    }
}

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

package de.telekom.smartcredentials.eid.commands;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.eid.commands.builder.CommandBuilder;
import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

/**
 * Created by Alex.Graur@endava.com at 4/8/2020
 */
@SuppressWarnings("unused")
public class GetReaderCommand extends SmartEidCommand {

    @SerializedName("name")
    @Expose
    private String mName;

    private GetReaderCommand(Builder builder) {
        super(EidCommandType.GET_READER.getCommandType());
        this.mName = builder.mName;
    }

    public String getName() {
        return mName;
    }

    @NonNull
    @Override
    public String toString() {
        return "GetReaderCommand{" +
                "mName='" + mName + '\'' +
                '}';
    }

    public static class Builder implements CommandBuilder<GetReaderCommand> {

        private String mName;

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        @Override
        public GetReaderCommand build() {
            return new GetReaderCommand(this);
        }
    }
}

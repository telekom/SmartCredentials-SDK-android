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

import java.util.Arrays;

import de.telekom.smartcredentials.eid.commands.builder.CommandBuilder;
import de.telekom.smartcredentials.eid.commands.types.EidCommandType;

/**
 * Created by Alex.Graur@endava.com at 4/8/2020
 */
@SuppressWarnings("unused")
public class SetAccessRightsCommand extends SmartEidCommand {

    @SerializedName("chat")
    @Expose
    private String[] mChat;

    private SetAccessRightsCommand(Builder builder) {
        super(EidCommandType.SET_ACCESS_RIGHTS.getCommandType());
        this.mChat = builder.mChat;
    }

    public String[] getChat() {
        return mChat;
    }

    @NonNull
    @Override
    public String toString() {
        return "SetAccessRightsCommand{" +
                "mChat=" + Arrays.toString(mChat) +
                '}';
    }

    public static class Builder implements CommandBuilder<SetAccessRightsCommand> {

        private String[] mChat;

        public Builder chat(String[] chat) {
            this.mChat = chat;
            return this;
        }

        @Override
        public SetAccessRightsCommand build() {
            return new SetAccessRightsCommand(this);
        }
    }
}

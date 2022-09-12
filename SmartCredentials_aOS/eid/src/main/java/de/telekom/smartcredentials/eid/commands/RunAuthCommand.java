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
public class RunAuthCommand extends SmartEidCommand {

    @SerializedName("tcTokenURL")
    @Expose
    private String mTokenUrl;
    @SerializedName("developerMode")
    @Expose
    private boolean mDeveloperMode;
    @SerializedName("handleInterrupt")
    @Expose
    private boolean mHandleInterrupt;
    @SerializedName("status")
    @Expose
    private boolean mStatus;
    @SerializedName("messages")
    @Expose
    private Messages mMessages;

    private RunAuthCommand(Builder builder) {
        super(EidCommandType.RUN_AUTH.getCommandType());
        this.mTokenUrl = builder.mTokenUrl;
        this.mDeveloperMode = builder.mDeveloperMode;
        this.mHandleInterrupt = builder.mHandleInterrupt;
        this.mStatus = builder.mStatus;
        this.mMessages = builder.mMessages;
    }

    public void setTokenUrl(String tokenUrl) {
        this.mTokenUrl = tokenUrl;
    }

    public String getTokenUrl() {
        return mTokenUrl;
    }

    public boolean isDeveloperMode() {
        return mDeveloperMode;
    }

    public void setDeveloperMode(boolean mDeveloperMode) {
        this.mDeveloperMode = mDeveloperMode;
    }

    public boolean isHandleInterrupt() {
        return mHandleInterrupt;
    }

    public void setHandleInterrupt(boolean mHandleInterrupt) {
        this.mHandleInterrupt = mHandleInterrupt;
    }

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean mStatus) {
        this.mStatus = mStatus;
    }

    public Messages getMessages() {
        return mMessages;
    }

    public void setMessages(Messages mMessages) {
        this.mMessages = mMessages;
    }

    @NonNull
    @Override
    public String toString() {
        return "RunAuthCommand{" +
                "mTokenUrl='" + mTokenUrl + '\'' +
                ", mDeveloperMode=" + mDeveloperMode +
                ", mHandleInterrupt=" + mHandleInterrupt +
                ", mStatus=" + mStatus +
                ", mMessages=" + mMessages +
                '}';
    }

    public static class Builder implements CommandBuilder<RunAuthCommand> {

        private final String mTokenUrl;
        private boolean mDeveloperMode;
        private boolean mHandleInterrupt;
        private boolean mStatus;
        private Messages mMessages;

        public Builder(String tokenUrl) {
            this.mTokenUrl = tokenUrl;
        }

        public void setDeveloperMode(boolean developerMode) {
            this.mDeveloperMode = developerMode;
        }

        public void setHandleInterrupt(boolean handleInterrupt) {
            this.mHandleInterrupt = handleInterrupt;
        }

        public void setStatus(boolean status) {
            this.mStatus = status;
        }

        public void setMessages(Messages messages) {
            this.mMessages = messages;
        }

        @Override
        public RunAuthCommand build() {
            return new RunAuthCommand(this);
        }
    }
}

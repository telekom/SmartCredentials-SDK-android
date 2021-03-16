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

package de.telekom.smartcredentials.eid.messages;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
public class AccessRightsMessage extends SmartEidMessage {

    @SerializedName("chat")
    @Expose
    private Chat mChat;

    @SerializedName("canAllowed")
    @Expose
    private boolean mCanAllowed;

    public AccessRightsMessage() {
        super();
    }

    @VisibleForTesting
    protected AccessRightsMessage(Chat chat, boolean canAllowed) {
        super();
        mChat = chat;
        mCanAllowed = canAllowed;
    }

    public Chat getChat() {
        return mChat;
    }

    public void setChat(Chat mChat) {
        this.mChat = mChat;
    }

    public boolean getCanAllowed() {
        return mCanAllowed;
    }

    public void setCanAllowed(boolean canAllowed) {
        this.mCanAllowed = canAllowed;
    }

    @NonNull
    @Override
    public String toString() {
        return "AccessRightsMessage{" +
                "mChat=" + mChat + "," +
                "mCanAllowed=" + mCanAllowed +
                '}';
    }
}

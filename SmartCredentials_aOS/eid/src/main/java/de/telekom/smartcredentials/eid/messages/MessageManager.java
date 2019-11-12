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

import com.google.gson.Gson;

import de.telekom.smartcredentials.core.eid.EidMessageReceivedCallback;
import de.telekom.smartcredentials.core.eid.messages.EidMessage;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
public class MessageManager {

    private final Gson mGson;
    private final EidMessageReceivedCallback mCallback;

    public MessageManager(EidMessageReceivedCallback callback) {
        mGson = new Gson();
        mCallback = callback;
    }

    public void parseMessage(String rawMessage) {
        EidMessage message = mGson.fromJson(rawMessage, EidMessage.class);
        mCallback.onDebugged("received message from SDK: " + message.getName());
        switch (message.getName()) {
            case EidMessage.ACCESS_RIGHTS:
                AccessRightsMessage accessRightsMessage = mGson.fromJson(rawMessage, AccessRightsMessage.class);
                mCallback.onMessageReceived(accessRightsMessage);
                break;
            case EidMessage.ENTER_CAN:
                EnterCanMessage enterCanMessage = mGson.fromJson(rawMessage, EnterCanMessage.class);
                mCallback.onMessageReceived(enterCanMessage);
                break;
            case EidMessage.AUTH:
                AuthMessage authMessage = mGson.fromJson(rawMessage, AuthMessage.class);
                mCallback.onMessageReceived(authMessage);
                break;
            case EidMessage.INSERT_CARD:
            case EidMessage.UNKNOWN_COMMAND:
            default:
                mCallback.onMessageReceived(message);
                break;
        }
    }
}

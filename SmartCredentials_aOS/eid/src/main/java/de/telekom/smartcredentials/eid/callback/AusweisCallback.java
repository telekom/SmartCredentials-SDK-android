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

package de.telekom.smartcredentials.eid.callback;

import com.governikus.ausweisapp2.IAusweisApp2SdkCallback;

import de.telekom.smartcredentials.core.eid.callbacks.EidMessageReceivedCallback;
import de.telekom.smartcredentials.eid.messages.SessionDisconnectedMessage;
import de.telekom.smartcredentials.eid.messages.SessionGeneratedMessage;
import de.telekom.smartcredentials.eid.messages.parser.MessageParser;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
public class AusweisCallback extends IAusweisApp2SdkCallback.Stub {

    private final MessageParser mMessageParser;
    private final EidMessageReceivedCallback mCallback;

    public String mSessionId;

    public AusweisCallback(MessageParser messageParser, EidMessageReceivedCallback callback) {
        mMessageParser = messageParser;
        mCallback = callback;
    }

    @Override
    public void sessionIdGenerated(String s, boolean b) {
        mSessionId = s;

        if (mCallback != null) {
            mCallback.onMessageReceived(new SessionGeneratedMessage(s));
        }
    }

    @Override
    public void receive(String s) {
        mMessageParser.parseMessage(s);
    }

    @Override
    public void sdkDisconnected() {
        if (mCallback != null) {
            mCallback.onMessageReceived(new SessionDisconnectedMessage());
        }
    }
}

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

import de.telekom.smartcredentials.core.eid.EidMessageReceivedCallback;
import de.telekom.smartcredentials.eid.handlers.AusweisHandler;
import de.telekom.smartcredentials.eid.messages.MessageManager;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
public class AusweisCallback extends IAusweisApp2SdkCallback.Stub {

    private final MessageManager mMessageManager;
    private final AusweisHandler mHandler;
    private final EidMessageReceivedCallback mCallback;

    public String mSessionId;

    public AusweisCallback(MessageManager messageManager, AusweisHandler handler, EidMessageReceivedCallback callback) {
        mMessageManager = messageManager;
        mHandler = handler;
        mCallback = callback;
    }

    @Override
    public void sessionIdGenerated(String s, boolean b) {
        mCallback.onDebugged("Session generated: " + s + " / " + b);
        mSessionId = s;
        mHandler.obtainMessage();
    }

    @Override
    public void receive(String s) {
        mCallback.onDebugged("Message received: " + s);
        mMessageManager.parseMessage(s);
        mHandler.obtainMessage();
    }

    @Override
    public void sdkDisconnected() {
        mHandler.obtainMessage();
    }
}

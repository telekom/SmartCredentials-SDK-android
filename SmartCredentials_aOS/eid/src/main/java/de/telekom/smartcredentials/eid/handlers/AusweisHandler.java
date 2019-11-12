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

package de.telekom.smartcredentials.eid.handlers;

import android.os.Handler;
import android.os.Message;

import de.telekom.smartcredentials.core.eid.EidMessageReceivedCallback;
import de.telekom.smartcredentials.eid.messages.MessageManager;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
public class AusweisHandler extends Handler {

    private MessageManager mMessageManager;
    private final EidMessageReceivedCallback mCallback;

    public AusweisHandler(EidMessageReceivedCallback callback) {
        mCallback = callback;
        mMessageManager = new MessageManager(callback);
    }

    @Override
    public void handleMessage(Message message) {
        mCallback.onDebugged("handle message in Handler: " + message.what);
        switch (message.what) {
            case 1:
                mMessageManager.parseMessage((String) message.obj);
                break;
            case 2:
                break;
            default:
                super.handleMessage(message);
                break;
        }
    }
}

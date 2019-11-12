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

package de.telekom.smartcredentials.eid.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.RemoteException;

import com.google.gson.Gson;

import de.telekom.smartcredentials.core.api.EidApi;
import de.telekom.smartcredentials.core.eid.EidBindCallback;
import de.telekom.smartcredentials.core.eid.EidMessageReceivedCallback;
import de.telekom.smartcredentials.core.eid.EidSendCommandCallback;
import de.telekom.smartcredentials.core.eid.EidUpdateTagCallback;
import de.telekom.smartcredentials.core.eid.commands.EidCommand;
import de.telekom.smartcredentials.eid.AusweisServiceConnection;
import de.telekom.smartcredentials.eid.callback.AusweisCallback;
import de.telekom.smartcredentials.eid.dispatcher.ForegroundDispatcher;
import de.telekom.smartcredentials.eid.handlers.AusweisHandler;
import de.telekom.smartcredentials.eid.messages.MessageManager;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
public class EidController implements EidApi {

    private static final String AUSWEIS_APP_ACTION = "com.governikus.ausweisapp2.START_SERVICE";

    private AusweisServiceConnection mServiceConnection;
    private AusweisCallback mAusweisCallback;
    private final Gson mGson;
    private final ForegroundDispatcher mForegroundDispatcher;

    public EidController() {
        mGson = new Gson();
        mForegroundDispatcher = new ForegroundDispatcher();
    }

    @Override
    public void enableDispatcher(Activity activity) {
        mForegroundDispatcher.enable(activity);
    }

    @Override
    public void disableDispatcher(Activity activity) {
        mForegroundDispatcher.disable(activity);
    }

    @Override
    public void bind(Context context, String appPackage, EidBindCallback bindCallback,
                     EidMessageReceivedCallback receiveMessageCallback) {
        Intent intent = new Intent(AUSWEIS_APP_ACTION);
        intent.setPackage(appPackage);
        AusweisHandler handler = new AusweisHandler(receiveMessageCallback);
        mAusweisCallback = new AusweisCallback(new MessageManager(receiveMessageCallback), handler, receiveMessageCallback);
        mServiceConnection = new AusweisServiceConnection(mAusweisCallback, bindCallback);
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void unbind(Context context) {
        context.unbindService(mServiceConnection);
        mServiceConnection = null;
    }

    @Override
    public void sendCommand(EidCommand command, EidSendCommandCallback callback) {
        try {
            String jsonString = mGson.toJson(command);
            callback.onDebugged("SDK command is: " + jsonString);
            mServiceConnection.getAusweisSdk().send(mAusweisCallback.mSessionId, jsonString);
        } catch (RemoteException e) {
            callback.onFailed(e);
        }
    }

    @Override
    public void updateNfcTag(Tag tag, EidUpdateTagCallback callback) {
        try {
            mServiceConnection.getAusweisSdk().updateNfcTag(mAusweisCallback.mSessionId, tag);
        } catch (RemoteException e) {
            callback.onFailed(e);
        }
    }
}

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

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.RemoteException;

import com.google.gson.Gson;

import de.telekom.smartcredentials.core.api.EidApi;
import de.telekom.smartcredentials.core.eid.callbacks.EidMessageReceivedCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidSendCommandCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidUpdateTagCallback;
import de.telekom.smartcredentials.core.eid.commands.EidCommand;
import de.telekom.smartcredentials.eid.callback.AusweisCallback;
import de.telekom.smartcredentials.eid.serviceconnection.AusweisServiceConnection;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
public class EidController implements EidApi {

    private static final String AUSWEIS_APP_ACTION = "com.governikus.ausweisapp2.START_SERVICE";

    private final Gson mGson;
    private AusweisServiceConnection mServiceConnection;
    private AusweisCallback mAusweisCallback;
    private EidMessageReceivedCallback mMessageReceivedCallback;

    public EidController() {
        mGson = new Gson();
    }

    @Override
    public void bind(Context context, String appPackage) {
        Intent intent = new Intent(AUSWEIS_APP_ACTION);
        intent.setPackage(appPackage);
        mAusweisCallback = new AusweisCallback(mMessageReceivedCallback);
        mServiceConnection = new AusweisServiceConnection(mAusweisCallback);
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void unbind(Context context) {
        context.unbindService(mServiceConnection);
        mServiceConnection = null;
    }

    @Override
    public void setMessageReceiverCallback(EidMessageReceivedCallback callback) {
        mMessageReceivedCallback = callback;
    }

    @Override
    public <T extends EidCommand> void sendCommand(T command, EidSendCommandCallback callback) {
        try {
            if (mServiceConnection != null) {
                mServiceConnection.getAusweisSdk().send(mAusweisCallback.mSessionId, mGson.toJson(command));
            } else {
                callback.onFailed(new Exception("Service connection was lost."));
            }
        } catch (RemoteException e) {
            callback.onFailed(e);
        }
    }

    @Override
    public void updateNfcTag(Tag tag, EidUpdateTagCallback callback) {
        try {
            if (mServiceConnection != null) {
                mServiceConnection.getAusweisSdk().updateNfcTag(mAusweisCallback.mSessionId, tag);
                callback.onSuccess();
            } else {
                callback.onFailed(new Exception("Service connection was lost."));
            }
        } catch (RemoteException e) {
            callback.onFailed(e);
        }
    }
}

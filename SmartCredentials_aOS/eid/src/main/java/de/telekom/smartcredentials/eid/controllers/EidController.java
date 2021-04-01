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

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.api.EidApi;
import de.telekom.smartcredentials.core.eid.EidConfiguration;
import de.telekom.smartcredentials.core.eid.callbacks.EidErrorReceivedCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidMessageReceivedCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidSendCommandCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidUpdateTagCallback;
import de.telekom.smartcredentials.core.eid.commands.EidCommand;
import de.telekom.smartcredentials.eid.callback.AusweisCallback;
import de.telekom.smartcredentials.eid.callback.EidCallbackObserver;
import de.telekom.smartcredentials.eid.callback.EidCallbackSubject;
import de.telekom.smartcredentials.eid.messages.parser.MessageParser;
import de.telekom.smartcredentials.eid.serviceconnection.AusweisServiceConnection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
public class EidController implements EidApi, EidCallbackSubject {

    private static final String AUSWEIS_APP_ACTION = "com.governikus.ausweisapp2.START_SERVICE";

    private final Gson mGson;
    private AusweisServiceConnection mServiceConnection;
    private AusweisCallback mAusweisCallback;
    private MessageParser mMessageParser;
    private EidMessageReceivedCallback mMessageReceivedCallback;
    private List<EidCallbackObserver> mObservers;
    private EidConfiguration mEidConfiguration;

    public EidController() {
        mGson = new Gson();
    }

    public void setConfiguration(EidConfiguration configuration) {
        mEidConfiguration = configuration;
    }

    public EidConfiguration getEidConfiguration() {
        return mEidConfiguration;
    }

    @Override
    public void bind(Context context, String appPackage) {
        mObservers = new ArrayList<>();
        Intent intent = new Intent(AUSWEIS_APP_ACTION);
        intent.setPackage(appPackage);
        mMessageParser = new MessageParser(mMessageReceivedCallback);
        attach(mMessageParser);
        mAusweisCallback = new AusweisCallback(mMessageParser, mMessageReceivedCallback);
        attach(mAusweisCallback);
        mServiceConnection = new AusweisServiceConnection(mAusweisCallback);
        attach(mServiceConnection);
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
        if (mAusweisCallback != null) {
            notify(callback);
        }
    }

    @Override
    public <C extends EidCommand> void sendCommand(C command, EidSendCommandCallback callback) {
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

    @Override
    public void attach(EidCallbackObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void notify(EidMessageReceivedCallback callback) {
        for (EidCallbackObserver observer : mObservers) {
            observer.update(callback);
        }
    }
}

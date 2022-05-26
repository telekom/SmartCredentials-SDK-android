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
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.eid.EidConfiguration;
import de.telekom.smartcredentials.core.eid.callbacks.EidErrorReceivedCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidMessageReceivedCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidPatchLevelCheckCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidSendCommandCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidUpdateTagCallback;
import de.telekom.smartcredentials.core.eid.commands.EidCommand;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.eid.callback.AusweisCallback;
import de.telekom.smartcredentials.eid.callback.EidCallbackObserver;
import de.telekom.smartcredentials.eid.callback.EidCallbackSubject;
import de.telekom.smartcredentials.eid.messages.parser.MessageParser;
import de.telekom.smartcredentials.eid.rest.RetrofitClient;
import de.telekom.smartcredentials.eid.serviceconnection.AusweisServiceConnection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
public class EidController implements EidApi, EidCallbackSubject {

    private static final String AUSWEIS_APP_ACTION = "com.governikus.ausweisapp2.START_SERVICE";

    private final CoreController mCoreController;
    private final Gson mGson;
    private final CompositeDisposable mCompositeDisposable;
    private AusweisServiceConnection mServiceConnection;
    private AusweisCallback mAusweisCallback;
    private EidMessageReceivedCallback mMessageReceivedCallback;
    private List<EidCallbackObserver> mObservers;
    private EidConfiguration mEidConfiguration;

    public EidController(CoreController coreController) {
        mCoreController = coreController;
        mGson = new Gson();
        mCompositeDisposable = new CompositeDisposable();
    }

    public void setConfiguration(EidConfiguration configuration) {
        mEidConfiguration = configuration;
    }

    public EidConfiguration getEidConfiguration() {
        return mEidConfiguration;
    }

    @Override
    public SmartCredentialsApiResponse<Void> bind(Context context, String appPackage) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "bind");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.BIND)) {
            String errorMessage = SmartCredentialsFeatureSet.BIND.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        mObservers = new ArrayList<>();
        Intent intent = new Intent(AUSWEIS_APP_ACTION);
        intent.setPackage(appPackage);
        MessageParser mMessageParser = new MessageParser(mMessageReceivedCallback);
        attach(mMessageParser);
        mAusweisCallback = new AusweisCallback(mMessageParser, mMessageReceivedCallback);
        attach(mAusweisCallback);
        mServiceConnection = new AusweisServiceConnection(mAusweisCallback);
        attach(mServiceConnection);
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> unbind(Context context) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "unbind");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.UNBIND)) {
            String errorMessage = SmartCredentialsFeatureSet.UNBIND.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (mServiceConnection != null) {
            context.unbindService(mServiceConnection);
            mServiceConnection = null;
        }
        return new SmartCredentialsResponse<>();
    }

    @Override
    public void setMessageReceiverCallback(EidMessageReceivedCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "setMessageReceiverCallback");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            callback.onFailed(new RootedThrowable());
            return;
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SET_MESSAGE_RECEIVER_CALLBACK)) {
            String errorMessage = SmartCredentialsFeatureSet.SET_MESSAGE_RECEIVER_CALLBACK.getNotSupportedDesc();
            callback.onFailed(new FeatureNotSupportedThrowable(errorMessage));
            return;
        }

        mMessageReceivedCallback = callback;
        notify(callback);
    }

    @Override
    public <C extends EidCommand> void sendCommand(C command, EidSendCommandCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "sendCommand");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            callback.onFailed(new RootedThrowable());
            return;
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SEND_COMMAND)) {
            String errorMessage = SmartCredentialsFeatureSet.SEND_COMMAND.getNotSupportedDesc();
            callback.onFailed(new FeatureNotSupportedThrowable(errorMessage));
            return;
        }

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
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "updateNfcTag");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            callback.onFailed(new RootedThrowable());
            return;
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.UPDATE_NFC_TAG)) {
            String errorMessage = SmartCredentialsFeatureSet.UPDATE_NFC_TAG.getNotSupportedDesc();
            callback.onFailed(new FeatureNotSupportedThrowable(errorMessage));
            return;
        }

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
    public void retrieveLoadingErrorCode(String jwt, boolean isProduction,
                                         EidErrorReceivedCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "retrieveLoadingErrorCode");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            callback.onFailed(new RootedThrowable());
            return;
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.RETRIEVE_LOADING_ERROR_CODE)) {
            String errorMessage = SmartCredentialsFeatureSet.RETRIEVE_LOADING_ERROR_CODE.getNotSupportedDesc();
            callback.onFailed(new FeatureNotSupportedThrowable(errorMessage));
            return;
        }

        RetrofitClient retrofitClient = new RetrofitClient(mEidConfiguration);
        mCompositeDisposable.add(retrofitClient.getRx2EidService(isProduction).getError(jwt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback::onSuccess, callback::onFailed));
    }

    @Override
    public void checkPatchLevel(String version, boolean isProduction, EidPatchLevelCheckCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "checkPatchLevel");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            callback.onFailed(new RootedThrowable());
            return;
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.CHECK_PATCH_LEVEL)) {
            String errorMessage = SmartCredentialsFeatureSet.CHECK_PATCH_LEVEL.getNotSupportedDesc();
            callback.onFailed(new FeatureNotSupportedThrowable(errorMessage));
            return;
        }
        RetrofitClient retrofitClient = new RetrofitClient(mEidConfiguration);
        mCompositeDisposable.add(retrofitClient.getRx2EidService(isProduction).checkPatchLevel(version)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSupported -> {
                    if (isSupported) {
                        callback.onSupportedPatchLevel();
                    } else {
                        callback.onUnsupportedPatchLevel();
                    }
                }, callback::onFailed));
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

    public void destroy() {
        mCompositeDisposable.clear();
    }
}

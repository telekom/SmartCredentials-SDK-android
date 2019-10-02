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

package de.telekom.smartcredentials.wispr.controllers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.net.SocketFactory;

import de.telekom.smartcredentials.core.api.WisprApi;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.responses.EnvelopeException;
import de.telekom.smartcredentials.core.responses.EnvelopeExceptionReason;
import de.telekom.smartcredentials.core.wispr.WisprKey;
import de.telekom.smartcredentials.core.wispr.callbacks.WisprAuthenticationCallback;
import de.telekom.smartcredentials.core.wispr.callbacks.WisprLogoffCallback;
import de.telekom.smartcredentials.core.wispr.callbacks.WisprStatusCallback;
import de.telekom.smartcredentials.wispr.callbacks.WisprRedirectCallback;
import de.telekom.smartcredentials.wispr.exceptions.NotOnRequiredNetworkException;
import de.telekom.smartcredentials.wispr.exceptions.WisprException;
import de.telekom.smartcredentials.wispr.exceptions.WisprExceptionReason;
import de.telekom.smartcredentials.wispr.loggers.WisprLogger;
import de.telekom.smartcredentials.wispr.sockets.SocketCallback;
import de.telekom.smartcredentials.wispr.sockets.SocketManager;
import okhttp3.CertificatePinner;

public final class WisprController implements WisprApi {

    private WisprLogger mWisprLogger;

    public WisprController(CertificatePinner certificatePinner) {
        mWisprLogger = new WisprLogger(certificatePinner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(Context context, ItemEnvelope itemEnvelope, WisprAuthenticationCallback authenticationCallback) {
        if (itemEnvelope == null || itemEnvelope.getDetails() == null) {
            authenticationCallback.onFailed(new EnvelopeException(EnvelopeExceptionReason.NO_DETAILS_EXCEPTION_MSG));
            return;
        }
        JSONObject jsonDetails = itemEnvelope.getDetails();

        if (!jsonDetails.has(WisprKey.WISPR_VERSION.name())) {
            authenticationCallback.onFailed(new WisprException(WisprExceptionReason.NO_WISPR_VERSION));
            return;
        }

        if (!jsonDetails.has(WisprKey.ONLINE_URL.name())) {
            authenticationCallback.onFailed(new WisprException(WisprExceptionReason.NO_ONLINE_URL));
            return;
        }

        if (!jsonDetails.has(WisprKey.FOLLOW_REDIRECTS.name())) {
            authenticationCallback.onFailed(new WisprException(WisprExceptionReason.NO_FOLLOW_REDIRECT));
            return;
        }

        if (!jsonDetails.has(WisprKey.TIMEOUT.name())) {
            authenticationCallback.onFailed(new WisprException(WisprExceptionReason.NO_TIMEOUT));
            return;
        }

        try {
            checkOnlineStatus(context,
                    jsonDetails.getString(WisprKey.WISPR_VERSION.name()),
                    jsonDetails.getString(WisprKey.ONLINE_URL.name()),
                    jsonDetails.getBoolean(WisprKey.FOLLOW_REDIRECTS.name()),
                    jsonDetails.getInt(WisprKey.TIMEOUT.name()),
                    new WisprRedirectCallback() {

                        @Override
                        public void onSuccess(String loginUrl, String eapMessage) {

                            if (!jsonDetails.has(WisprKey.USERNAME.name())) {
                                authenticationCallback.onFailed(new WisprException(WisprExceptionReason.NO_USERNAME));
                                return;
                            }

                            if (!jsonDetails.has(WisprKey.PASSWORD.name())) {
                                authenticationCallback.onFailed(new WisprException(WisprExceptionReason.NO_PASSWORD));
                                return;
                            }

                            try {
                                login(context, loginUrl,
                                        jsonDetails.getString(WisprKey.WISPR_VERSION.name()),
                                        jsonDetails.getString(WisprKey.USERNAME.name()),
                                        jsonDetails.getString(WisprKey.PASSWORD.name()),
                                        jsonDetails.getString(WisprKey.ONLINE_URL.name()),
                                        eapMessage,
                                        getLoginParametersFromItemEnvelope(itemEnvelope, authenticationCallback),
                                        jsonDetails.getInt(WisprKey.TIMEOUT.name()),
                                        authenticationCallback);
                            } catch (JSONException e) {
                                authenticationCallback.onFailed(e);
                            }
                        }

                        @Override
                        public void onFailed(Exception e) {
                            authenticationCallback.onFailed(e);
                        }

                        @Override
                        public void onDebugged(String message) {
                            authenticationCallback.onDebugged(message);
                        }
                    });
        } catch (JSONException e) {
            authenticationCallback.onFailed(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkStatus(Context context, ItemEnvelope itemEnvelope, WisprStatusCallback statusCallback) {
        if (itemEnvelope == null || itemEnvelope.getDetails() == null) {
            statusCallback.onFailed(new EnvelopeException(EnvelopeExceptionReason.NO_DETAILS_EXCEPTION_MSG));
            return;
        }
        JSONObject jsonDetails = itemEnvelope.getDetails();

        if (!jsonDetails.has(WisprKey.STATUS_URL.name())) {
            statusCallback.onFailed(new WisprException(WisprExceptionReason.NO_STATUS_URL));
            return;
        }

        if (!jsonDetails.has(WisprKey.TIMEOUT.name())) {
            statusCallback.onFailed(new WisprException(WisprExceptionReason.NO_TIMEOUT));
            return;
        }

        try {
            new SocketManager().createSocketFactory(context, SocketManager.TYPE_WIFI, new SocketCallback() {

                @Override
                public void onSocketFactoryAvailable(SocketFactory socketFactory) {
                    try {
                        mWisprLogger.checkStatus(socketFactory, jsonDetails.getString(WisprKey.STATUS_URL.name()),
                                jsonDetails.getInt(WisprKey.TIMEOUT.name()), statusCallback);
                    } catch (JSONException e) {
                        statusCallback.onFailed(e);
                    }
                }
            });
        } catch (NotOnRequiredNetworkException e) {
            statusCallback.onFailed(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logoff(Context context, ItemEnvelope itemEnvelope, WisprLogoffCallback logoffCallback) {
        if (itemEnvelope == null || itemEnvelope.getDetails() == null) {
            logoffCallback.onFailed(new EnvelopeException(EnvelopeExceptionReason.NO_DETAILS_EXCEPTION_MSG));
            return;
        }
        JSONObject jsonDetails = itemEnvelope.getDetails();

        if (!jsonDetails.has(WisprKey.LOGOFF_URL.name())) {
            logoffCallback.onFailed(new WisprException(WisprExceptionReason.NO_LOGOFF_URL));
            return;
        }

        if (!jsonDetails.has(WisprKey.FOLLOW_REDIRECTS.name())) {
            logoffCallback.onFailed(new WisprException(WisprExceptionReason.NO_FOLLOW_REDIRECT));
            return;
        }

        if (!jsonDetails.has(WisprKey.TIMEOUT.name())) {
            logoffCallback.onFailed(new WisprException(WisprExceptionReason.NO_TIMEOUT));
            return;
        }

        try {
            new SocketManager().createSocketFactory(context, SocketManager.TYPE_WIFI, new SocketCallback() {

                @Override
                public void onSocketFactoryAvailable(SocketFactory socketFactory) {
                    try {
                        mWisprLogger.logout(socketFactory, jsonDetails.getString(WisprKey.LOGOFF_URL.name()),
                                jsonDetails.getBoolean(WisprKey.FOLLOW_REDIRECTS.name()),
                                jsonDetails.getInt(WisprKey.TIMEOUT.name()), logoffCallback);
                    } catch (JSONException e) {
                        logoffCallback.onFailed(e);
                    }
                }
            });
        } catch (NotOnRequiredNetworkException e) {
            logoffCallback.onFailed(e);
        }
    }

    private void checkOnlineStatus(Context context, String wisprVersion, String url,
                                   boolean followRedirects, int timeout,
                                   WisprRedirectCallback redirectCallback) {
        try {
            new SocketManager().createSocketFactory(context, SocketManager.TYPE_WIFI, new SocketCallback() {

                @Override
                public void onSocketFactoryAvailable(SocketFactory socketFactory) {
                    mWisprLogger.checkOnlineStatus(socketFactory, wisprVersion, url, followRedirects,
                            timeout, redirectCallback);
                }
            });
        } catch (NotOnRequiredNetworkException e) {
            redirectCallback.onFailed(e);
        }
    }

    private void login(Context context, String loginUrl, String wisprVersion, String username,
                       String password, String originatingServer, String eapMessage,
                       Map<String, String> loginParameters, int timeout,
                       WisprAuthenticationCallback authenticationCallback) {
        try {
            new SocketManager().createSocketFactory(context, SocketManager.TYPE_WIFI, new SocketCallback() {

                @Override
                public void onSocketFactoryAvailable(SocketFactory socketFactory) {
                    mWisprLogger.login(socketFactory, loginUrl, wisprVersion, username, password, originatingServer,
                            eapMessage, loginParameters, timeout, authenticationCallback);
                }
            });
        } catch (NotOnRequiredNetworkException e) {
            authenticationCallback.onFailed(e);
        }
    }

    private Map<String, String> getLoginParametersFromItemEnvelope(ItemEnvelope itemEnvelope,
                                                                   WisprAuthenticationCallback authenticationCallback) {
        Map<String, String> headers = new HashMap<>();

        try {
            if (itemEnvelope.getDetails().has(WisprKey.URL_PARAMS.name()) &&
                    itemEnvelope.getDetails().getJSONArray(WisprKey.URL_PARAMS.name()) != null) {
                JSONArray headersArray = itemEnvelope.getDetails()
                        .getJSONArray(WisprKey.URL_PARAMS.name());

                if (headersArray.length() > 0) {
                    for (int i = 0; i < headersArray.length(); i++) {
                        JSONObject jsonHeader = headersArray.getJSONObject(i);
                        String key = jsonHeader.getString(WisprKey.PARAM_KEY.name());
                        String value = jsonHeader.getString(WisprKey.PARAM_VALUE.name());
                        headers.put(key, value);
                    }
                }
            }
        } catch (JSONException e) {
            authenticationCallback.onFailed(e);
        }
        return headers;
    }
}

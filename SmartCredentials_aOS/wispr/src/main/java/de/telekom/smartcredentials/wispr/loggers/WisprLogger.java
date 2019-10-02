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

package de.telekom.smartcredentials.wispr.loggers;

import java.io.IOException;
import java.util.Map;

import javax.net.SocketFactory;

import de.telekom.smartcredentials.core.wispr.callbacks.WisprAuthenticationCallback;
import de.telekom.smartcredentials.core.wispr.callbacks.WisprLogoffCallback;
import de.telekom.smartcredentials.core.wispr.callbacks.WisprStatusCallback;
import de.telekom.smartcredentials.wispr.WisprConstants;
import de.telekom.smartcredentials.wispr.callbacks.WisprRedirectCallback;
import de.telekom.smartcredentials.wispr.exceptions.WisprException;
import de.telekom.smartcredentials.wispr.httpclient.HttpClientFactory;
import de.telekom.smartcredentials.wispr.parsers.WisprLogoffParser;
import de.telekom.smartcredentials.wispr.parsers.WisprStatusParser;
import de.telekom.smartcredentials.wispr.replies.WisprAuthenticationReply;
import de.telekom.smartcredentials.wispr.replies.WisprLogoffReply;
import de.telekom.smartcredentials.wispr.replies.WisprRedirectReply;
import de.telekom.smartcredentials.wispr.replies.WisprStatusReply;
import de.telekom.smartcredentials.wispr.services.WisprService;
import de.telekom.smartcredentials.wispr.strategies.WisprStrategy;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WisprLogger {

    private final static String DUMMY_BASE_URl = "http://localhost";

    private CertificatePinner mCertificatePinner;

    public WisprLogger(CertificatePinner certificatePinner) {
        mCertificatePinner = certificatePinner;
    }

    public void checkOnlineStatus(SocketFactory socketFactory, String wisprVersion, String url,
                                  boolean followRedirects, int timeout,
                                  WisprRedirectCallback redirectCallback) {
        HttpClientFactory httpClientFactory = new HttpClientFactory(timeout, mCertificatePinner);
        Call.Factory callFactory = httpClientFactory.createHttpClient(socketFactory, followRedirects);

        try {
            WisprStrategy wisprStrategy = new WisprStrategy(wisprVersion);
            WisprRedirectReply redirectReply = wisprStrategy.checkOnline(createWisprService(callFactory),
                    url, redirectCallback);
            redirectCallback.onSuccess(redirectReply.getLoginUrl(), redirectReply.getmEapMessage());
        } catch (IOException | WisprException | IllegalArgumentException e) {
            redirectCallback.onFailed(e);
        }
    }

    public void login(SocketFactory socketFactory, String loginUrl, String wisprVersion,
                      String username, String password, String originatingServer, String eapMessage,
                      Map<String, String> loginParameters, int timeout,
                      WisprAuthenticationCallback authenticationCallback) {
        try {
            WisprStrategy wisprStrategy = new WisprStrategy(wisprVersion);
            HttpClientFactory httpClientFactory = new HttpClientFactory(timeout, mCertificatePinner);
            String authenticationResponse = wisprStrategy.login(createWisprService(httpClientFactory.createHttpClient(socketFactory, true)),
                    loginUrl, username, password, originatingServer, eapMessage, loginParameters,
                    authenticationCallback);

            if (authenticationResponse != null && !authenticationResponse.isEmpty()) {
                WisprAuthenticationReply authenticationReply = wisprStrategy.getAuthenticationParser(authenticationCallback)
                        .parse(authenticationResponse);

                authenticationCallback.onDebugged(authenticationReply.toString());
                if (authenticationReply.getResponseCode() == WisprConstants.WISPR_RESPONSE_CODE_LOGIN_SUCCEEDED) {
                    authenticationCallback.onSuccess(authenticationReply.getStatusUrl(),
                            authenticationReply.getLogoffUrl());
                } else {
                    authenticationCallback.onFailed(new WisprException("WISPr failed with " +
                            authenticationReply.getResponseCode() + " response code and " +
                            authenticationReply.getMessageType() + " message type."));
                }
            } else {
                authenticationCallback.onFailed(new WisprException("Login response is null or empty."));
            }
        } catch (IOException | IllegalArgumentException | WisprException | InterruptedException e) {
            authenticationCallback.onFailed(e);
        }
    }

    public void checkStatus(SocketFactory socketFactory, String statusUrl, int timeout,
                            WisprStatusCallback statusCallback) {
        try {
            HttpClientFactory httpClientFactory = new HttpClientFactory(timeout, mCertificatePinner);
            String body = createWisprService(httpClientFactory.createHttpClient(socketFactory, false))
                    .checkStatus(statusUrl).execute().body();
            WisprStatusReply statusReply = new WisprStatusParser(statusCallback).parse(body);
            statusCallback.onSuccess(statusReply.getStatus() == 1);
        } catch (IOException | WisprException e) {
            statusCallback.onFailed(e);
        }
    }

    public void logout(SocketFactory socketFactory, String logoffUrl, boolean followRedirects,
                       int timeout, WisprLogoffCallback logoffCallback) {
        logoffCallback.onDebugged("Logoff URL is: " + logoffUrl);
        try {
            HttpClientFactory httpClientFactory = new HttpClientFactory(timeout, mCertificatePinner);
            Response<String> response = createWisprService(httpClientFactory.createHttpClient(socketFactory, followRedirects))
                    .logout(logoffUrl).execute();
            WisprLogoffReply logoffReply;

            if (response.body() != null) {
                logoffReply = new WisprLogoffParser(logoffCallback).parse(response.body());

                if (WisprConstants.WISPR_MESSAGE_TYPE_LOGOFF_SUCCEEDED == logoffReply.getMessageType() &&
                        WisprConstants.WISPR_RESPONSE_CODE_LOGOFF_SUCCEEDED == logoffReply.getResponseCode()) {
                    logoffCallback.onSuccess();
                } else {
                    logoffCallback.onFailed(new WisprException("WISPr logoff failed with message type " +
                            logoffReply.getMessageType() + " and response code " + logoffReply.getResponseCode()));
                }
            } else {
                if (response.errorBody() != null) {
                    logoffReply = new WisprLogoffParser(logoffCallback).parse(response.errorBody().string());

                    if (WisprConstants.WISPR_MESSAGE_TYPE_LOGOFF_SUCCEEDED == logoffReply.getMessageType() &&
                            WisprConstants.WISPR_RESPONSE_CODE_LOGOFF_SUCCEEDED == logoffReply.getResponseCode()) {
                        logoffCallback.onSuccess();
                    } else {
                        logoffCallback.onFailed(new WisprException("WISPr logoff failed with message type " +
                                logoffReply.getMessageType() + " and response code " + logoffReply.getResponseCode()));
                    }
                } else {
                    logoffCallback.onFailed(new WisprException("Unknown WISPr logoff procedure."));
                }
            }
        } catch (IOException | WisprException e) {
            logoffCallback.onFailed(e);
        }
    }

    private WisprService createWisprService(Call.Factory callFactory) {
        return new Retrofit.Builder()
                .baseUrl(DUMMY_BASE_URl)
                .callFactory(callFactory)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(WisprService.class);
    }
}

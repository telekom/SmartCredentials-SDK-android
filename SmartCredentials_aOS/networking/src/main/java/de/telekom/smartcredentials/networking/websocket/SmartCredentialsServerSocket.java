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

package de.telekom.smartcredentials.networking.websocket;

import static de.telekom.smartcredentials.core.qrlogin.TokenPluginError.INVALID_WEB_SOCKET_URL;
import static de.telekom.smartcredentials.core.qrlogin.TokenPluginError.UNDEFINED_URL;

import com.google.gson.Gson;

import java.util.Map;
import java.util.regex.Pattern;

import de.telekom.smartcredentials.core.model.token.TokenResponse;
import de.telekom.smartcredentials.core.networking.AuthParamKey;
import de.telekom.smartcredentials.core.networking.ServerSocket;
import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;
import de.telekom.smartcredentials.networking.request.utils.HttpClientBuilder;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SmartCredentialsServerSocket implements ServerSocket<TokenResponse, String> {

    private EchoWebSocketListener mWebSocketListener;
    private final Request.Builder mRequestBuilder;
    private final CertificatePinner.Builder mCertificatePinnerBuilder;

    public SmartCredentialsServerSocket(Request.Builder requestBuilder,
                                        CertificatePinner.Builder certificatePinnerBuilder) {
        this.mRequestBuilder = requestBuilder;
        this.mCertificatePinnerBuilder = certificatePinnerBuilder;
    }

    @Override
    public void startServer(Map<String, String> params, TokenPluginCallback<TokenResponse, String> pluginCallback) {
        if (!params.containsKey(AuthParamKey.QR_SERVER_URL.name())) {
            if (pluginCallback != null) {
                pluginCallback.onFailed(UNDEFINED_URL.getDesc());
            }
            return;
        }
        mWebSocketListener = new EchoWebSocketListener(params, pluginCallback, new Gson());
        String url = params.get(AuthParamKey.QR_SERVER_URL.name());
        if (Pattern.compile("^(wss?:\\/\\/(?!\\s*$).+)").matcher(url).matches()) {
            startWebSocket(url, params);
        } else {
            pluginCallback.onFailed(INVALID_WEB_SOCKET_URL.getDesc());
        }
    }

    public void startWebSocket(String requestUrl, Map<String, String> params) {
        Request mRequest = mRequestBuilder.url(requestUrl).build();
        OkHttpClient.Builder okHttpClientBuilder = HttpClientBuilder.getBuilder(requestUrl, params, mCertificatePinnerBuilder);
        OkHttpClient mOkHttpClient = okHttpClientBuilder.build();
        mOkHttpClient.newWebSocket(mRequest, mWebSocketListener);
        mOkHttpClient.dispatcher()
                .executorService()
                .shutdown();
    }
}

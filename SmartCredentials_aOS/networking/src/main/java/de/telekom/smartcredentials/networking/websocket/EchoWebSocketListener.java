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

import com.google.gson.Gson;

import java.util.Map;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.token.TokenResponse;
import de.telekom.smartcredentials.core.networking.AuthParamKey;
import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;
import de.telekom.smartcredentials.core.qrlogin.TokenPluginError;
import de.telekom.smartcredentials.networking.websocket.model.BarcodeWrapper;
import de.telekom.smartcredentials.networking.websocket.model.ErrorAuthInfo;
import de.telekom.smartcredentials.networking.websocket.model.SuccessAuthInfo;
import de.telekom.smartcredentials.networking.websocket.model.WebSocketMessage;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static de.telekom.smartcredentials.core.qrlogin.TokenPluginError.EMPTY_MESSAGE;

public class EchoWebSocketListener extends WebSocketListener {

    private static final String WS_EMIT_EVENT_QR_SCANNED = "Scanning#QRCode";
    private static final String WS_EVENT_CONNECTION_SUCCESS = "Connection#Success";
    private static final String WS_EVENT_QR_AUTH_SUCCESS = "Success#QRCodeScan";
    private static final String WS_EVENT_QR_AUTH_FAIL = "Error#QRCodeScan";
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private static final String TAG = "EchoWebSocketListener";

    private final Map<String, String> mParamMap;
    private final TokenPluginCallback<TokenResponse, String> mPluginCallback;
    private final Gson mGson;

    public EchoWebSocketListener(Map<String, String> paramMap, TokenPluginCallback<TokenResponse, String> pluginCallback, Gson gson) {
        mParamMap = paramMap;
        mPluginCallback = pluginCallback;
        mGson = gson;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        // no implementation
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        ApiLoggerResolver.logEvent("Received server message: " + text);
        WebSocketMessage message = mGson.fromJson(text, WebSocketMessage.class);
        if (message == null) {
            onNullMessage(webSocket);
            return;
        }

        switch (message.mEvent) {
            case WS_EVENT_CONNECTION_SUCCESS:
                sendQrSocketMessage(webSocket);
                break;
            case WS_EVENT_QR_AUTH_SUCCESS:
                String data = mGson.toJson(message.mData);
                SuccessAuthInfo authInfo = mGson.fromJson(data, SuccessAuthInfo.class);
                if (authInfo.mAccessToken != null) {
                    long expiresIn;
                    try {
                        expiresIn = Long.parseLong(authInfo.mExpireTime);
                    } catch (NumberFormatException nfe) {
                        ApiLoggerResolver.logError(TAG, "Could not determine expiration time from: " + authInfo.mExpireTime);
                        expiresIn = -1;
                    }
                    mPluginCallback.onSuccess(new TokenResponse(authInfo.mAccessToken, System.currentTimeMillis() + expiresIn));
                } else {
                    mPluginCallback.onFailed(TokenPluginError.NULL_ACCESS_TOKEN.getDesc());
                }
                webSocket.close(NORMAL_CLOSURE_STATUS, null);
                break;
            case WS_EVENT_QR_AUTH_FAIL:
                String errorEnvelope = mGson.toJson(message.mData);
                ErrorAuthInfo info = mGson.fromJson(errorEnvelope, ErrorAuthInfo.class);
                ApiLoggerResolver.logError(TAG, info.mError.mErrorMessage);
                mPluginCallback.onFailed(TokenPluginError.FAILED_AUTHENTICATION.getDesc());
                webSocket.close(NORMAL_CLOSURE_STATUS, null);
                break;
            default:
                mPluginCallback.onFailed(TokenPluginError.UNKNOWN_EVENT.name());
                webSocket.close(NORMAL_CLOSURE_STATUS, null);
                break;
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        onMessage(webSocket, bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        ApiLoggerResolver.logEvent("Closing : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        ApiLoggerResolver.logEvent("Error : " + t.getMessage());
        mPluginCallback.onFailed(TokenPluginError.SOCKET_FAILED.getDesc());
    }

    public void onNullMessage(WebSocket webSocket) {
        if (mPluginCallback != null) {
            mPluginCallback.onFailed(EMPTY_MESSAGE.getDesc());
        }
        webSocket.close(NORMAL_CLOSURE_STATUS, EMPTY_MESSAGE.getDesc());
    }

    public void sendQrSocketMessage(WebSocket webSocket) {
        String qrCodeRaw = mParamMap.get(AuthParamKey.QR_CODE.name());
        BarcodeWrapper[] barcodeWrapperArray = mGson.fromJson(qrCodeRaw, BarcodeWrapper[].class);
        BarcodeWrapper barcodeWrapper = barcodeWrapperArray[0];
        WebSocketMessage webSocketMessage =
                new WebSocketMessage(WS_EMIT_EVENT_QR_SCANNED,
                        barcodeWrapper.mQrID,
                        barcodeWrapper.mBrowserClient,
                        mParamMap.get(AuthParamKey.ID_TOKEN.name()),
                        mParamMap.get(AuthParamKey.REFRESH_TOKEN.name()));
        webSocket.send(mGson.toJson(webSocketMessage, WebSocketMessage.class));
        ApiLoggerResolver.logEvent("Send server message, " + webSocketMessage);
    }
}

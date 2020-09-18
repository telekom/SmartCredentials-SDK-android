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

package de.telekom.smartcredentials.qrlogin.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import de.telekom.smartcredentials.core.model.token.TokenResponse;
import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;
import de.telekom.smartcredentials.core.qrlogin.TokenPluginError;
import de.telekom.smartcredentials.qrlogin.websocket.EchoWebSocketListener;
import de.telekom.smartcredentials.qrlogin.websocket.model.WebSocketMessage;
import okhttp3.WebSocket;
import okio.ByteString;

import static de.telekom.smartcredentials.core.qrlogin.TokenPluginError.EMPTY_MESSAGE;
import static de.telekom.smartcredentials.core.qrlogin.TokenPluginError.SOCKET_FAILED;
import static de.telekom.smartcredentials.core.qrlogin.TokenPluginError.UNKNOWN_EVENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class EchoWebSocketListenerTest {

    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();
    private WebSocket mWebSocket;
    private Gson mGson;
    private WebSocketMessage mWebSocketMessage;
    private TokenPluginCallback mPluginCallback;
    private EchoWebSocketListener mEchoWebSocketListener;

    private static final int NORMAL_CLOSURE_STATUS = 1000;

    @Before
    public void setUp() {
        mGson = new GsonBuilder().create();
        mPluginCallback = Mockito.mock(TokenPluginCallback.class);
        mWebSocket = Mockito.mock(WebSocket.class);
        mWebSocketMessage = TestUtils.createWebSocketMessage();

        mEchoWebSocketListener = new EchoWebSocketListener(TestUtils.getParams(), mPluginCallback, mGson);
    }

    @Test
    public void onMessageCallsOnFailedOnCallbackWhenResponseIsAuthFailed() {
        mEchoWebSocketListener.onMessage(mWebSocket, TestUtils.AUTH_FAIL_RESPONSE);

        verify(mPluginCallback).onFailed(TokenPluginError.FAILED_AUTHENTICATION.name());
        verify(mWebSocket).close(NORMAL_CLOSURE_STATUS, null);
    }

    @Test
    public void onMessageCallsOnSuccessOnCallbackWhenResponseIsAuthSuccessful() {
        mEchoWebSocketListener.onMessage(mWebSocket, TestUtils.AUTH_SUCCESS_RESPONSE);

        verify(mPluginCallback).onSuccess(any(TokenResponse.class));
        verify(mWebSocket).close(NORMAL_CLOSURE_STATUS, null);
    }

    @Test
    public void onMessageSendsQrSocketMessageWhenResponseIsConnectionSuccess() {
        String json = mGson.toJson(mWebSocketMessage, WebSocketMessage.class);

        mEchoWebSocketListener.onMessage(mWebSocket, TestUtils.CONNECTION_SUCCESS_RESPONSE);

        verify(mWebSocket).send(json);
    }

    @Test
    public void onMessageCallsOnNullMessageWhenResponseIsNull() {
        mEchoWebSocketListener.onMessage(mWebSocket, (String) null);

        verify(mPluginCallback).onFailed(EMPTY_MESSAGE.name());
        verify(mWebSocket).close(NORMAL_CLOSURE_STATUS, EMPTY_MESSAGE.getDesc());
    }

    @Test
    public void onMessageNotifiesFailedWhenResponseDoesNotHaveACustomHandling() {
        mEchoWebSocketListener.onMessage(mWebSocket, TestUtils.OTHER_RESPONSE);

        verify(mPluginCallback).onFailed(UNKNOWN_EVENT.name());
        verify(mWebSocket).close(NORMAL_CLOSURE_STATUS, null);
    }


    @Test
    public void onMessageTransformsByteStringToStringAndCallsOnMessage() {
        ByteString byteString = ByteString.decodeBase64("SomeString");
        EchoWebSocketListener listener = Mockito.spy(mEchoWebSocketListener);
        doNothing().when(listener).onMessage(mWebSocket, byteString.hex());

        listener.onMessage(mWebSocket, byteString);

        verify(listener).onMessage(mWebSocket, byteString.hex());
    }

    @Test
    public void onClosingCallsCloseMethodOnWebSocket() {
        mEchoWebSocketListener.onClosing(mWebSocket, 0, "reason");

        verify(mWebSocket).close(NORMAL_CLOSURE_STATUS, null);
    }

    @Test
    public void onFailureCallsOnFailedWithMessageOnPluginCallback() {
        String throwableMessage = "error";

        mEchoWebSocketListener.onFailure(mWebSocket, new Throwable(throwableMessage), any());

        verify(mPluginCallback).onFailed(SOCKET_FAILED.name());
    }

    @Test
    public void onNullMessageCallsOnFailedOnPluginCallbackAndClosingWebSocket() {
        mEchoWebSocketListener.onNullMessage(mWebSocket);

        verify(mPluginCallback).onFailed(EMPTY_MESSAGE.name());
        verify(mWebSocket).close(NORMAL_CLOSURE_STATUS, EMPTY_MESSAGE.getDesc());
    }

    @Test
    public void sendQrSocketMessageSendMessageOnWebSocket() {
        String json = mGson.toJson(mWebSocketMessage, WebSocketMessage.class);

        mEchoWebSocketListener.sendQrSocketMessage(mWebSocket);

        verify(mWebSocket).send(json);
    }

}

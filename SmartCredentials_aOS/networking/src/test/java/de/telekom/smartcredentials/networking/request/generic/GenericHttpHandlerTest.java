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

package de.telekom.smartcredentials.networking.request.generic;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.net.SocketFactory;

import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;
import de.telekom.smartcredentials.networking.request.models.FailedRequest;
import de.telekom.smartcredentials.networking.request.models.ModelConverter;
import de.telekom.smartcredentials.networking.request.models.RequestParams;
import de.telekom.smartcredentials.core.networking.RequestFailureLevel;
import de.telekom.smartcredentials.networking.request.models.enums.SocketFactoryFailure;
import de.telekom.smartcredentials.networking.request.service.Service;
import de.telekom.smartcredentials.networking.request.socket.SocketFactoryCallback;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({ModelConverter.class, FailedRequest.class})
public class GenericHttpHandlerTest {

    private Context mContext;
    private GenericHttpHandler mGenericHttpHandler;
    private Service mService;
    private de.telekom.smartcredentials.core.model.request.RequestParams mReqParams;
    private ServicePluginCallback mServicePluginCallback;
    private RequestParams mRequestParams;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(ModelConverter.class);
        PowerMockito.mockStatic(FailedRequest.class);

        mContext = Mockito.mock(Context.class);
        mService = Mockito.mock(Service.class);
        mReqParams = Mockito.mock(de.telekom.smartcredentials.core.model.request.RequestParams.class);
        mServicePluginCallback = Mockito.mock(ServicePluginCallback.class);
        mRequestParams = Mockito.mock(RequestParams.class);

        mGenericHttpHandler = new GenericHttpHandler(mService);
    }

    @Test
    public void performRequestCallsPrepareSocketFactory() {
        when(ModelConverter.toRequestParams(mReqParams)).thenReturn(mRequestParams);
        SocketFactoryCallback socketFactoryCallback = Mockito.mock(SocketFactoryCallback.class);

        GenericHttpHandler genericHttpHandler = Mockito.spy(mGenericHttpHandler);
        when(genericHttpHandler.getSocketFactoryCallback(mRequestParams, mServicePluginCallback))
                .thenReturn(socketFactoryCallback);

        genericHttpHandler.performRequest(mContext, mReqParams, mServicePluginCallback);

        verify(mService).prepareSocketFactory(mContext, mRequestParams.getConnectionType(), socketFactoryCallback);
    }

    @Test
    public void socketFactoryCallbackCallsMethodsWhenSocketFactoryCreated() {
        SocketFactoryCallback socketFactoryCallback = mGenericHttpHandler
                .getSocketFactoryCallback(mRequestParams, mServicePluginCallback);
        SocketFactory socketFactory = Mockito.mock(SocketFactory.class);

        socketFactoryCallback.onSocketFactoryCreated(socketFactory);

        verify(mRequestParams).setSocketFactory(socketFactory);
        verify(mService).createRequest(mRequestParams, mServicePluginCallback);
    }

    @Test
    public void socketFactoryCallbackCallsMethodsWhenSocketFactoryFailed() {
        SocketFactoryCallback socketFactoryCallback = mGenericHttpHandler
                .getSocketFactoryCallback(mRequestParams, mServicePluginCallback);
        FailedRequest request = Mockito.mock(FailedRequest.class);
        when(FailedRequest.newInstance(RequestFailureLevel.SOCKET_CREATION,
                SocketFactoryFailure.NETWORK_UNAVAILABLE))
                .thenReturn(request);

        socketFactoryCallback.onSocketFactoryFailed(SocketFactoryFailure.NETWORK_UNAVAILABLE);

        verify(mServicePluginCallback).onFailed(request);
    }
}

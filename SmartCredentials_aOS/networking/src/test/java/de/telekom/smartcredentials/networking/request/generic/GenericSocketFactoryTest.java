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
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.networking.request.models.enums.ConnectionType;
import de.telekom.smartcredentials.networking.request.models.enums.SocketFactoryFailure;
import de.telekom.smartcredentials.networking.request.socket.SocketFactoryCallback;
import de.telekom.smartcredentials.networking.request.utils.PermissionsUtil;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PermissionsUtil.class, ConnectionTypeRequest.class,
        UnavailableNetworkListener.class})
public class GenericSocketFactoryTest {

    private Context mContext;
    private ConnectionType mConnectionType;
    private SocketFactoryCallback mSocketFactoryCallback;
    private UnavailableNetworkListener mUnavailableNetworkListener;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(PermissionsUtil.class);
        PowerMockito.mockStatic(ConnectionTypeRequest.class);
        PowerMockito.mockStatic(UnavailableNetworkListener.class);

        mContext = Mockito.mock(Context.class);
        mConnectionType = ConnectionType.WIFI;
        mSocketFactoryCallback = Mockito.mock(SocketFactoryCallback.class);
        mUnavailableNetworkListener = Mockito.mock(UnavailableNetworkListener.class);
    }

    @Test
    public void createSocketFactoryCallsFactoryFailedIfWriteSettingsPermissionRequired() {
        when(PermissionsUtil.checkRequiresWriteSettingsPermission(mContext)).thenReturn(true);

        GenericSocketFactory.createSocketFactory(mContext, mConnectionType, mSocketFactoryCallback);

        verify(mSocketFactoryCallback).onSocketFactoryFailed(SocketFactoryFailure.MISSING_PERMISSION);
    }


    @PrepareForTest({PermissionsUtil.class, ConnectionTypeRequest.class,
            UnavailableNetworkListener.class, GenericSocketFactory.NetworkCallback.class})
    @Test
    public void createSocketFactoryRequestsNetwork() {
        PowerMockito.mockStatic(GenericSocketFactory.NetworkCallback.class);

        when(PermissionsUtil.checkRequiresWriteSettingsPermission(mContext)).thenReturn(false);

        ConnectivityManager connectivityManager = Mockito.mock(ConnectivityManager.class);
        when(mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);

        NetworkRequest networkRequest = Mockito.mock(NetworkRequest.class);
        when(ConnectionTypeRequest.getNetworkRequest(mConnectionType)).thenReturn(networkRequest);

        when(UnavailableNetworkListener.getInstance()).thenReturn(mUnavailableNetworkListener);

        GenericSocketFactory.NetworkCallback networkCallback =
                Mockito.mock(GenericSocketFactory.NetworkCallback.class);
        when(GenericSocketFactory.NetworkCallback.getInstance(mSocketFactoryCallback, mUnavailableNetworkListener))
                .thenReturn(networkCallback);

        GenericSocketFactory.createSocketFactory(mContext, mConnectionType, mSocketFactoryCallback);

        verify(connectivityManager).requestNetwork(networkRequest, networkCallback);
        verify(mUnavailableNetworkListener).listenNetworkUnavailable(mSocketFactoryCallback);
    }

    @Test
    public void callingOnAvailableOnNetworkCallbackNotifiesSocketCreated() {
        GenericSocketFactory.NetworkCallback networkCallback =
                GenericSocketFactory.NetworkCallback.getInstance(mSocketFactoryCallback, mUnavailableNetworkListener);
        GenericSocketFactory.connectivityManager = Mockito.mock(ConnectivityManager.class);

        Network network = Mockito.mock(Network.class);
        networkCallback.onAvailable(network);

        verify(mUnavailableNetworkListener).unregister();
        verify(mSocketFactoryCallback).onSocketFactoryCreated(network.getSocketFactory());
        verify(GenericSocketFactory.connectivityManager).unregisterNetworkCallback(networkCallback);
    }

    @Test
    public void callingOnUnavailableOnNetworkCallbackNotifiesSocketFailed() {
        GenericSocketFactory.NetworkCallback networkCallback =
                GenericSocketFactory.NetworkCallback.getInstance(mSocketFactoryCallback, mUnavailableNetworkListener);
        GenericSocketFactory.connectivityManager = Mockito.mock(ConnectivityManager.class);

        networkCallback.onUnavailable();

        verify(mUnavailableNetworkListener).unregister();
        verify(mSocketFactoryCallback).onSocketFactoryFailed(SocketFactoryFailure.NETWORK_UNAVAILABLE);
        verify(GenericSocketFactory.connectivityManager).unregisterNetworkCallback(networkCallback);
    }
}
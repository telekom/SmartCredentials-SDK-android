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

package de.telekom.smartcredentials.wispr.sockets;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

import javax.net.SocketFactory;

import de.telekom.smartcredentials.wispr.exceptions.NotOnRequiredNetworkException;

public class SocketManager {

    private static final int TYPE_MOBILE = 0;
    public static final int TYPE_WIFI = 1;

    public SocketManager() {

    }

    public void createSocketFactory(Context context, int socketType, SocketCallback socketCallback) throws NotOnRequiredNetworkException {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createLollipopSocketFactory(connectivityManager, socketType, socketCallback);
        } else {
            createPreLollipopSocketFactory(connectivityManager, socketType, socketCallback);
        }
    }

    private void createPreLollipopSocketFactory(ConnectivityManager connectivityManager, int socketType, SocketCallback socketCallback) throws NotOnRequiredNetworkException {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == (socketType == TYPE_MOBILE ? ConnectivityManager.TYPE_MOBILE : ConnectivityManager.TYPE_WIFI)) {
            socketCallback.onSocketFactoryAvailable(SocketFactory.getDefault());
        } else {
            throw new NotOnRequiredNetworkException();
        }
    }

    private void createLollipopSocketFactory(ConnectivityManager connectivityManager,
                                             int socketType,
                                             SocketCallback socketCallback) {
        final NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addTransportType(socketType == TYPE_MOBILE ? NetworkCapabilities.TRANSPORT_CELLULAR : NetworkCapabilities.TRANSPORT_WIFI)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connectivityManager.requestNetwork(networkRequest, socketCallback);
    }
}

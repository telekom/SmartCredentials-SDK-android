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

import javax.net.SocketFactory;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.networking.request.models.enums.ConnectionType;
import de.telekom.smartcredentials.networking.request.models.enums.SocketFactoryFailure;
import de.telekom.smartcredentials.networking.request.socket.SocketFactoryCallback;
import de.telekom.smartcredentials.networking.request.utils.PermissionsUtil;

import static de.telekom.smartcredentials.networking.request.generic.ConnectionTypeRequest.getNetworkRequest;
import static de.telekom.smartcredentials.networking.request.models.enums.SocketFactoryFailure.TOO_MANY_REQUESTS;

class GenericSocketFactory {

    private static final String TAG = GenericSocketFactory.class.getSimpleName();

    static ConnectivityManager connectivityManager;
    private static int requestCount = 0;
    private static int unregisterCount = 0;

    static void createSocketFactory(Context context, ConnectionType connectionType, SocketFactoryCallback socketFactoryCallback) {
        if (PermissionsUtil.checkRequiresWriteSettingsPermission(context)) {
            socketFactoryCallback.onSocketFactoryFailed(SocketFactoryFailure.MISSING_PERMISSION);
            return;
        }

        init(context);

        final NetworkRequest networkRequest = getNetworkRequest(connectionType);
        UnavailableNetworkListener unavailableNetworkListener = UnavailableNetworkListener.getInstance();
        NetworkCallback networkCallback = NetworkCallback.getInstance(socketFactoryCallback, unavailableNetworkListener);
        try {
            connectivityManager.requestNetwork(networkRequest, networkCallback);
            requestCount++;
        } catch (RuntimeException e) {
            // despite calling unregister for all network callbacks, the system leaked callbacks for this pid.
            // api <= 26 throws IllegalStateExceptions, android > 26 throws TooManyRequestsExceptions. crashes
            // were observed on several android 7 and 8 devices. killing the process means we get a new pid
            // and can request networks again. we keep track of request and unregister counts to learn about
            // device specific limits. the limit for api <= 26 so far was roughly at ~90 requests/unregister.
            ApiLoggerResolver.logError(TAG, "requestNetwork failed: requested " + requestCount + ", unregistered: " + unregisterCount);
            socketFactoryCallback.onSocketFactoryFailed(TOO_MANY_REQUESTS);
            System.exit(0);
        }
        unavailableNetworkListener.listenNetworkUnavailable(socketFactoryCallback);
    }

    private static void init(Context context) {
        if (connectivityManager == null) {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    static class NetworkCallback extends ConnectivityManager.NetworkCallback {
        final SocketFactoryCallback mSocketFactoryCallback;
        private final UnavailableNetworkListener mUnavailableNetworkListener;

        private NetworkCallback(SocketFactoryCallback socketFactoryCallback, UnavailableNetworkListener unavailableNetworkListener) {
            mSocketFactoryCallback = socketFactoryCallback;
            mUnavailableNetworkListener = unavailableNetworkListener;
        }

        static NetworkCallback getInstance(SocketFactoryCallback socketFactoryCallback, UnavailableNetworkListener unavailableNetworkListener) {
            return new NetworkCallback(socketFactoryCallback, unavailableNetworkListener);
        }

        @Override
        public void onAvailable(Network network) {
            mUnavailableNetworkListener.unregister();
            SocketFactory socketFactory = network.getSocketFactory();
            mSocketFactoryCallback.onSocketFactoryCreated(socketFactory);

            unregisterCallbacks();
        }

        @Override
        public void onUnavailable() {
            mUnavailableNetworkListener.unregister();
            mSocketFactoryCallback.onSocketFactoryFailed(SocketFactoryFailure.NETWORK_UNAVAILABLE);
            unregisterCallbacks();
        }

        void unregisterCallbacks() {
            connectivityManager.unregisterNetworkCallback(this);
            unregisterCount++;
        }
    }
}

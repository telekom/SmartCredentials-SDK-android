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

import de.telekom.smartcredentials.networking.request.models.enums.SocketFactoryFailure;
import de.telekom.smartcredentials.networking.request.socket.SocketFactoryCallback;

class UnavailableNetworkListener {

    private static final int SOCKET_FACTORY_TIMEOUT = 4000;

    private final android.os.Handler handler = new android.os.Handler();
    private NetworkListenerRunnable runnable;

    private UnavailableNetworkListener() {
    }

    static UnavailableNetworkListener getInstance() {
        return new UnavailableNetworkListener();
    }

    void listenNetworkUnavailable(SocketFactoryCallback socketFactoryCallback) {
        runnable = new NetworkListenerRunnable(socketFactoryCallback);
        handler.postDelayed(runnable, SOCKET_FACTORY_TIMEOUT);
    }

    void unregister() {
        if (runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    private class NetworkListenerRunnable implements Runnable {
        private final SocketFactoryCallback mSocketFactoryCallback;

        NetworkListenerRunnable(SocketFactoryCallback socketFactoryCallback) {
            mSocketFactoryCallback = socketFactoryCallback;
        }

        @Override
        public void run() {
            mSocketFactoryCallback.onSocketFactoryFailed(SocketFactoryFailure.NETWORK_UNAVAILABLE);
            handler.removeCallbacks(this);
        }
    }
}

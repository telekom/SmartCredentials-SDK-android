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

package de.telekom.smartcredentials.qrlogin.di;

import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.api.NetworkingApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.qrlogin.controllers.QrLoginController;

public class ObjectGraphCreatorQrLogin {

    private static ObjectGraphCreatorQrLogin sInstance;

    private NetworkingApi mNetworkingApi;
    private AuthorizationApi mAuthorizationApi;

    public static ObjectGraphCreatorQrLogin getInstance() {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorQrLogin();
        }
        return sInstance;
    }

    public void init(AuthorizationApi authorizationApi, NetworkingApi networkingApi) {
        mAuthorizationApi = authorizationApi;
        mNetworkingApi = networkingApi;
    }

    @NonNull
    public QrLoginController provideApiControllerQrLogin(CoreController coreController) {
        return new QrLoginController(coreController, getAuthorizationApi(),
                getNetworkingApi());
    }

    public NetworkingApi getNetworkingApi() {
        if (mNetworkingApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.NETWORKING_MODULE + " from "
                    + SmartCredentialsModuleSet.QR_LOGIN_MODULE + " has not been initialized");
        }
        return mNetworkingApi;
    }

    public AuthorizationApi getAuthorizationApi() {
        if (mAuthorizationApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.AUTHORIZATION_MODULE + " from "
                    + SmartCredentialsModuleSet.QR_LOGIN_MODULE + " has not been initialized");
        }
        return mAuthorizationApi;
    }

    public static void destroy() {
        sInstance = null;
    }
}

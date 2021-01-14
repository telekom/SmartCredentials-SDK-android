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

package de.telekom.smartcredentials.qrlogin.factory;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.NetworkingApi;
import de.telekom.smartcredentials.core.api.QrLoginApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;
import de.telekom.smartcredentials.qrlogin.controllers.QrLoginController;
import de.telekom.smartcredentials.qrlogin.di.ObjectGraphCreatorQrLogin;

@SuppressWarnings("unused")
public class SmartCredentialsQrLoginFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials QrLogin Module have not been initialized";

    private static QrLoginController sQrLoginController;

    private SmartCredentialsQrLoginFactory() {
        // required empty constructor
    }

    @NonNull
    public static synchronized QrLoginApi initSmartCredentialsQrLoginModule(@NonNull final CoreApi coreApi,
                                                                            @NonNull final AuthorizationApi authorizationApi,
                                                                            @NonNull final NetworkingApi networkingApi) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.QR_LOGIN_MODULE.getModuleName());
        }

        ObjectGraphCreatorQrLogin objectGraphCreatorQrLogin = ObjectGraphCreatorQrLogin.getInstance();
        objectGraphCreatorQrLogin.init(authorizationApi, networkingApi);
        sQrLoginController = objectGraphCreatorQrLogin.provideApiControllerQrLogin(coreController);
        return sQrLoginController;
    }

    @NonNull
    public static synchronized QrLoginApi getQrLoginApi() {
        if (sQrLoginController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sQrLoginController;
    }

    public static void clear() {
        ObjectGraphCreatorQrLogin.destroy();
        sQrLoginController = null;
    }
}

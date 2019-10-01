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

package de.telekom.smartcredentials.networking.factory;

import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.NetworkingApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;
import de.telekom.smartcredentials.networking.controllers.NetworkingController;
import de.telekom.smartcredentials.networking.di.ObjectGraphCreatorNetworking;

@SuppressWarnings("unused")
public class SmartCredentialsNetworkingFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials Networking Module have not been initialized";

    private static NetworkingController sNetworkingController;

    private SmartCredentialsNetworkingFactory() {
        // required empty constructor
    }

    @NonNull
    public static synchronized NetworkingApi initSmartCredentialsNetworkingModule(@NonNull final CoreApi coreApi) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.NETWORKING_MODULE.getModuleName());
        }
        sNetworkingController = ObjectGraphCreatorNetworking.getInstance()
                .provideApiControllerNetworking(coreController);
        return sNetworkingController;
    }

    @NonNull
    public static synchronized NetworkingApi getNetworkingApi() {
        if (sNetworkingController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sNetworkingController;
    }

    public static void clear() {
        ObjectGraphCreatorNetworking.destroy();
        sNetworkingController = null;
    }
}

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

package de.telekom.smartcredentials.eid.factory;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.EidApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.eid.EidConfiguration;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;
import de.telekom.smartcredentials.eid.controllers.EidController;
import de.telekom.smartcredentials.eid.controllers.Rx2EidController;
import de.telekom.smartcredentials.eid.controllers.Rx3EidController;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
@SuppressWarnings("unused")
public class SmartCredentialsEidFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials e-ID Module have not been initialized";

    private static EidController sEidController;

    private SmartCredentialsEidFactory() {
        // required empty constructor
    }

    @NonNull
    public static synchronized EidApi initSmartCredentialsEidModule(@NonNull CoreApi coreApi,
                                                                    @NonNull final EidConfiguration configuration) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.EID_MODULE.getModuleName());
        }

        sEidController = new EidController(coreController);
        sEidController.setConfiguration(configuration);
        return sEidController;
    }

    @NonNull
    public static synchronized EidController getEidApi() {
        if (sEidController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sEidController;
    }

    public static synchronized Rx2EidController getRx2EidApi() {
        if (sEidController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return new Rx2EidController(sEidController);
    }

    public static synchronized Rx3EidController getRx3EidApi() {
        if (sEidController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return new Rx3EidController(sEidController);
    }

    public static void clear() {
        sEidController.destroy();
        sEidController = null;
    }
}

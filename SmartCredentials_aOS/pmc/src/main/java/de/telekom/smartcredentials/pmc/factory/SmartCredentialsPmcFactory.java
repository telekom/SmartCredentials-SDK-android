/*
 * Copyright (c) 2021 Telekom Deutschland AG
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

package de.telekom.smartcredentials.pmc.factory;

import androidx.annotation.NonNull;

import java.util.List;

import de.telekom.smartcredentials.core.api.PolicyApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.pmc.PmcController;
import de.telekom.smartcredentials.pmc.Policy;
import de.telekom.smartcredentials.pmc.PolicySchemaResponse;

/**
 * Created by Alex.Graur@endava.com at 2/11/2021
 */
public class SmartCredentialsPmcFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials PMC Module have not been initialized";

    private static PmcController sPmcController;

    private SmartCredentialsPmcFactory() {
        // required empty constructor
    }

    @NonNull
    public static synchronized PmcController initSmartCredentialsPmcModule(StorageApi storageApi) {
        sPmcController = new PmcController(storageApi);
        return sPmcController;
    }

    @NonNull
    public static synchronized PmcController getPolicyApi() {
        if (sPmcController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sPmcController;
    }

    public static void clear() {
        sPmcController = null;
    }
}

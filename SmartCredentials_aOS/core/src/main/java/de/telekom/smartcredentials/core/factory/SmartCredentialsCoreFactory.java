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

package de.telekom.smartcredentials.core.factory;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.di.ObjectGraphCreator;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.utils.SystemPropertyMapper;

@SuppressWarnings("unused")
public class SmartCredentialsCoreFactory {

    public static final String MODULE_NOT_INITIALIZED_EXCEPTION = "Smart Credentials Core module not initialized; first call initialize(configuration) to initialize it.";

    private static CoreApi sCoreApi;

    private SmartCredentialsCoreFactory() {
        // required empty constructor
    }

    /**
     * Initializes {@link CoreApi}.
     *
     * @param configuration {@link SmartCredentialsConfiguration} wrapping library configurations
     * @return a {@link CoreApi} instance
     */
    @SuppressWarnings("unused")
    @NonNull
    public static synchronized CoreApi initialize(@NonNull SmartCredentialsConfiguration configuration) {
        Context context = configuration.getContext();
        initiateBlacklisting(context);
        ApiLoggerResolver.setApiLogger(configuration.getLogger());
        sCoreApi = ObjectGraphCreator.getInstance(context, configuration.isRootCheckerEnabled(),
                configuration.getRootDetectionOptions()).provideCoreController(context);
        ((CoreController) sCoreApi).setUserId(configuration.getUserId());
        ((CoreController) sCoreApi).setAppAlias(configuration.getAppAlias());
        return sCoreApi;
    }

    /**
     * Gets the initialized instance of the {@link CoreApi}.
     *
     * @return a {@link CoreApi} instance
     */
    @SuppressWarnings("unused")
    @NonNull
    public static synchronized CoreApi getSmartCredentialsCoreApi() {
        if (sCoreApi == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sCoreApi;
    }

    private static void initiateBlacklisting(Context context) {
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                SystemPropertyMapper.initBlacklisting(contexts[0]);
                return null;
            }
        }.execute(context);
    }

    public static void clear() {
        ObjectGraphCreator.destroy();
        sCoreApi = null;
    }
}

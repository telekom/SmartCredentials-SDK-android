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

package de.telekom.smartcredentials.authentication.controllers;

import de.telekom.smartcredentials.authentication.SmartCredentialsAuthenticationService;
import de.telekom.smartcredentials.core.api.AuthenticationApi;
import de.telekom.smartcredentials.core.authentication.AuthenticationService;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

public class AuthenticationController implements AuthenticationApi {

    private final CoreController mCoreController;

    public AuthenticationController(CoreController coreController) {
        mCoreController = coreController;
    }

    private Object getAuthenticationTool() {
        return SmartCredentialsAuthenticationService.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<AuthenticationService> getAuthenticationService() {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getAuthenticationService");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHENTICATION)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHENTICATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        AuthenticationService authService = (AuthenticationService) getAuthenticationTool();
        return new SmartCredentialsResponse<>(authService);
    }
}

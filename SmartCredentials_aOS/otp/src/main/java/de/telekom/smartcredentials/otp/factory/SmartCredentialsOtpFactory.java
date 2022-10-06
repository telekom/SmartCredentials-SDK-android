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

package de.telekom.smartcredentials.otp.factory;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;

import java.util.List;

import de.telekom.smartcredentials.core.api.CameraApi;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.OtpApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;
import de.telekom.smartcredentials.otp.controllers.OtpController;
import de.telekom.smartcredentials.otp.di.ObjectGraphCreatorOtp;

@SuppressWarnings("unused")
public class SmartCredentialsOtpFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials Otp Module have not been initialized";

    private static OtpController sOtpController;

    private SmartCredentialsOtpFactory() {
        // required empty constructor
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized OtpApi<PreviewView> initSmartCredentialsOtpModule(@NonNull final CoreApi coreApi,
                                                                                 @NonNull final SecurityApi securityApi,
                                                                                 @NonNull final StorageApi storageApi,
                                                                                 @NonNull final CameraApi<PreviewView> cameraApi) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.OTP_MODULE.getModuleName());
        }
        ObjectGraphCreatorOtp objectGraphCreatorOtp = ObjectGraphCreatorOtp.getInstance();
        objectGraphCreatorOtp.init(securityApi, storageApi, cameraApi);
        sOtpController = objectGraphCreatorOtp.provideApiControllerOtp(coreController);
        return sOtpController;
    }

    public static void addAcceptedAlgorithms(@NonNull final List<String> acceptedMacAlgorithms) {
        ObjectGraphCreatorOtp.getInstance().addAcceptedMacAlgorithms(acceptedMacAlgorithms);
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized OtpApi<PreviewView> getOtpApi() {
        if (sOtpController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sOtpController;
    }

    public static void clear() {
        ObjectGraphCreatorOtp.destroy();
        sOtpController = null;
    }
}

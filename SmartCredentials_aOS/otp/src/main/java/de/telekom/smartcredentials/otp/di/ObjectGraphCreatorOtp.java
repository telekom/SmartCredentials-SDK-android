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

package de.telekom.smartcredentials.otp.di;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.telekom.smartcredentials.core.api.CameraApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.security.MacAlgorithm;
import de.telekom.smartcredentials.otp.callback.ScannerPluginCallbackForOTP;
import de.telekom.smartcredentials.otp.controllers.OtpController;
import de.telekom.smartcredentials.otp.converters.OTPUriParser;
import de.telekom.smartcredentials.otp.converters.UriToItemDomainModelConverter;
import de.telekom.smartcredentials.otp.task.TaskManager;

public class ObjectGraphCreatorOtp {

    private static ObjectGraphCreatorOtp sInstance;

    private SecurityApi mSecurityApi;
    private StorageApi mStorageApi;
    private CameraApi mCameraApi;
    private Set<String> mAcceptedMacAlgorithms;

    private ObjectGraphCreatorOtp() {
        // required empty constructor
    }

    public static ObjectGraphCreatorOtp getInstance() {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorOtp();
        }
        return sInstance;
    }

    public void init(SecurityApi securityApi, StorageApi storageApi, CameraApi cameraApi) {
        mSecurityApi = securityApi;
        mStorageApi = storageApi;
        mCameraApi = cameraApi;
        initMacAlgorithms();
    }

    private void initMacAlgorithms() {
        mAcceptedMacAlgorithms = new HashSet<>();
        mAcceptedMacAlgorithms.addAll(Collections.unmodifiableList(
                Arrays.asList(MacAlgorithm.SHA256, MacAlgorithm.SHA384, MacAlgorithm.SHA512)));
    }

    public void addAcceptedMacAlgorithms(List<String> acceptedMacAlgorithms) {
        if (acceptedMacAlgorithms != null) {
            mAcceptedMacAlgorithms.addAll(acceptedMacAlgorithms);
        }
    }

    public static void destroy() {
        sInstance = null;
    }

    @NonNull
    public OtpController provideApiControllerOtp(@NonNull final CoreController coreController) {
        return new OtpController(coreController, getSecurityApi(), getStorageApi(), getCameraApi(),
                provideScannerPluginCallbackForOtp(getStorageApi()), getOtpTaskManager());
    }

    private ScannerPluginCallbackForOTP provideScannerPluginCallbackForOtp(StorageApi storageApi) {
        return new ScannerPluginCallbackForOTP(storageApi, provideUriToIDMConverter());
    }

    private UriToItemDomainModelConverter provideUriToIDMConverter() {
        return new UriToItemDomainModelConverter(provideOtpUriParser());
    }

    private OTPUriParser provideOtpUriParser() {
        return new OTPUriParser();
    }

    private TaskManager getOtpTaskManager() {
        return new TaskManager();
    }

    private SecurityApi getSecurityApi() {
        if (mSecurityApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.SECURITY_MODULE + " from "
                    + SmartCredentialsModuleSet.OTP_MODULE + " has not been initialized");
        }
        return mSecurityApi;
    }

    private StorageApi getStorageApi() {
        if (mStorageApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.STORAGE_MODULE + " from "
                    + SmartCredentialsModuleSet.OTP_MODULE + " has not been initialized");
        }
        return mStorageApi;
    }

    private CameraApi getCameraApi() {
        if (mCameraApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.CAMERA_MODULE + " from "
                    + SmartCredentialsModuleSet.OTP_MODULE + " has not been initialized");
        }
        return mCameraApi;
    }

    public Set<String> getAcceptedAlgorithms() {
        return mAcceptedMacAlgorithms;
    }
}

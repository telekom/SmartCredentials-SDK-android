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

package de.telekom.smartcredentials.otp.controllers;

import android.content.Context;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.LifecycleOwner;

import de.telekom.smartcredentials.core.api.CameraApi;
import de.telekom.smartcredentials.core.api.OtpApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.camera.BarcodeType;
import de.telekom.smartcredentials.core.camera.SurfaceContainer;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.otp.HOTPHandlerCallback;
import de.telekom.smartcredentials.core.otp.OTPHandlerCallback;
import de.telekom.smartcredentials.core.otp.OTPImporterCallback;
import de.telekom.smartcredentials.core.otp.TOTPHandlerCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.OTPScannerPluginCallback;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.otp.callback.PluginCallbackOtpConverter;
import de.telekom.smartcredentials.otp.callback.ScannerPluginCallbackForOTP;
import de.telekom.smartcredentials.otp.task.InitOTPTask;
import de.telekom.smartcredentials.otp.task.TaskManager;

public class OtpController implements OtpApi<PreviewView> {

    private final CoreController mCoreController;
    private final SecurityApi mSecurityApi;
    private final CameraApi<PreviewView> mCameraApi;
    private final StorageApi mStorageApi;
    private final ScannerPluginCallbackForOTP mScannerPluginCallbackForOTP;
    private final TaskManager mTaskManager;

    public OtpController(@NonNull final CoreController coreController,
                         @NonNull final SecurityApi securityApi,
                         @NonNull final StorageApi storageApi,
                         @NonNull final CameraApi<PreviewView> cameraApi,
                         @NonNull final ScannerPluginCallbackForOTP scannerPluginCallbackForOTP,
                         @NonNull final TaskManager taskManager) {
        mCoreController = coreController;
        mSecurityApi = securityApi;
        mCameraApi = cameraApi;
        mStorageApi = storageApi;
        mScannerPluginCallbackForOTP = scannerPluginCallbackForOTP;
        mTaskManager = taskManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Void> createHOTPGenerator(@NonNull String hotpItemId, @NonNull HOTPHandlerCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "createHOTPGenerator");
        return getOTPGenerator(SmartCredentialsFilterFactory.createSensitiveItemFilter(hotpItemId, OTPType.HOTP.getDesc()),
                callback, OTPType.HOTP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Void> createTOTPGenerator(@NonNull String totpItemId, @NonNull TOTPHandlerCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "createTOTPGenerator");
        return getOTPGenerator(SmartCredentialsFilterFactory.createSensitiveItemFilter(totpItemId, OTPType.TOTP.getDesc()),
                callback, OTPType.TOTP);
    }

    @Override
    public SmartCredentialsApiResponse<Boolean> importOTPItemViaQRForId(@NonNull Context context,
                                                                        SurfaceContainer<PreviewView> surfaceContainer,
                                                                        LifecycleOwner lifecycleOwner,
                                                                        @NonNull String itemId,
                                                                        OTPImporterCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "importOTPItemViaQRForId");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.OTP_VIA_QR)) {
            String errorMessage = SmartCredentialsFeatureSet.OTP_VIA_QR.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        OTPScannerPluginCallback pluginCallback = PluginCallbackOtpConverter
                .convertToDomainPluginCallback(callback, getClass().getSimpleName());

        mScannerPluginCallbackForOTP.init(itemId, mCoreController.getUserId(), pluginCallback);

        mCameraApi.getBarcodeScannerView(context,
                surfaceContainer, lifecycleOwner, mScannerPluginCallbackForOTP, BarcodeType.BARCODE_2D_QR_CODE).getData();
        return new SmartCredentialsResponse<>(true);
    }

    /**
     * Generates OTP values, based on the provided otp item id.
     */
    private SmartCredentialsApiResponse<Void> getOTPGenerator(@NonNull SmartCredentialsFilter smartCredentialsFilter,
                                                              @NonNull OTPHandlerCallback callback, OTPType otpType) {
        SmartCredentialsFeatureSet mOtpFeatureSet = null;

        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (otpType == OTPType.TOTP) {
            mOtpFeatureSet = SmartCredentialsFeatureSet.OTP_TOTP;
        } else if (otpType == OTPType.HOTP) {
            mOtpFeatureSet = SmartCredentialsFeatureSet.OTP_HOTP;
        }

        if (mCoreController.isDeviceRestricted(mOtpFeatureSet)) {
            String errorMessage = mOtpFeatureSet.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        ItemDomainModel itemDomainModel = smartCredentialsFilter.toItemDomainModel(mCoreController.getUserId());
        initOTP(callback, itemDomainModel);

        return new SmartCredentialsResponse<>((Void) null);
    }

    /**
     * Starts the initialisation of the OTP task.
     */
    private void initOTP(OTPHandlerCallback otpHandlerCallback, ItemDomainModel itemDomainModel) {
        InitOTPTask initOTPTask = mTaskManager.getInitOTPTask(mSecurityApi, mStorageApi,
                otpHandlerCallback, itemDomainModel);
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            initOTPTask.run();
        } else {
            initOTPTask.getOTPHandlerResolver()
                    .resolveHandler(mSecurityApi, mStorageApi, otpHandlerCallback, itemDomainModel);
        }
    }
}

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

package de.telekom.smartcredentials.camera.views.presenters;

import android.os.Build;

import java.lang.ref.WeakReference;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED;
import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.DETECTOR_NOT_OPERATIONAL;
import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.DETECTOR_UNAVAILABLE;
import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.GOOGLE_PLAY_SERVICES_UNAVAILABLE;
import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.LOW_STORAGE;

public class CameraScannerPresenter extends CameraViewPresenter {

    private static final String SMART_CREDENTIALS_EVENT_TYPE = "SmartCredentials";
    private static final String TAG = "CameraScannerPresenter";

    public boolean mIsCameraStarted;
    private WeakReference<CameraScannerView> mView;

    @Override
    public String getTAG() {
        return TAG;
    }

    public void viewReady(CameraScannerView cameraScannerView) {
        mView = new WeakReference<>(cameraScannerView);
    }

    public void startCameraRequested() {
        new WeakRefClassResolver<CameraScannerView>() {
            @Override
            public void onWeakRefResolved(CameraScannerView ref) {
                ref.stopCamera();
                ref.prepareCameraStart();

                if (!ref.hasCameraPermission()) {
                    onError(CAMERA_PERMISSION_NEEDED);
                    mIsCameraStarted = false;
                    return;
                }
                mIsCameraStarted = true;

                ref.initCameraSource();

                if (!ref.isGooglePlayServicesAvailable()) {
                    onError(GOOGLE_PLAY_SERVICES_UNAVAILABLE);
                    return;
                }
                ref.startCameraSource();
            }
        }.execute(mView);
    }

    public void onDetectorNotOperational() {
        onError(DETECTOR_UNAVAILABLE);

        new WeakRefClassResolver<CameraScannerView>() {
            @Override
            public void onWeakRefResolved(CameraScannerView ref) {
                if (ref.hasLowStorage()) {
                    onError(LOW_STORAGE);
                } else {
                    onError(DETECTOR_NOT_OPERATIONAL);
                    ApiLoggerResolver.logExternal(SMART_CREDENTIALS_EVENT_TYPE,
                            DETECTOR_NOT_OPERATIONAL.getDesc(),
                            Build.MODEL + ", " + Build.VERSION.RELEASE + ", " + Build.ID);
                }
            }
        }.execute(mView);
    }
}

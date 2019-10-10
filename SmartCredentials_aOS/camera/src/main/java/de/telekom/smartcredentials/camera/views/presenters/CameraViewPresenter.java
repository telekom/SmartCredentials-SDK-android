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

import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

public abstract class CameraViewPresenter {

    private static final String CAMERA_STARTED_MESSAGE = "Camera has started";

    protected ScannerPluginCallback<ScannerPluginUnavailable> mPluginCallback;

    public abstract String getTAG();

    public void setPluginCallback(ScannerPluginCallback<ScannerPluginUnavailable> pluginCallback) {
        mPluginCallback = pluginCallback;
    }

    public void onError(ScannerPluginUnavailable errorReason) {
        if (mPluginCallback != null) {
            mPluginCallback.onPluginUnavailable(errorReason);
        }
        ApiLoggerResolver.logError(getTAG(), errorReason.getDesc());
    }

    public void onCameraStarted() {
        if (mPluginCallback != null) {
            mPluginCallback.onScannerStarted();
        }
        ApiLoggerResolver.logInfo(CAMERA_STARTED_MESSAGE);
    }
}

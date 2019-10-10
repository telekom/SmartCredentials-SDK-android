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

package de.telekom.smartcredentials.camera.callback;

import java.util.List;

import de.telekom.smartcredentials.core.camera.ScannerCallback;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

public class PluginCallbackCameraConverter {

    public static ScannerPluginCallback convertToDomainPluginCallback(final ScannerCallback callback, String tag) {
        return new ScannerPluginCallback<ScannerPluginUnavailable>() {
            @Override
            public void onScannerStarted() {
                ApiLoggerResolver.logCallbackMethod(tag, ScannerCallback.TAG, "onInitialized");
                if (callback == null) {
                    return;
                }
                callback.onInitialized();
            }

            @Override
            public void onScanned(List<String> result) {
                ApiLoggerResolver.logCallbackMethod(tag, ScannerCallback.TAG, "onScanned");
                if (callback == null) {
                    return;
                }
                callback.onDetected(result);
            }

            @Override
            public void onPluginUnavailable(ScannerPluginUnavailable errorMessage) {
                ApiLoggerResolver.logCallbackMethod(tag, ScannerCallback.TAG, "onPluginUnavailable");
                if (callback == null) {
                    return;
                }
                callback.onScannerUnavailable(errorMessage);
            }
        };
    }

}

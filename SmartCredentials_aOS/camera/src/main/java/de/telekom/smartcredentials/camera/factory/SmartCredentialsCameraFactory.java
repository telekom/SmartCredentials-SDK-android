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

package de.telekom.smartcredentials.camera.factory;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;

import de.telekom.smartcredentials.camera.controllers.CameraController;
import de.telekom.smartcredentials.camera.di.ObjectGraphCreatorCamera;
import de.telekom.smartcredentials.core.api.CameraApi;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;

@SuppressWarnings("unused")
public class SmartCredentialsCameraFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials Camera Module have not been initialized";

    private static CameraController sCameraController;

    private SmartCredentialsCameraFactory() {
        // required empty constructor
    }

    @NonNull
    public static synchronized CameraApi<PreviewView> initSmartCredentialsCameraModule(@NonNull final CoreApi coreApi) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.CAMERA_MODULE.getModuleName());
        }
        sCameraController = ObjectGraphCreatorCamera.getInstance().provideApiControllerCamera(coreController);
        return sCameraController;
    }

    @SuppressWarnings("unused")
    @NonNull
    public static synchronized CameraApi<PreviewView> getCameraApi() {
        if (sCameraController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sCameraController;
    }

    public static void clear() {
        ObjectGraphCreatorCamera.destroy();
        sCameraController = null;
    }
}

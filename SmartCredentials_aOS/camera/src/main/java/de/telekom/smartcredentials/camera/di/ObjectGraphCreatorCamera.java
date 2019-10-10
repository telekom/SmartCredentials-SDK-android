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

package de.telekom.smartcredentials.camera.di;

import android.support.annotation.NonNull;

import de.telekom.smartcredentials.camera.controllers.CameraController;
import de.telekom.smartcredentials.core.controllers.CoreController;

public class ObjectGraphCreatorCamera {

    private static ObjectGraphCreatorCamera sInstance;

    private ObjectGraphCreatorCamera() {
        // required empty constructor
    }

    public static ObjectGraphCreatorCamera getInstance() {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorCamera();
        }
        return sInstance;
    }

    @NonNull
    public CameraController provideApiControllerCamera(CoreController coreController) {
        return new CameraController(coreController);
    }

    public static void destroy() {
        sInstance = null;
    }
}

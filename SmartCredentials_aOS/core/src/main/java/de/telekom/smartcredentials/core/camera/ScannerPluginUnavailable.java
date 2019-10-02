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

package de.telekom.smartcredentials.core.camera;

public enum ScannerPluginUnavailable {

    SCANNER_NOT_STARTED("Scanner not started; call startScanner() first!"),
    CAMERA_SECURITY_EXCEPTION("Trying to start camera without the camera permission to be granted."),
    CAMERA_START_EXCEPTION("Could not start camera source."),
    CAMERA_PERMISSION_NEEDED("Camera permission has not been granted and it is needed to start the camera."),
    GOOGLE_PLAY_SERVICES_UNAVAILABLE("GooglePlayServices are unavailable."),
    LOW_STORAGE("Cannot set up detector. Storage free space is low."),
    DETECTOR_NOT_OPERATIONAL("Detector not yet operational."),
    DETECTOR_UNAVAILABLE("Detector is unavailable."),
    SURFACE_UNAVAILABLE("SurfaceHolder needed for camera is not available; try restarting scanner when surface becomes available.");

    private final String mDesc;

    ScannerPluginUnavailable(String desc) {
        mDesc = desc;
    }

    public String getDesc() {
        return mDesc;
    }
}

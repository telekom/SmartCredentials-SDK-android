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

/*
 * Created by Lucian Iacob on 6/29/18 2:08 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.core.documentscanner;

public enum ScannerPluginUnavailable {
    NATIVE_LIBRARY_NOT_LOADED("Native library not successful loaded"),
    CAMERA_PERMISSION_NEEDED("Camera permission has not been granted and it is needed to start the camera."),
    SCANNER_EXCEPTION("Unknown error has occurred. It may be another app that is using camera in the same time."),
    CAMERA_RESOLUTION_TOO_SMALL("The camera largest resolution is not enough for making a successful  scan"),
    INITIALIZATION_FAILURE("Internal component initialization failed."),
    FEATURE_NOT_SUPPORTED("The scanning process cannot be performed."),
    RECOGNIZER_NOT_SUPPORTED("The selected recognizer requires autofocus, which is not supported on selected camera type.");

    private final String mDesc;

    ScannerPluginUnavailable(String desc) {
        mDesc = desc;
    }

    public String getDesc() {
        return mDesc;
    }
}

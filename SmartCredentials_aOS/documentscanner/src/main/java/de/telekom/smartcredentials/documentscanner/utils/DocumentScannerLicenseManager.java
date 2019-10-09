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

package de.telekom.smartcredentials.documentscanner.utils;

import android.content.Context;

import com.microblink.MicroblinkSDK;
import com.microblink.recognition.InvalidLicenceKeyException;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

/**
 * Created by Lucian Iacob on November 12, 2018.
 */
public class DocumentScannerLicenseManager {

    private static final String BLINK_ID_LICENSE_KEY = "sRwAAAAQc21hcnRjcmVkZW50aWFsc6BaF390hlFc8SCyfdSr1zy6h+VBnUVQ6hEHez+BD5NuAsEc86kTK6NzvM9XPQcTYNGgXFwsit2wcp3ZMSZXNS3soQwo5fE1kyyOfrM2d12BhReCeqRGLrYc9IRoQDH4JhFDeXWQ5mYju0PNHwNn6dBWQ8UoG6LWWboK4EsCfEXk9FvsMNDGnx/DAazywTF6z+dddyotd2XB1VVR8jnWIBphnZf4enIJfVgcYv2Cgsab3vQUAA1nYUwI";
    private static final String BLINK_ID_LICENSE_NAME = "smartcredentials";

    public static void setLicense(Context context) {
        MicroblinkSDK.setShowTimeLimitedLicenseWarning(false);
        try {
            MicroblinkSDK.setLicenseKey(BLINK_ID_LICENSE_KEY, BLINK_ID_LICENSE_NAME, context);
        } catch (InvalidLicenceKeyException exception) {
            ApiLoggerResolver.logError("LicenseManager", exception.getMessage());
        }
    }

}

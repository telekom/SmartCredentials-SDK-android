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

package de.telekom.smartcredentials.core.rootdetector;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

class AssetsReader {

    private static final String CLASS_NAME = "AssetsReader";
    private static final String RESTRICTED_DEVICES_FILE_NAME = "root_detection_constants.json";

    static JSONObject readRootDetectionConstantsJSON(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(RESTRICTED_DEVICES_FILE_NAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            return new JSONObject(json);
        } catch (IOException e) {
            ApiLoggerResolver.logError(CLASS_NAME, "error opening root detection constants assets file");
        } catch (JSONException e) {
            ApiLoggerResolver.logError(CLASS_NAME, "error parsing root detection constants JSON");
        }
        return null;
    }

}

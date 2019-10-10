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

package de.telekom.smartcredentials.core.blacklisting;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

/**
 * Created by Lucian Iacob on February 01, 2019.
 */
public class FilesManager {

    public static JSONObject readDeviceBlackListJSON(Context context, String blacklistFileName) {
        try {
            FileInputStream inputStream = context.openFileInput(blacklistFileName);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return new JSONObject(stringBuilder.toString());
        } catch (IOException | JSONException e) {
            ApiLoggerResolver.logError(context.getClass().getSimpleName(), "Error reading device black list");
        }
        return null;
    }

    public static void writeDeviceBlacklistJSON(Context context, String blacklistFileName, JSONObject jsonObject) throws IOException {
        if (jsonObject == null) {
            return;
        }

        String stringBlacklist = jsonObject.toString();
        FileOutputStream outputStream = context.openFileOutput(blacklistFileName, Context.MODE_PRIVATE);
        outputStream.write(stringBlacklist.getBytes());
        outputStream.close();
    }
}

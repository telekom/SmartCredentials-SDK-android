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
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.utils.SystemPropertyMapper;

/**
 * Created by Lucian Iacob on February 01, 2019.
 */
@SuppressWarnings("unused")
public class SmartCredentialsSystemPropertyMap {

    private static final String TAG = "SmartCredentialsSystemPropertyMap";

    public static void restrictCurrentDeviceOnFeature(Context context, SmartCredentialsFeatureSet feature) {
        JSONObject blackListJSON = FilesManager.readDeviceBlackListJSON(context, SystemPropertyMapper.BLACKLIST_FILE_NAME);

        if (blackListJSON == null) {
            return;
        }

        try {
            if (!blackListJSON.has(feature.name())) {
                JSONArray array = new JSONArray();
                array.put(Build.MODEL);
                blackListJSON.put(feature.name(), array);
            } else {
                JSONArray jsonArray = blackListJSON.getJSONArray(feature.name());
                for (int index = 0; index < jsonArray.length(); index++) {
                    String device = jsonArray.getString(index);
                    if (device.equalsIgnoreCase(Build.MODEL)) {
                        return;
                    }
                }
                blackListJSON.getJSONArray(feature.name()).put(Build.MODEL);
            }
            FilesManager.writeDeviceBlacklistJSON(context, SystemPropertyMapper.BLACKLIST_FILE_NAME, blackListJSON);
        } catch (IOException | JSONException e) {
            ApiLoggerResolver.logError(TAG, e.getMessage());
        }
    }

    public static void unrestrictCurrentDeviceOnFeature(Context context, SmartCredentialsFeatureSet feature) {
        JSONObject blackListJSON = FilesManager.readDeviceBlackListJSON(context, SystemPropertyMapper.BLACKLIST_FILE_NAME);

        if (blackListJSON == null || !blackListJSON.has(feature.name())) {
            return;
        }

        try {
            JSONArray jsonArray = blackListJSON.getJSONArray(feature.name());
            for (int index = 0; index < jsonArray.length(); index++) {
                String device = jsonArray.getString(index);
                if (device.equalsIgnoreCase(Build.MODEL)) {
                    blackListJSON.getJSONArray(feature.name()).remove(index);
                    if (blackListJSON.getJSONArray(feature.name()).length() == 0) {
                        blackListJSON.remove(feature.name());
                    }
                }
            }
            FilesManager.writeDeviceBlacklistJSON(context, SystemPropertyMapper.BLACKLIST_FILE_NAME, blackListJSON);
        } catch (JSONException | IOException e) {
            ApiLoggerResolver.logError(TAG, e.getMessage());
        }
    }

    public static boolean isFeatureBlockedOnCurrentDevice(Context context, SmartCredentialsFeatureSet feature) {
        JSONObject blackListJSON = FilesManager.readDeviceBlackListJSON(context, SystemPropertyMapper.BLACKLIST_FILE_NAME);

        if (blackListJSON == null) {
            return false;
        }

        if (!blackListJSON.has(feature.name())) {
            return false;
        }

        try {
            JSONArray jsonArray = blackListJSON.getJSONArray(feature.name());
            for (int index = 0; index < jsonArray.length(); index++) {
                String device = jsonArray.getString(index);
                if (device.equalsIgnoreCase(Build.MODEL)) {
                    return true;
                }
            }
        } catch (JSONException e) {
            ApiLoggerResolver.logError(TAG, e.getMessage());
        }

        return false;
    }

}

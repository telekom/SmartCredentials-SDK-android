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

package de.telekom.smartcredentials.core.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import de.telekom.smartcredentials.core.blacklisting.FilesManager;

/**
 * Created by Lucian Iacob on February 01, 2019.
 */
public class SystemPropertyMapper {

    public static final String BLACKLIST_FILE_NAME = "smart_credentials_property_map.json";

    public static void initBlacklisting(Context context) {
        JSONObject defaultBlackListJSON = AssetsReader.readDeviceBlacklistJSON(context);

        try {
            if (blacklistExistsInInternalStorage(context)) {
                syncBlacklists(context, defaultBlackListJSON);
            } else {
                FilesManager.writeDeviceBlacklistJSON(context, BLACKLIST_FILE_NAME, defaultBlackListJSON);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void syncBlacklists(Context context, JSONObject defaultBlackListJson) throws JSONException, IOException {
        JSONObject storageBlackList = FilesManager.readDeviceBlackListJSON(context, BLACKLIST_FILE_NAME);

        if (defaultBlackListJson == null || storageBlackList == null) {
            return;
        }

        Iterator<String> defaultBlackListIterator = defaultBlackListJson.keys();
        while (defaultBlackListIterator.hasNext()) {
            String key = defaultBlackListIterator.next();
            if (!storageBlackList.has(key)) {
                storageBlackList.put(key, defaultBlackListJson.get(key));
            } else {
                JSONArray finalArray = solveExistingKey(defaultBlackListJson.getJSONArray(key),
                        storageBlackList.getJSONArray(key));
                storageBlackList.put(key, finalArray);
            }
        }

        FilesManager.writeDeviceBlacklistJSON(context, BLACKLIST_FILE_NAME, storageBlackList);
    }

    private static JSONArray solveExistingKey(JSONArray assetsDeviceArray, JSONArray storageDeviceArray) throws JSONException {
        for (int assetsIndex = 0; assetsIndex < assetsDeviceArray.length(); assetsIndex++) {
            String device = assetsDeviceArray.getString(assetsIndex);
            boolean found = false;
            for (int storageIndex = 0; storageIndex < storageDeviceArray.length(); storageIndex++) {
                String storageDevice = storageDeviceArray.getString(storageIndex);
                if (device.equalsIgnoreCase(storageDevice)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                storageDeviceArray.put(device);
            }
        }
        return storageDeviceArray;
    }

    private static boolean blacklistExistsInInternalStorage(Context context) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + BLACKLIST_FILE_NAME;
        File file = new File(path);
        return file.exists();
    }

}

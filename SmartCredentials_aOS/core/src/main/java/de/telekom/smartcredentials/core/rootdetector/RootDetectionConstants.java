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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

public class RootDetectionConstants {

    private static final String TAG = "RootDetectionConstants";

    public static Map<String, String> getJSONConstantsMap(Context context, RootDetectionConstantsSet constantName) {
        JSONObject rootConstantsJSON = AssetsReader.readRootDetectionConstantsJSON(context);

        Map<String, String> pathsMap = new HashMap<>();

        if (rootConstantsJSON.has(constantName.name())) {
            try {
                JSONArray jsonArray = rootConstantsJSON.getJSONArray(constantName.name());

                for (int index = 0; index < jsonArray.length(); index++) {
                    JSONObject jsonMapObject = jsonArray.getJSONObject(index);
                    pathsMap.put(jsonMapObject.getString("package"), jsonMapObject.getString("index"));
                }
            } catch (JSONException e) {
                ApiLoggerResolver.logError(TAG, e.getMessage());
            }
        }

        return pathsMap;
    }

    public static ArrayList<String> getJSONConstantsList(Context context, RootDetectionConstantsSet constantName) {
        JSONObject rootConstantsJSON = AssetsReader.readRootDetectionConstantsJSON(context);

        ArrayList<String> pathsArrayList = new ArrayList<>();

        if (rootConstantsJSON.has(constantName.name())) {
            try {
                JSONArray jsonArray = rootConstantsJSON.getJSONArray(constantName.name());

                for (int index = 0; index < jsonArray.length(); index++) {
                    pathsArrayList.add(jsonArray.getString(index));
                }
            } catch (JSONException e) {
                ApiLoggerResolver.logError(TAG, e.getMessage());
            }
        }

        return pathsArrayList;
    }

    public static String[] convertArrayListToStringArray(ArrayList<String> pathsArrayList) {
        String[] stringArray = new String[pathsArrayList.size()];
        for (int i = 0; i < pathsArrayList.size(); i++) {
            stringArray[i] = pathsArrayList.get(i);
        }
        return stringArray;
    }
}

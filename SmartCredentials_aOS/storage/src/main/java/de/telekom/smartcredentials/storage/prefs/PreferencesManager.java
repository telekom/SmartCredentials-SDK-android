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

package de.telekom.smartcredentials.storage.prefs;

import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PreferencesManager {

    private final SharedPreferences mSharedPreferences;

    public PreferencesManager(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    void remove(String uniqueKey) {
        if (uniqueKey != null) {
            mSharedPreferences
                    .edit()
                    .remove(uniqueKey)
                    .apply();
        }
    }

    void save(String uniqueKey, String data) {
        if (uniqueKey != null && data != null) {
            mSharedPreferences
                    .edit()
                    .putString(uniqueKey, data)
                    .apply();
        }
    }

    void update(String uniqueKey, String data) {
        if (uniqueKey != null) {
            mSharedPreferences
                    .edit()
                    .putString(uniqueKey, data)
                    .apply();
        }
    }

    List<String> getItemsMatchingType(String keyToMatch) {
        List<String> values = new ArrayList<>();

        Map<String, ?> allPrefs = mSharedPreferences.getAll();
        if (allPrefs.isEmpty()) {
            return values;
        }

        for (String key : allPrefs.keySet()) {
            if (!TextUtils.isEmpty(keyToMatch) && key.startsWith(keyToMatch)) {
                values.add(mSharedPreferences.getString(key, ""));
            }
        }

        return values;
    }

    String getItem(String uniqueKey) {
        if (uniqueKey != null && mSharedPreferences.contains(uniqueKey)) {
            return mSharedPreferences.getString(uniqueKey, "");
        }
        return null;
    }

    int getItemsCount() {
        Map all = mSharedPreferences.getAll();
        return all != null ? all.size() : 0;
    }

    void clearAll() {
        mSharedPreferences
                .edit()
                .clear()
                .apply();
    }
}

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

package de.telekom.smartcredentials.authentication.parser;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import org.json.JSONObject;

import de.telekom.smartcredentials.authentication.exception.InvalidConfigurationException;

/**
 * Created by Lucian Iacob on February 28, 2019.
 */
public class JsonParser {

    private final JSONObject mJsonObject;

    private JsonParser(JSONObject jsonObject) {
        mJsonObject = jsonObject;
    }

    public static JsonParser forJson(@NonNull JSONObject jsonObject) {
        return new JsonParser(jsonObject);
    }

    @Nullable
    public String getString(String key) {
        String value = mJsonObject.optString(key);
        value = value.trim();

        if (TextUtils.isEmpty(value)) {
            return null;
        }

        return value;
    }

    public String getRequiredString(String key) throws InvalidConfigurationException {
        String value = getString(key);

        if (TextUtils.isEmpty(value)) {
            throw new InvalidConfigurationException(key + " is required but not specified in the configuration");
        }

        return value;
    }

    public Uri getRequiredUri(String key) throws InvalidConfigurationException {
        String uriString = getRequiredString(key);
        Uri uri = Uri.parse(uriString);

        if (!uri.isHierarchical() && !uri.isAbsolute()) {
            throw new InvalidConfigurationException(key + " must be hierarchical and absolute");
        }

        if (!TextUtils.isEmpty(uri.getEncodedUserInfo())) {
            throw new InvalidConfigurationException(key + " must not have user info");
        }

        if (!TextUtils.isEmpty(uri.getEncodedQuery())) {
            throw new InvalidConfigurationException(key + " must not have query params");
        }

        if (!TextUtils.isEmpty(uri.getEncodedFragment())) {
            throw new InvalidConfigurationException(key + " must not have a fragment");
        }

        return uri;
    }

    public Uri getRequiredWebUri(String key) throws InvalidConfigurationException {
        Uri uri = getRequiredUri(key);
        String scheme = uri.getScheme();
        if (TextUtils.isEmpty(scheme) || !(scheme.equals("http") || scheme.equals("https"))) {
            throw new InvalidConfigurationException(key + " must have an http or https scheme");
        }

        return uri;
    }
}

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

package de.telekom.smartcredentials.core.plugins.fingerprint;

public class BiometricView {

    private final String mTitle;
    private final String mSubtitle;
    private final String mDescription;
    private final String mNegativeButtonDescription;
    private final BiometricViewClickListener mListener;

    public BiometricView(String title, String subtitle, String description,
                         String negativeButtonDescription, BiometricViewClickListener listener) {
        mTitle = title;
        mSubtitle = subtitle;
        mDescription = description;
        mNegativeButtonDescription = negativeButtonDescription;
        mListener = listener;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getNegativeButtonDescription() {
        return mNegativeButtonDescription;
    }

    public BiometricViewClickListener getBiometricViewClickListener() {
        return mListener;
    }
}

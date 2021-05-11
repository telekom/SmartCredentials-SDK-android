/*
 * Copyright (c) 2021 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.core.authorization;

import androidx.annotation.NonNull;

import java.util.Objects;

public class AuthorizationView {

    private final String mTitle;
    private final String mSubtitle;
    private final String mDescription;
    private final String mNegativeButtonText;

    public AuthorizationView(Builder builder) {
        this.mTitle = builder.title;
        this.mSubtitle = builder.subtitle;
        this.mDescription = builder.description;
        this.mNegativeButtonText = builder.negativeButtonText;
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

    public String getNegativeButtonText() {
        return mNegativeButtonText;
    }

    public static class Builder {

        public static final String NULL_TITLE = "AuthorizationView title must not be null.";
        public static final String NULL_NEGATIVE_BUTTON_TEXT = "AuthorizationView negative button text must not be null.";
        public static final String NULL_SUBTITLE = "AuthorizationView subtitle must not be null";
        public static final String NULL_DESCRIPTION = "AuthorizationView description must not be null";

        private final String title;
        private final String negativeButtonText;
        private String subtitle;
        private String description;

        public Builder(String title, String negativeButtonText) {
            Objects.requireNonNull(title, NULL_TITLE);
            this.title = title;
            Objects.requireNonNull(negativeButtonText, NULL_NEGATIVE_BUTTON_TEXT);
            this.negativeButtonText = negativeButtonText;
        }

        @SuppressWarnings("unused")
        public Builder setSubtitle(@NonNull String subtitle) {
            Objects.requireNonNull(subtitle, NULL_SUBTITLE);
            this.subtitle = subtitle;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder setDescription(@NonNull String description) {
            Objects.requireNonNull(description, NULL_DESCRIPTION);
            this.description = description;
            return this;
        }

        public AuthorizationView build() {
            return new AuthorizationView(this);
        }
    }
}
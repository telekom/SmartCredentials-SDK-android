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

import java.util.Objects;

public class AuthorizationConfiguration {

    private final AuthorizationView mAuthorizationView;
    private final boolean mAllowDeviceCredentialsFallback;
    private final boolean mRequireFaceRecognitionConfirmation;

    public AuthorizationConfiguration(Builder builder) {
        this.mAuthorizationView = builder.authorizationView;
        this.mAllowDeviceCredentialsFallback = builder.allowDeviceCredentialsFallback;
        this.mRequireFaceRecognitionConfirmation = builder.requireFaceRecognitionConfirmation;
    }

    public boolean shouldAllowDeviceCredentialsFallback() {
        return mAllowDeviceCredentialsFallback;
    }

    public boolean isFaceRecognitionConfirmationRequired() {
        return mRequireFaceRecognitionConfirmation;
    }

    public AuthorizationView getAuthorizationView() {
        return mAuthorizationView;
    }

    public static class Builder {

        public static final String NULL_AUTHORIZATION_VIEW = "Authorization View must not be null.";

        private final AuthorizationView authorizationView;
        private boolean allowDeviceCredentialsFallback;
        private boolean requireFaceRecognitionConfirmation;

        public Builder(AuthorizationView authorizationView) {
            Objects.requireNonNull(authorizationView, NULL_AUTHORIZATION_VIEW);
            this.authorizationView = authorizationView;
        }

        @SuppressWarnings("unused")
        public Builder allowDeviceCredentialsFallback(boolean allowDeviceCredentialsFallback) {
            this.allowDeviceCredentialsFallback = allowDeviceCredentialsFallback;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder requireFaceRecognitionConfirmation(boolean requireFaceRecognitionConfirmation) {
            this.requireFaceRecognitionConfirmation = requireFaceRecognitionConfirmation;
            return this;
        }

        public AuthorizationConfiguration build() {
            return new AuthorizationConfiguration(this);
        }
    }

}
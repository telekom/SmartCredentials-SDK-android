/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.core.authentication.configuration;

import androidx.annotation.NonNull;

/**
 * Created by gabriel.blaj@endava.com at 10/5/2020
 */
public class PkceConfiguration {

    private String mCodeVerifier;
    private String mCodeChallenge;
    private String mCodeChallengeMethod;

    /**
     * The library will handle the PKCE flow by generating the needed parameters.
     */
    public PkceConfiguration() {
    }

    /**
     * The PKCE flow will use the specified {@param codeVerifier}, and will generate a {@param codeChallenge}
     * based on it using the default SHA-256 method.
     */
    public PkceConfiguration(@NonNull String codeVerifier) {
        this.mCodeVerifier = codeVerifier;
    }

    /**
     * The PKCE flow will use the specified parameters.
     */
    @SuppressWarnings("unused")
    public PkceConfiguration(@NonNull String codeVerifier, @NonNull String codeChallenge, @NonNull String codeChallengeMethod) {
        this.mCodeVerifier = codeVerifier;
        this.mCodeChallenge = codeChallenge;
        this.mCodeChallengeMethod = codeChallengeMethod;
    }

    public String getCodeVerifier() {
        return mCodeVerifier;
    }

    public String getCodeChallenge() {
        return mCodeChallenge;
    }

    public String getCodeChallengeMethod() {
        return mCodeChallengeMethod;
    }
}

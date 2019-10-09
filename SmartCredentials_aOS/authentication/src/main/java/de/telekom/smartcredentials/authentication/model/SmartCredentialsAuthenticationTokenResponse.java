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

package de.telekom.smartcredentials.authentication.model;

import java.util.Map;
import java.util.Set;

import de.telekom.smartcredentials.core.authentication.AuthenticationTokenResponse;

public class SmartCredentialsAuthenticationTokenResponse extends AuthenticationTokenResponse {

    private final String mAccessToken;
    private final Long mAccessTokenExpirationTime;
    private final Map<String, String> mAdditionalParameters;
    private final Set<String> mScopeSet;
    private final String mIdToken;
    private final String mRefreshToken;
    private final String mScope;
    private final String mTokenType;

    public SmartCredentialsAuthenticationTokenResponse(String accessToken,
                                                       Long accessTokenExpirationTime,
                                                       Map<String, String> additionalParameters,
                                                       Set<String> scopeSet,
                                                       String idToken,
                                                       String refreshToken,
                                                       String scope,
                                                       String tokenType) {
        mAccessToken = accessToken;
        mAccessTokenExpirationTime = accessTokenExpirationTime;
        mAdditionalParameters = additionalParameters;
        mScopeSet = scopeSet;
        mIdToken = idToken;
        mRefreshToken = refreshToken;
        mScope = scope;
        mTokenType = tokenType;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public Long getAccessTokenExpirationTime() {
        return mAccessTokenExpirationTime;
    }

    public Map<String, String> getAdditionalParameters() {
        return mAdditionalParameters;
    }

    public Set<String> getScopeSet() {
        return mScopeSet;
    }

    public String getIdToken() {
        return mIdToken;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public String getScope() {
        return mScope;
    }

    public String getTokenType() {
        return mTokenType;
    }
}

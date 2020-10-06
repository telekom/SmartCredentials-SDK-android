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

import android.content.Context;

import de.telekom.smartcredentials.core.authentication.AuthenticationServiceInitListener;

/**
 * Created by gabriel.blaj@endava.com at 10/6/2020
 */
public class AuthenticationConfiguration {

    private Context mContext;
    private String mIdentityProviderId;
    private int mAuthConfigFileResId;
    private PKCEConfiguration mPkceConfiguration;
    private int mCustomTabBarColor;
    private AuthenticationServiceInitListener mAuthenticationServiceInitListener;

    private AuthenticationConfiguration(ConfigurationBuilder builder) {
        mContext = builder.context;
        mIdentityProviderId = builder.identityProviderId;
        mAuthConfigFileResId = builder.authConfigFileResId;
        mPkceConfiguration = builder.pkceConfiguration;
        mCustomTabBarColor = builder.customTabBarColor;
        mAuthenticationServiceInitListener = builder.authenticationServiceInitListener;
    }

    public Context getContext() {
        return mContext;
    }

    public String getIdentityProviderId() {
        return mIdentityProviderId;
    }

    public int getAuthConfigFileResId() {
        return mAuthConfigFileResId;
    }

    public PKCEConfiguration getPkceConfiguration() {
        return mPkceConfiguration;
    }

    public int getCustomTabBarColor() {
        return mCustomTabBarColor;
    }

    public AuthenticationServiceInitListener getAuthenticationServiceInitListener() {
        return mAuthenticationServiceInitListener;
    }

    public static class ConfigurationBuilder {
        private Context context;
        private String identityProviderId;
        private int authConfigFileResId;
        private PKCEConfiguration pkceConfiguration = null;
        private int customTabBarColor;
        private AuthenticationServiceInitListener authenticationServiceInitListener;

        /**
         * Creates a builder for a {@link AuthenticationConfiguration} which sets the mandatory
         * parameters for the authentication flow.
         *
         * @param context                           The application context.
         * @param identityProviderId                The identity provider name.
         * @param authConfigFileResId               The identity provider configuration file id from the resources
         *                                          raw folder.
         * @param customTabBarColor                 The color of the custom tab's action bar.
         * @param authenticationServiceInitListener An {@link AuthenticationServiceInitListener} that will be called
         *                                          once the initialization is complete or fail.
         */
        public ConfigurationBuilder(Context context, String identityProviderId, int authConfigFileResId,
                                    int customTabBarColor, AuthenticationServiceInitListener authenticationServiceInitListener) {
            this.context = context;
            this.identityProviderId = identityProviderId;
            this.authConfigFileResId = authConfigFileResId;
            this.customTabBarColor = customTabBarColor;
            this.authenticationServiceInitListener = authenticationServiceInitListener;
        }

        /**
         * Sets whether the Authentication flow uses the PKCE(Proof Key for Code Exchange) extension
         * or not. If the {@link PKCEConfiguration} is provided, then the pixie key is used in order
         * to prevent several attacks and perform code authorization flow securely.
         * <p>
         *
         * @param pkceConfiguration represents a set of elements that are used in the Auhorization Code flow.
         *
         * @return this {@link AuthenticationConfiguration.ConfigurationBuilder} object to allow for
         * chaining of calls to set methods
         */
        @SuppressWarnings("unused")
        public ConfigurationBuilder setPKCEConfiguration(PKCEConfiguration pkceConfiguration) {
            this.pkceConfiguration = pkceConfiguration;
            return this;
        }

        /**
         * Creates a {@link AuthenticationConfiguration} with the arguments supplied to this builder.
         *
         * @return {@link AuthenticationConfiguration} configuration object
         */
        public AuthenticationConfiguration build() {
            return new AuthenticationConfiguration(this);
        }
    }
}

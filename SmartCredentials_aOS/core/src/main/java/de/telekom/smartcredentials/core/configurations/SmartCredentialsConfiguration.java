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

package de.telekom.smartcredentials.core.configurations;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Objects;
import java.util.Set;

import de.telekom.smartcredentials.core.logger.ApiLogger;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;

/**
 * Created by Lucian Iacob on November 07, 2018.
 */
public class SmartCredentialsConfiguration {

    private final Context mContext;
    private final String mAppAlias;
    private final String mUserId;
    private final ApiLogger mLogger;
    private final Set<RootDetectionOption> mRootDetectionOptions;

    private SmartCredentialsConfiguration(Builder builder) {
        mContext = builder.context;
        mUserId = builder.userId;
        mAppAlias = builder.appAlias;
        mLogger = builder.logger;
        mRootDetectionOptions = builder.mRootDetectionOptions;
    }

    public static class Builder {

        public static final String NULL_CONTEXT = "Context must not be null.";
        public static final String NULL_ALIAS = "Alias must not be null.";
        public static final String NULL_USER_ID = "User ID must not be null.";

        private final Context context;
        private final String userId;
        private String appAlias = "";
        private ApiLogger logger;
        private Set<RootDetectionOption> mRootDetectionOptions = RootDetectionOption.ALL;

        /**
         * Creates a builder for a {@link SmartCredentialsConfiguration} that uses the default settings.
         * Also it sets user id used to identify data inside the library's database.
         * If you have multiple users, in order to separate stored data for each of them,
         * set an unique id for each of the users.
         *
         * @param applicationContext application's {@link Context}
         * @param currentUserId      {@link String} user ID that will be used to perform CRUD operations on database.
         * @throws NullPointerException if context is null
         */
        public Builder(@NonNull Context applicationContext, @NonNull String currentUserId) {
            Objects.requireNonNull(applicationContext, NULL_CONTEXT);
            Objects.requireNonNull(currentUserId, NULL_USER_ID);
            this.context = applicationContext;
            this.userId = currentUserId;
        }

        /**
         * Sets an alias at app level used to build encryption and decryption keys.
         * This alias aims to be an extra layer of security. It is recommended to set this property.
         * <p>
         * Default is empty string.
         *
         * @param appAlias {@link String} alias that will be used to create encryption keys
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setAppAlias(@NonNull String appAlias) {
            Objects.requireNonNull(appAlias, NULL_ALIAS);
            this.appAlias = appAlias;
            return this;
        }

        /**
         * Sets a logger implementation used to retrieve logs triggered inside library.
         *
         * @param logger implementation of {@link ApiLogger} through which logs will be forwarded
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setLogger(ApiLogger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * Sets whether root checker should block library operations for rooted devices or not. If
         * a set of {@link RootDetectionOption} is provided, then the root check operations is
         * performed checking only the specified options. If you do not want the library to check
         * for rooted device, set the options as <code>null</code>.
         * <p>
         * Default behavior is checking for rooted devices using all the options available.
         *
         * @param rootDetectionOptions represents a set of {@link RootDetectionOption} indicating
         *                             what options should be used for determining if a device
         *                             is rooted.
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setRootCheckerEnabled(Set<RootDetectionOption> rootDetectionOptions) {
            this.mRootDetectionOptions = rootDetectionOptions;
            return this;
        }

        /**
         * Creates a {@link SmartCredentialsConfiguration} with the arguments supplied to this builder.
         *
         * @return {@link SmartCredentialsConfiguration} configuration object
         */
        public SmartCredentialsConfiguration build() {
            return new SmartCredentialsConfiguration(this);
        }
    }

    /**
     * Returns the library configurations context.
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * Returns the user id.
     */
    public String getUserId() {
        return mUserId;
    }

    /**
     * Returns the alias that is used to build encryption and decryption keys.
     */
    public String getAppAlias() {
        return mAppAlias;
    }

    /**
     * Returns the logger implementation used to retrieve logs triggered inside library.
     */
    public ApiLogger getLogger() {
        return mLogger;
    }

    /**
     * Checks if the root checker should block library operations for rooted devices or not.
     */
    public boolean isRootCheckerEnabled() {
        return mRootDetectionOptions != null && !mRootDetectionOptions.isEmpty();
    }

    public Set<RootDetectionOption> getRootDetectionOptions() {
        return mRootDetectionOptions;
    }
}

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

package de.telekom.smartcredentials.authorization.handlers;

import androidx.fragment.app.FragmentActivity;

import de.telekom.smartcredentials.authorization.security.CipherManager;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.authorization.AuthorizationConfiguration;

public abstract class AuthorizationHandler {

    protected FragmentActivity mActivity;
    protected AuthorizationConfiguration mConfiguration;
    protected AuthorizationCallback mCallback;
    protected CipherManager mCipherManager;

    public AuthorizationHandler(FragmentActivity activity,
                                AuthorizationConfiguration configuration,
                                AuthorizationCallback callback,
                                CipherManager cipherManager) {
        this.mActivity = activity;
        this.mConfiguration = configuration;
        this.mCallback = callback;
        this.mCipherManager = cipherManager;
    }

    public abstract void prepareAuthorization();
}

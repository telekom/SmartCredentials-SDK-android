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

package de.telekom.smartcredentials.authorization.keyguard;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;

import de.telekom.smartcredentials.authorization.R;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

import static android.content.Context.KEYGUARD_SERVICE;

public class KeyguardManagerWrapper {

    private static final String IS_KEYGUARD_SECURED_MESSAGE = "Is device keyguard secured: ";

    private final Context mContext;
    private final KeyguardManager mKeyguardManager;

    public KeyguardManagerWrapper(Context context) {
        mContext = context;
        mKeyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
    }

    public Intent getDeviceCredentialsIntent() {
        return mKeyguardManager.createConfirmDeviceCredentialIntent(
                mContext.getResources().getString(R.string.sc_credentials_title),
                mContext.getResources().getString(R.string.sc_credentials_description));
    }

    public boolean checkIsKeyguardSecured() {
        boolean isSecured = mKeyguardManager.isKeyguardSecure();
        ApiLoggerResolver.logInfo(IS_KEYGUARD_SECURED_MESSAGE + isSecured);

        return isSecured;
    }
}

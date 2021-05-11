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

package de.telekom.smartcredentials.authorization.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.telekom.smartcredentials.authorization.security.KeyguardManagerWrapper;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.authorization.AuthorizationError;
import de.telekom.smartcredentials.core.authorization.AuthorizationView;

public class DeviceCredentialsFragment extends Fragment {

    public final static String TAG = "DeviceCredentialsFragment";
    public final static int AUTHORIZATION_REQUEST_CODE = 643;

    private AuthorizationView mAuthorizationView;
    private AuthorizationCallback mCallback;
    private KeyguardManagerWrapper mKeyguardManagerWrapper;

    public static DeviceCredentialsFragment getInstance(AuthorizationCallback callback,
                                                        AuthorizationView authorizationView,
                                                        KeyguardManagerWrapper keyguardManagerWrapper) {
        DeviceCredentialsFragment fragment = new DeviceCredentialsFragment();
        fragment.mAuthorizationView = authorizationView;
        fragment.mCallback = callback;
        fragment.mKeyguardManagerWrapper = keyguardManagerWrapper;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authorize();
    }

    public void authorize() {
        if (mKeyguardManagerWrapper.isDeviceSecured()) {
            startActivityForResult(mKeyguardManagerWrapper.getDeviceCredentialsIntent(mAuthorizationView), AUTHORIZATION_REQUEST_CODE);
        } else {
            mCallback.onAuthorizationError(AuthorizationError.DEVICE_NOT_SECURED.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (mCallback != null) {
                mCallback.onAuthorizationSucceeded();
            }
        } else {
            if (mCallback != null) {
                mCallback.onAuthorizationFailed(AuthorizationError.AUTHORIZATION_FAILED.getMessage());
            }
        }
    }
}

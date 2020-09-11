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

package de.telekom.smartcredentials.authorization.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.telekom.smartcredentials.authorization.fragments.presenters.AlternativeAuthView;
import de.telekom.smartcredentials.authorization.fragments.presenters.DeviceCredentialsPresenter;
import de.telekom.smartcredentials.authorization.keyguard.KeyguardManagerWrapper;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;

import static android.app.Activity.RESULT_OK;

public class DeviceCredentialsFragment extends Fragment implements AlternativeAuthView {

    DeviceCredentialsPresenter mPresenter;
    private transient AuthorizationPluginCallback mPluginCallback;

    static DeviceCredentialsFragment getInstance(@NonNull AuthorizationPluginCallback callback) {
        DeviceCredentialsFragment fragment = new DeviceCredentialsFragment();
        Bundle args = new Bundle();
        args.putParcelable(AUTH_CALLBACK_ARGS, callback);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPluginCallback = getArguments() != null
                ? (AuthorizationPluginCallback) getArguments().getParcelable(AUTH_CALLBACK_ARGS)
                : null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        KeyguardManagerWrapper keyguardManagerWrapper = new KeyguardManagerWrapper(getContext());
        mPresenter = new DeviceCredentialsPresenter(keyguardManagerWrapper);
        informPresenterViewIsReady();
        return getView();
    }

    private void informPresenterViewIsReady() {
        mPresenter.viewReady(this, mPluginCallback);
    }

    @Override
    public void showViewForAuthorization(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onAlternativeAuthResult(requestCode, resultCode == RESULT_OK);
    }
}

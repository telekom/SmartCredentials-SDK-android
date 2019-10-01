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
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.telekom.smartcredentials.authorization.R;
import de.telekom.smartcredentials.authorization.fingerprint.FingerprintLayout;
import de.telekom.smartcredentials.authorization.fragments.presenters.FingerprintPresenter;
import de.telekom.smartcredentials.authorization.fragments.presenters.FingerprintView;
import de.telekom.smartcredentials.authorization.keyguard.KeyguardManagerWrapper;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;

import static android.app.Activity.RESULT_OK;

@RequiresApi(Build.VERSION_CODES.M)
public class FingerprintDialogFragment extends DialogFragment implements FingerprintView {

    FingerprintPresenter mPresenter;
    private FingerprintLayout mFingerprintLayout;
    private AuthorizationPluginCallback mPluginCallback;

    static FingerprintDialogFragment getInstance(@NonNull AuthorizationPluginCallback callback) {
        FingerprintDialogFragment fragment = new FingerprintDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(AUTH_CALLBACK_ARGS, callback);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(android.app.DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);

        mPluginCallback = getArguments() != null
                ? (AuthorizationPluginCallback) getArguments().getParcelable(AUTH_CALLBACK_ARGS)
                : null;

        KeyguardManagerWrapper keyguardManagerWrapper = new KeyguardManagerWrapper(getContext());
        mPresenter = new FingerprintPresenter(keyguardManagerWrapper);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(false);
            getDialog().setTitle(getString(R.string.sc_plugin_string_fingerprint_sign_in));
        }

        View view = View.inflate(getActivity(), R.layout.sc_dialog_fingerprint, null);
        mFingerprintLayout = view.findViewById(R.id.sc_layout_fingerprint);

        informPresenterViewIsReady();
        return view;
    }

    private void informPresenterViewIsReady() {
        mPresenter.viewReady(this, mPluginCallback);
    }

    @Override
    public void onResume() {
        super.onResume();

        mFingerprintLayout.setClickListeners(mPresenter);
        mPresenter.viewResumed();
    }

    @Override
    public boolean isFeatureAvailable() {
        return mFingerprintLayout.isFingerprintAuthorizationAvailable();
    }

    @Override
    public void showFingerprintView() {
        mFingerprintLayout.show();
    }

    @Override
    public void listenToFingerprintAuthorization() {
        mFingerprintLayout.listenToFingerprintAuthorization(mPluginCallback);
    }

    @Override
    public void remove() {
        dismiss();
    }

    @Override
    public boolean isAuthorized() {
        return mFingerprintLayout.isAuthorized();
    }

    @Override
    public void showViewForAuthorization(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }
    //</editor-fold>

    @Override
    public void onPause() {
        super.onPause();
        mFingerprintLayout.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onAlternativeAuthResult(requestCode, resultCode == RESULT_OK);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.viewDestroyed();
        mFingerprintLayout = null;
    }
}

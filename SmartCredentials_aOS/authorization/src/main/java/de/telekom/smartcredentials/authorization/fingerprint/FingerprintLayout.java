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

package de.telekom.smartcredentials.authorization.fingerprint;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import de.telekom.smartcredentials.authorization.R;
import de.telekom.smartcredentials.authorization.fingerprint.presenters.FingerprintPresenter;
import de.telekom.smartcredentials.authorization.fingerprint.presenters.WeakRefClassResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;
import de.telekom.smartcredentials.core.plugins.fingerprint.FingerprintView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintLayout extends ConstraintLayout implements FingerprintView {

    private static final int ERROR_TIMEOUT_MILLIS = 1600;

    FingerprintPresenter mPresenter;
    private AuthHandler authHandler;
    private Button mAlternativeAuthButton;
    private ImageView mIcon;
    private TextView mErrorTextView;
    private Button mCancelButton;
    private FingerprintCallback mFingerprintCallback;

    public FingerprintLayout(AuthHandler authHandler, Context context) {
        super(context);
        init(authHandler, context);
    }

    public FingerprintLayout(AuthHandler authHandler, Context context, AttributeSet attrs) {
        super(context, attrs);
        init(authHandler, context);
    }

    public FingerprintLayout(AuthHandler authHandler, Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(authHandler, context);
    }

    @Override
    public void showFingerprintMismatched() {
        mIcon.setImageResource(R.drawable.sc_ic_dialog_fingerprint_error);

        mErrorTextView.setText(mIcon.getResources().getString(R.string.sc_plugin_string_fingerprint_not_recognized));
        requestLayout();
        mErrorTextView.setTextColor(mErrorTextView.getResources().getColor(R.color.sc_fingerprint_warning));

        getHandler().removeCallbacksAndMessages(null);

        resetLayoutWithDelay(ERROR_TIMEOUT_MILLIS);
    }

    @Override
    public void showFingerprintError(CharSequence errString) {
        if (errString != null) {
            mErrorTextView.setText(errString.toString());
        }
    }

    @Override
    public void showFingerprintHelp(CharSequence helpString) {
        if (helpString != null) {
            mIcon.setImageResource(R.drawable.sc_ic_dialog_fingerprint_error);

            mErrorTextView.setText(helpString.toString());
            requestLayout();
            mErrorTextView.setTextColor(mErrorTextView.getResources().getColor(R.color.sc_fingerprint_warning));

            getHandler().removeCallbacksAndMessages(null);

            resetLayoutWithDelay(ERROR_TIMEOUT_MILLIS);
        }
    }

    private static class ResetLayoutRunnable implements Runnable {
        private final WeakReference<FingerprintLayout> mFingerprintLayoutWeakReference;

        ResetLayoutRunnable(FingerprintLayout fingerprintLayout) {
            mFingerprintLayoutWeakReference = new WeakReference<>(fingerprintLayout);
        }

        @Override
        public void run() {
            new WeakRefClassResolver<FingerprintLayout>() {
                @Override
                public void onWeakRefResolved(FingerprintLayout ref) {
                    ref.resetLayout();
                }
            }.execute(mFingerprintLayoutWeakReference);
        }
    }

    public boolean isFingerprintAuthorizationAvailable() {
        return mPresenter.isFeatureAvailable();
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void setClickListeners(final FingerprintCallback fingerprintCallback) {
        mFingerprintCallback = fingerprintCallback;
        if (mFingerprintCallback == null) {
            return;
        }

        mPresenter.setFingerprintCallback(mFingerprintCallback);
        mCancelButton.setOnClickListener(view -> mPresenter.onCancelButtonClicked());
        mAlternativeAuthButton.setOnClickListener(view -> mPresenter.onAlternativeAuthButtonClicked());
    }

    public void listenToFingerprintAuthorization(AuthorizationPluginCallback callback) {
        mPresenter.setAuthPluginCallback(new AuthorizationPluginCallback() {
            @Override
            public void onAuthorized() {
                showAuthenticationSuccess();
                callback.onAuthorized();
            }

            @Override
            public void onPluginUnavailable(Object errorMessage) {
                callback.onPluginUnavailable(errorMessage);
            }

            @Override
            public void onFailed(Object message) {
                if (mFingerprintCallback != null) {
                    mFingerprintCallback.onError();
                }
            }
        });
        mPresenter.startListeningForUserAuth();
    }

    public void onPause() {
        mPresenter.stopListeningForUserAuth();
        resetClickListeners();
    }

    public boolean isAuthorized() {
        return mPresenter.isAuthorized();
    }

    private void init(AuthHandler authHandler, Context context) {
        inflate(context, R.layout.sc_layout_fingerprint, this);
        mAlternativeAuthButton = findViewById(R.id.sc_button_auth);
        mIcon = findViewById(R.id.sc_image_fingerprint_icon);
        mErrorTextView = findViewById(R.id.sc_text_fingerprint_status);
        mCancelButton = findViewById(R.id.sc_button_cancel);

        mPresenter = new FingerprintPresenter(context, authHandler);
        mPresenter.init();
        informPresenterViewIsReady();
    }

    private void informPresenterViewIsReady() {
        mPresenter.viewReady(this);
    }

    private void showAuthenticationSuccess() {
        mCancelButton.setEnabled(false);
        mAlternativeAuthButton.setEnabled(false);
        mIcon.setImageResource(R.drawable.sc_ic_dialog_fingerprint_success);
        mErrorTextView.setTextColor(getContext().getResources().getColor(R.color.sc_fingerprint_success));
        mErrorTextView.setText(getContext().getResources().getString(R.string.sc_plugin_string_fingerprint_success));
        getHandler().removeCallbacksAndMessages(null);
    }

    private void resetLayoutWithDelay(int delay) {
        getHandler().postDelayed(new ResetLayoutRunnable(this), delay);
    }

    private void resetClickListeners() {
        mCancelButton.setOnClickListener(null);
        mAlternativeAuthButton.setOnClickListener(null);
    }

    private void resetLayout() {
        mErrorTextView.setTextColor(getContext().getResources().getColor(R.color.sc_fingerprint_hint));
        mErrorTextView.setText(getContext().getResources().getString(R.string.sc_plugin_string_fingerprint_hint));
        mIcon.setImageResource(R.drawable.sc_ic_dialog_fingerprint);
    }
}

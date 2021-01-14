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

package de.telekom.smartcredentials.core.api;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricAuthorizationPresenter;
import de.telekom.smartcredentials.core.plugins.fingerprint.FingerprintAuthorizationPresenter;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Lucian Iacob on November 09, 2018.
 */
public interface AuthorizationApi {

    /**
     * Method used to authorize an user in order to perform a certain action.
     *
     * @param callback {@link AuthorizationCallback} for retrieving the success or failure events
     * @return {@link SmartCredentialsApiResponse} containing a
     * {@link Fragment} (for api below 23)
     * or a dialog fragment (for api level between 23 and 27)
     * if response is successful, or {@link RootedThrowable} if device is rooted.
     * <p>
     * If the API level is above 27 (API >= 28), this method will return an unsuccessful response stating that the
     * {@link AuthorizationApi#getBiometricAuthorizationPresenter(AuthorizationCallback)} method should be used
     */
    SmartCredentialsApiResponse<Fragment> getAuthorizeUserFragment(@NonNull AuthorizationCallback callback);

    /**
     * Method used to authorize an user in order to perform a certain action.
     *
     * @param callback {@link AuthorizationCallback} for retrieving the success or failure events
     * @return {@link SmartCredentialsApiResponse} containing a
     * {@link BiometricAuthorizationPresenter} (for api level between 23 and 27)
     * if response is successful, or {@link RootedThrowable} if device is rooted.
     * <p>
     * If the API level is above 27 (API >= 28), this method will return an unsuccessful response stating that the
     * {@link AuthorizationApi#getBiometricAuthorizationPresenter(AuthorizationCallback)} method should be used
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<FingerprintAuthorizationPresenter> getFingerprintAuthorizationPresenter(@NonNull AuthorizationCallback callback);

    /**
     * Method used to authorize an user in order to perform a certain action.
     *
     * @param callback {@link AuthorizationCallback} for retrieving the success or failure events
     * @return {@link SmartCredentialsApiResponse} containing a
     * {@link BiometricAuthorizationPresenter} (for api above 28)
     * if response is successful, or {@link RootedThrowable} if device is rooted.
     * <p>
     * If the API level is below 28, this method will return an unsuccessful response stating that the
     * {@link AuthorizationApi#getFingerprintAuthorizationPresenter(AuthorizationCallback)}
     * or {@link AuthorizationApi#getAuthorizeUserFragment(AuthorizationCallback)} methods should be used
     */
    SmartCredentialsApiResponse<BiometricAuthorizationPresenter> getBiometricAuthorizationPresenter(@NonNull AuthorizationCallback callback);
}

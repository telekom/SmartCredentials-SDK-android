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

package de.telekom.smartcredentials.core.actions;

/**
 * Created by Lucian Iacob on January 09, 2019.
 */
@SuppressWarnings("unused")
public abstract class ExecutionCallback {

    /**
     * Call this method when the action is complete
     *
     * @param response {@link Object} containing data needed when action has finished.
     *                 Use this argument to set error if any.
     */
    public abstract void onComplete(Object response);

    /**
     * Call this method when the action fails to execute
     *
     * @param error {@link Object} containing a specific error.
     */
    public abstract void onFailed(Object error);

    /**
     * Call this method when the action cannot be executed (e.g. no internet connection for a backend call).
     *
     * @param error {@link String} specifying the error.
     */
    public abstract void onUnavailable(String error);

    /**
     * Call this method when the action needs authorization before executing it.
     *
     * @param object returns a SmartCredentialsApiResponse containing a fragment if Android API is
     *               lower than 28, otherwise returns a
     *               {@link de.telekom.smartcredentials.core.plugins.fingerprint.BiometricAuthorizationPresenter}.
     */
    public abstract void onAuthorizationRequired(Object object);
}

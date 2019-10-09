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

package de.telekom.smartcredentials.core.utils;

import android.os.Build;

public class ApiLevel {

    public static final String API_ABOVE_28_ERROR_MESSAGE = "Cannot use this methods on devices with API level >= 28. " +
            "Use getBiometricAuthorizationPresenter(AuthorizationCallback) instead";
    public static final String API_BELOW_28_ERROR_MESSAGE = "Cannot use this methods on devices with API level < 28. " +
            "Use getFingerprintAuthorizationPresenter(AuthorizationCallback) " +
            "or getAuthorizeUserFragment(AuthorizationCallback) instead";
    public static final String API_BELOW_23_ERROR_MESSAGE = "Cannot use this methods on devices with API level < 23. " +
            "Use getAuthorizeUserFragment(AuthorizationCallback) instead";

    public static boolean isBelow28() {
        return Build.VERSION.SDK_INT < 28;
    }

    public static boolean isBelow23() {
        return Build.VERSION.SDK_INT < 23;
    }

    public static boolean isAbove28() {
        return Build.VERSION.SDK_INT >= 28;
    }
}

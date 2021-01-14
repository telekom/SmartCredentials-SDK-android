/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

import android.content.Context;

import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.wispr.callbacks.WisprAuthenticationCallback;
import de.telekom.smartcredentials.core.wispr.callbacks.WisprLogoffCallback;
import de.telekom.smartcredentials.core.wispr.callbacks.WisprStatusCallback;

/**
 * Created by Alex.Graur@endava.com at 9/10/2020
 */
public interface WisprApi {

    /**
     * Method used to perform the WISPr login to a hotspot.
     * <br>
     * The login is implemented according to the WISPr 1.0 and 2.0 specifications. It supports
     * simple, proxy, polling and EAP mechanisms. All these versions can be found in
     * {@link de.telekom.smartcredentials.core.wispr.WisprVersion}, In order to work, you have to
     * provide the following information, declared in the
     * {@link de.telekom.smartcredentials.core.wispr.WisprKey}: WISPr version to be used for the
     * login, URL defining the first WISPr endpoint, specify if the login operation should follow
     * or not the login call redirect, WISPr username and password and a timeout for the login
     * call, specified in seconds.
     *
     * @param context                to be used for the login information
     * @param itemEnvelope           containing the login information
     * @param authenticationCallback delivering the login result
     */
    @SuppressWarnings("unused")
    void login(Context context, ItemEnvelope itemEnvelope,
               WisprAuthenticationCallback authenticationCallback);

    /**
     * Method used to perform the WISPr status operation, to check if there is still a WISPr
     * connection enabled.
     * <br>
     * <b>It only works for WISPr version 2.0.</b>
     * <br>
     * The check status is implemented according to the WISPr 2.0 specifications and you have to
     * provide the following information, declared in the
     * {@link de.telekom.smartcredentials.core.wispr.WisprKey}: the status URL, which is obtained
     * from a successful WISPr login operation, and a timeout for the check online call, specified
     * in seconds.
     *
     * @param context        to be used for the check status information
     * @param itemEnvelope   containing the check online information
     * @param statusCallback delivering the check status result
     */
    @SuppressWarnings("unused")
    void checkStatus(Context context, ItemEnvelope itemEnvelope, WisprStatusCallback statusCallback);

    /**
     * Method used to perform the WISPr logoff.
     * <br>
     * The logoff is implemented according to the WISPr 1.0 and 2.0 specifications. In order to
     * work, you have to provide the following information, declared in the
     * {@link de.telekom.smartcredentials.core.wispr.WisprKey}:the logoff URL, which is obtained
     * from a successful WISPr login operation, and a timeout for the logoff call, specified
     * in seconds.
     *
     * @param context        to be used for the logoff information
     * @param itemEnvelope   containing the logoff information
     * @param logoffCallback delivering the logoff result
     */
    @SuppressWarnings("unused")
    void logoff(Context context, ItemEnvelope itemEnvelope, WisprLogoffCallback logoffCallback);
}

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

import android.content.ContentValues;
import android.content.Context;
import android.os.PersistableBundle;

import java.util.HashMap;

import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Suciu Larisa on May, 2022.
 */
public interface CarrierSettingsApi {

    /**
     * Method used to get the SIM's list of APNs and their respective details.
     *
     * @param context {@link Context} the context of the application
     * @return {@link SmartCredentialsApiResponse} a {@link HashMap} containing APN-details pairs if the
     * APN list was accessed successfully; otherwise returns an empty {@link HashMap}
     */
    SmartCredentialsApiResponse<HashMap<String, PersistableBundle>> getApnList(Context context);

    /**
     * Method used to add a new APN in the APN list.
     *
     * @param context {@link Context} the context of the application
     * @param apn     {@link ContentValues} is the APN to be added. Its details are represented by key-value
     *                pairs, where the keys are attributes from the {@link android.provider.Telephony.Carriers} class
     * @return {@link SmartCredentialsApiResponse} true if the APN was successfully inserted into the APN list, false otherwise
     */
    SmartCredentialsApiResponse<Boolean> addNewApn(Context context, ContentValues apn);

    /**
     * Method used to update the details of an APN.
     *
     * @param context       {@link Context} the context of the application
     * @param apn           {@link String} is the APN that needs to be updated
     * @param newApnSetting {@link ContentValues} that holds key-value pairs.
     *                      The key is the field that needs to be updated. It can be any value from the {@link android.provider.Telephony.Carriers} class.
     *                      The value field represents the data that will be updated to the corresponding key.
     * @return {@link SmartCredentialsApiResponse} an {@link Integer} with the number of APNs that have been updated. It should be 1 or 0.
     * 1 if the APN has been updated, 0 otherwise
     */
    SmartCredentialsApiResponse<Integer> updateApn(Context context, String apn, ContentValues newApnSetting);

    /**
     * Method used to delete an APN.
     *
     * @param context {@link Context} the context of the application
     * @param apn     {@link String} is the APN to be deleted.
     * @return {@link SmartCredentialsApiResponse} an {@link Integer} representing the number of APNs that have been deleted. It should be 1 or 0.
     * 1 if the APn has been deleted successfully, 0 otherwise
     */
    SmartCredentialsApiResponse<Integer> deleteApn(Context context, String apn);

    /**
     * Method used to check the if the Roaming option is enabled or disabled.
     *
     * @param context {@link Context} the context of the application
     * @return {@link SmartCredentialsApiResponse} true if Roaming is available to the user, false otherwise
     */
    SmartCredentialsApiResponse<Boolean> isRoamingEnabled(Context context);

    /**
     * Method used to check if the Mobile Data option is enabled or disabled.
     *
     * @param context {@link Context} the context of the application
     * @return {@link SmartCredentialsApiResponse} true if Mobile Data is available to the user, false otherwise
     */
    SmartCredentialsApiResponse<Boolean> isMobileDataEnabled(Context context);

    /**
     * Method used to turn on the Mobile Data for the user.
     *
     * @param context {@link Context} the context of the application
     * @return {@link SmartCredentialsApiResponse} a SmartCredentialsResponse
     */
    SmartCredentialsApiResponse<Void> enableMobileData(Context context);

    /**
     * Method used to turn off the Mobile Data for the user.
     *
     * @param context {@link Context} the context of the application
     * @return {@link SmartCredentialsApiResponse} a SmartCredentialsResponse
     */
    SmartCredentialsApiResponse<Void> disableMobileData(Context context);

}

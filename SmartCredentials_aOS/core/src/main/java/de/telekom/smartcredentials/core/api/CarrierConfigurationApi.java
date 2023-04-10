/*
 * Copyright (c) 2023 Telekom Deutschland AG
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

import java.util.Map;
import java.util.SortedMap;

import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Larisa Suciu on May 3, 2023.
 */
public interface CarrierConfigurationApi {

    /**
     * Method used to retrieve the carrier configuration of a SIM.
     *
     * @param context {@link Context}, the context of the calling application
     * @return a {@link SmartCredentialsApiResponse}, where the sorted map contains the key-value
     * pairs of the configuration; otherwise, the map is empty if the configuration couldn't
     * be retrieved
     */
    SmartCredentialsApiResponse<SortedMap<String, Object>> retrieveCarrierConfiguration(Context context);

    /**
     * Method used to update the carrier configuration.
     * <p>
     * * @param context {@link Context}, the context of the calling application
     *
     * @param newCarrierConfig {@link SortedMap} containing key-value pairs that will be updated
     *                         in the configuration
     * @param subscriptionId   represents the phone's subscription id
     * @return an empty {@link SmartCredentialsApiResponse}, if successful or one that contains a
     * throwable otherwise
     */
    SmartCredentialsApiResponse<Void> setNewCarrierConfiguration(Context context,
                                                                 SortedMap<String, Object> newCarrierConfig,
                                                                 int subscriptionId);

    /**
     * Method used to provide updated carrier configuration data from push notifications.
     * <p>
     * * @param context {@link Context}, the context of the calling application
     *
     * @param data {@link Map} containing key-value pairs that will be parsed in order to update the
     *                       configuration
     * @return an empty {@link SmartCredentialsApiResponse}, if successful or one that contains a
     * throwable otherwise
     */
    SmartCredentialsApiResponse<Void> pushCarrierConfigurationMessage(Context context,
                                                                      Map<String, String> data);
}

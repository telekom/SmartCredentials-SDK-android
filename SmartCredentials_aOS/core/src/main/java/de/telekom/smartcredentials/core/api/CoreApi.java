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

import android.content.Context;

import de.telekom.smartcredentials.core.actions.ExecutionCallback;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.rootdetector.strategy.RootDetectionOptionListener;

public interface CoreApi {

    /**
     * Detects if the device is rooted.
     *
     * @return a {@link SmartCredentialsApiResponse<Boolean>}, where the boolean is true if the
     * device is rooted, false otherwise.
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> isDeviceRooted();

    SmartCredentialsApiResponse<Boolean> isDeviceRooted(RootDetectionOptionListener listener);

    /**
     * Executes a specific action over an item envelope
     *
     * @param itemEnvelope {@link ItemEnvelope} needed for executing the action
     * @param actionId     {@link String} specifying the ID of the action within the actions list
     *                     stored inside item envelope
     * @param callback     {@link ExecutionCallback} an abstract class needed for app2library
     *                     communication and event forwarding
     * @return {@link SmartCredentialsApiResponse} wrapping a {@link Void}. If the device is rooted
     * then the response is unsuccessful and none of callback's methods will be invoked
     */
    SmartCredentialsApiResponse<Void> execute(Context context, ItemEnvelope itemEnvelope, String actionId,
                                              ExecutionCallback callback);

}

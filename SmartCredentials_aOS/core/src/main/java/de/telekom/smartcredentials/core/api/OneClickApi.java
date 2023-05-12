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
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;

import java.util.List;
import java.util.Map;

public interface OneClickApi {

    /**
     * Method used for UI integration. Must be bound to an activity in onCreate.
     *
     * @param activity - needs to be AppCompatActivity
     */
    void bind(@NonNull AppCompatActivity activity);

    /**
     * Method used for UI integration. A ComposeView needs to be passed.
     *
     * @param composeView - compose view to build the OCB UI within
     */
    void setComposeView(ComposeView composeView);

    /**
     * Method used for UI integration.
     * Needs to be called on the bound activities onDestroy.
     */
    void unbind();

    /**
     * Method used to force a recommendation delivery. To be removed in future versions.
     * <p>
     *
     * @param productIds - list of productIds needed to make a recommendation.
     */
    @Deprecated
    void makeRecommendation(List<String> productIds);

    /**
     * Method used to pass messages from a FirebaseMessagingService to the OneClickApi implementation.
     *
     * @param data - data field of RemoteMessage from the onReceive method of a FirebaseMessagingService
     */
    void pushRecommendationMessage(Map<String, String> data);

    /**
     * Method used to pass the latest Firebase Token to the OneClickApi implementation.
     *
     * @param token - Firebase token - needs to be passed during app initialization and whenever it
     *              changes
     */
    void updateFirebaseToken(String token);

}

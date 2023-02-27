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

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

public interface OneClickApi {

    /**
     * Used to force a recommendation delivery. To be removed in future versions.
     * <p>
     * A list of productIds is required to make a recommendation.
     */
    @Deprecated
    void makeRecommendation(List<String> productIds, String firebaseServerKey);

    /**
     * Used to pass messages from a FirebaseMessagingService to the OneClickApi implementation.
     */
    void pushRecommendationMessage(Map<String, String> data);

    /**
     * Used to pass the latest Firebase Token to the OneClickApi implementation.
     */
    void updateFirebaseToken(String token);

    void bind(AppCompatActivity activity);

    void unbind();
}

/*
 * Copyright (c) 2020 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.core.pushnotifications.models;

import android.net.Uri;

/**
 * Created by gabriel.blaj@endava.com at 5/15/2020
 */
public abstract class SmartCredentialsNotification {

        public abstract String getTitle();

        public abstract String getBody();

        public abstract String[] getBodyLocalizationArgs();

        public abstract String getBodyLocalizationKey();

        public abstract String getChannelId();

        public abstract String getClickAction();

        public abstract String getColor();

        public abstract String getIcon();

        public abstract Uri getImageUrl();

        public abstract Uri getLink();

        public abstract String getSound();

        public abstract String getTag();
        
        public abstract String[] getTitleLocalizationArgs();

        public abstract String getTitleLocalizationKey();
}

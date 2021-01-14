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

package de.telekom.smartcredentials.pushnotifications.controllers;

import de.telekom.smartcredentials.core.pushnotifications.enums.ServiceType;
import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;
import de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository;

/**
 * Created by Alex.Graur@endava.com at 5/22/2020
 */
public class ControllerFactory {

    private final PushNotificationsStorageRepository mStorageRepository;

    public ControllerFactory() {
        mStorageRepository = ObjectGraphCreatorPushNotifications.getInstance()
                .providePushNotificationsStorageRepository();
    }

    public BaseController getController() {
        if (mStorageRepository.getPushNotificationsConfigString(PushNotificationsStorageRepository.KEY_SERVICE_TYPE)
                .equals(ServiceType.TPNS.name())) {
            return new TpnsController();
        } else {
            return new FcmController();
        }
    }
}

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

package de.telekom.smartcredentials.pushnotifications.factory;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.PushNotificationsApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.api.rx.RxPushNotificationsApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;
import de.telekom.smartcredentials.core.pushnotifications.configuration.PushNotificationsConfiguration;
import de.telekom.smartcredentials.core.pushnotifications.enums.ServiceType;
import de.telekom.smartcredentials.pushnotifications.controllers.PushNotificationsController;
import de.telekom.smartcredentials.pushnotifications.controllers.rx.RxPushNotificationsController;
import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;
import de.telekom.smartcredentials.pushnotifications.utils.FirebaseManager;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class SmartCredentialsPushNotificationsFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials PushNotifications Module have not been initialized";
    private static final String RX_MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials RxPushNotifications Module have not been initialized";

    private static PushNotificationsController sPushNotificationsController;
    private static RxPushNotificationsController sRxPushNotificationsController;

    private SmartCredentialsPushNotificationsFactory() {
        // required empty constructor
    }

    public static synchronized void initSmartCredentialsPushNotificationsModule(
            @NonNull final CoreApi coreApi,
            @NonNull final StorageApi storageApi,
            @NonNull final PushNotificationsConfiguration configuration) {

        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE.getModuleName());
        }
        FirebaseManager.initializeFirebase(configuration);
        ObjectGraphCreatorPushNotifications objectGraphCreatorPushNotifications =
                ObjectGraphCreatorPushNotifications.getInstance();
        objectGraphCreatorPushNotifications.init(storageApi);
        objectGraphCreatorPushNotifications.setService(configuration.getAutoSubscribeState(),
                configuration.getServiceType());
        if (configuration.getServiceType().equals(ServiceType.TPNS)) {
            objectGraphCreatorPushNotifications.setTpnsService(configuration.getTpnsApplicationKey(),
                    configuration.getTpnsEnvironment());
        }
        objectGraphCreatorPushNotifications.setSenderId(configuration.getGcmSenderId());
        sPushNotificationsController = objectGraphCreatorPushNotifications
                .provideApiControllerPushNotifications(coreController);
        sRxPushNotificationsController = objectGraphCreatorPushNotifications
                .provideRxApiControllerPushNotifications(coreController);
    }

    @NonNull
    public static synchronized PushNotificationsApi getPushNotificationsApi() {
        if (sPushNotificationsController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sPushNotificationsController;
    }

    @NonNull
    public static synchronized RxPushNotificationsApi getRxPushNotificationsApi() {
        if (sRxPushNotificationsController == null) {
            throw new RuntimeException(RX_MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sRxPushNotificationsController;
    }

    public static void clear() {
        ObjectGraphCreatorPushNotifications.destroy();
        sPushNotificationsController = null;
        sRxPushNotificationsController = null;
    }
}

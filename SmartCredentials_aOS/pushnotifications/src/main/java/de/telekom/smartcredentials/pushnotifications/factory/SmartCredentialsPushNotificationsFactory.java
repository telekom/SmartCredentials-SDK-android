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

import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.PushNotificationsApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.pushnotifications.configuration.PushNotificationsConfiguration;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;
import de.telekom.smartcredentials.core.pushnotifications.enums.ServiceType;
import de.telekom.smartcredentials.pushnotifications.utils.FirebaseManager;
import de.telekom.smartcredentials.pushnotifications.controllers.PushNotificationsController;
import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class SmartCredentialsPushNotificationsFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials PusNotifications Module have not been initialized";

    private static PushNotificationsController sPushNotificationsController;

    private SmartCredentialsPushNotificationsFactory() {
        // required empty constructor
    }

    @NonNull
    public static synchronized PushNotificationsApi initSmartCredentialsPushNotificationsModule(
            @NonNull final CoreApi coreApi,
            @NonNull final StorageApi storageApi,
            @NonNull final PushNotificationsConfiguration configuration,
            final boolean autoSubscribe) {

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
        if(configuration.getServiceType().equals(ServiceType.TPNS)){
            objectGraphCreatorPushNotifications.setService(autoSubscribe,
                    configuration.getServiceType(), configuration.getTpnsApplicationKey(), configuration.isTpnsInProduction());
        } else {
            objectGraphCreatorPushNotifications.setService(autoSubscribe, configuration.getServiceType());
        }
        sPushNotificationsController = objectGraphCreatorPushNotifications.provideApiControllerPushNotifications(coreController);
        return sPushNotificationsController;
    }

    @NonNull
    public static synchronized PushNotificationsApi getPushNotificationsApi() {
        if (sPushNotificationsController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sPushNotificationsController;
    }

    public static void clear() {
        ObjectGraphCreatorPushNotifications.destroy();
        sPushNotificationsController = null;
    }
}

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

package de.telekom.smartcredentials.pushnotifications.di;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.pushnotifications.enums.ServiceType;
import de.telekom.smartcredentials.core.pushnotifications.enums.TpnsEnvironment;
import de.telekom.smartcredentials.pushnotifications.controllers.rx.RxPushNotificationsController;
import de.telekom.smartcredentials.pushnotifications.handlers.PushNotificationsHandler;
import de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository;
import de.telekom.smartcredentials.pushnotifications.controllers.PushNotificationsController;
import de.telekom.smartcredentials.pushnotifications.rest.models.TpnsRequestBody;

import static de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository.KEY_AUTO_SUBSCRIBE;
import static de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository.KEY_SERVICE_TYPE;
import static de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository.KEY_TPNS_APPLICATION_KEY;
import static de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository.KEY_TPNS_PRODUCTION_STATE;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class ObjectGraphCreatorPushNotifications {

    private static ObjectGraphCreatorPushNotifications sInstance;
    private PushNotificationsController mController;
    private StorageApi mStorageApi;

    private ObjectGraphCreatorPushNotifications() {
        // required empty constructor
    }

    public static ObjectGraphCreatorPushNotifications getInstance() {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorPushNotifications();
        }
        return sInstance;
    }

    public void init(StorageApi storageApi) {
        mStorageApi = storageApi;
    }

    @NonNull
    public PushNotificationsController provideApiControllerPushNotifications(CoreController coreController) {
        if(mController == null) {
            mController = new PushNotificationsController(coreController);
        }
        return mController;
    }

    @NonNull
    public RxPushNotificationsController provideRxApiControllerPushNotifications(CoreController coreController) {
        if(mController == null) {
            mController = new PushNotificationsController(coreController);
        }
        return new RxPushNotificationsController(mController);
    }

    public void setService(boolean autoSubscribe, ServiceType service) {
        providePushNotificationsStorageRepository().saveConfigurationValue(
                KEY_AUTO_SUBSCRIBE, autoSubscribe);
        providePushNotificationsStorageRepository().saveConfigurationValue(
                KEY_SERVICE_TYPE, service.name());
    }

    public void setTpnsService(String applicationKey, TpnsEnvironment environment) {
        if(applicationKey != null) {
            providePushNotificationsStorageRepository().saveConfigurationValue(
                    KEY_TPNS_APPLICATION_KEY, applicationKey);
        }
        if(environment != null){
            if(environment == TpnsEnvironment.TESTING){
                providePushNotificationsStorageRepository().saveConfigurationValue(
                        KEY_TPNS_PRODUCTION_STATE, false);
            } else {
                providePushNotificationsStorageRepository().saveConfigurationValue(
                        KEY_TPNS_PRODUCTION_STATE, true);
            }
        }
    }

    private StorageApi getStorageApi() {
        if (mStorageApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.STORAGE_MODULE + " from "
                    + SmartCredentialsModuleSet.STORAGE_MODULE + " has not been initialized");
        }
        return mStorageApi;
    }

    public PushNotificationsStorageRepository providePushNotificationsStorageRepository(){
        return PushNotificationsStorageRepository.getInstance(getStorageApi());
    }

    public TpnsRequestBody provideTpnsRequestBody(){
        return new TpnsRequestBody();
    }

    public PushNotificationsHandler providePushNotificationsHandler(){
        return PushNotificationsHandler.getInstance();
    }

    public static void destroy() {
        sInstance = null;
    }
}

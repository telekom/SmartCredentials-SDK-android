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

package de.telekom.smartcredentials.pushnotifications.controllers;

import de.telekom.smartcredentials.core.api.PushNotificationsApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsCallback;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsMessageCallback;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsTokenCallback;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class PushNotificationsController implements PushNotificationsApi {

    private final CoreController mCoreController;
    private final ControllerFactory mControllerFactory;

    public PushNotificationsController(CoreController coreController) {
        mCoreController = coreController;
        mControllerFactory = new ControllerFactory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Void> subscribeAllNotifications(PushNotificationsCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SUBSCRIBE)) {
            String errorMessage = SmartCredentialsFeatureSet.SUBSCRIBE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return mControllerFactory.getController().subscribeAllNotifications(callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Void> unsubscribeAllNotifications(PushNotificationsCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.UNSUBSCRIBE)) {
            String errorMessage = SmartCredentialsFeatureSet.UNSUBSCRIBE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return mControllerFactory.getController().unsubscribeAllNotifications(callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Void> subscribeToTopic(String topic, PushNotificationsCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SUBSCRIBE_TO_TOPIC)) {
            String errorMessage = SmartCredentialsFeatureSet.SUBSCRIBE_TO_TOPIC.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return mControllerFactory.getController().subscribeToTopic(topic, callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Void> unsubscribeFromTopic(String topic, PushNotificationsCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.UNSUBSCRIBE_FROM_TOPIC)) {
            String errorMessage = SmartCredentialsFeatureSet.UNSUBSCRIBE_FROM_TOPIC.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return mControllerFactory.getController().unsubscribeFromTopic(topic, callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsResponse<String> retrieveToken() {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.RETRIEVE_TOKEN)) {
            String errorMessage = SmartCredentialsFeatureSet.RETRIEVE_TOKEN.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return mControllerFactory.getController().retrieveToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsResponse<String> retrieveDeviceId() {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.RETRIEVE_DEVICE_ID)) {
            String errorMessage = SmartCredentialsFeatureSet.RETRIEVE_DEVICE_ID.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return mControllerFactory.getController().retrieveDeviceId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsResponse<Void> setTokenRefreshedCallback(PushNotificationsTokenCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SET_TOKEN_REFRESHED_CALLBACK)) {
            String errorMessage = SmartCredentialsFeatureSet.SET_TOKEN_REFRESHED_CALLBACK.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return mControllerFactory.getController().setTokenRefreshedCallback(callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsResponse<Void> setMessageReceivedCallback(PushNotificationsMessageCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SET_MESSAGE_RECEIVED_CALLBACK)) {
            String errorMessage = SmartCredentialsFeatureSet.SET_MESSAGE_RECEIVED_CALLBACK.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return mControllerFactory.getController().setMessageReceivedCallback(callback);
    }
}

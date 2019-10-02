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

package de.telekom.smartcredentials.core.domain.utils;

import android.net.Uri;
import android.os.Looper;

import org.mockito.Mockito;

import de.telekom.smartcredentials.core.actions.ActionSelector;
import de.telekom.smartcredentials.core.controllers.CreationController;
import de.telekom.smartcredentials.core.handlers.CreationHandler;
import de.telekom.smartcredentials.core.handlers.HttpHandler;
import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.model.request.RequestParams;
import de.telekom.smartcredentials.core.plugins.authentication.OTPPlugin;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthenticationPluginCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;
import de.telekom.smartcredentials.core.plugins.scanner.ScannerPlugin;
import de.telekom.smartcredentials.core.repositories.Repository;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionApi;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;

/**
 * Created by Lucian Iacob on November 07, 2018.
 */
public class MocksProvider {

    public static AuthenticationPluginCallback provideAuthPluginCallback() {
        return Mockito.mock(AuthenticationPluginCallback.class);
    }

    public static CreationController provideCreationController() {
        return Mockito.mock(CreationController.class);
    }

    public static ItemDomainModel provideItemDomainModel() {
        return Mockito.mock(ItemDomainModel.class);
    }

    public static ServicePluginCallback provideServicePluginCallback() {
        return Mockito.mock(ServicePluginCallback.class);
    }

    public static Looper provideMainThreadLooper() {
        return Mockito.mock(Looper.class);
    }

    public static CreationHandler provideCreationHandler() {
        return Mockito.mock(CreationHandler.class);
    }

    public static ScannerPlugin provideScannerPlugin() {
        return Mockito.mock(ScannerPlugin.class);
    }

    public static OTPPlugin provideOtpPlugin() {
        return Mockito.mock(OTPPlugin.class);
    }

    public static AuthorizationPluginCallback provideAuthorizationPluginCallback() {
        return Mockito.mock(AuthorizationPluginCallback.class);
    }

    public static ScannerPluginCallback provideScannerPluginCallback() {
        return Mockito.mock(ScannerPluginCallback.class);
    }

    public static Repository provideRepository() {
        return Mockito.mock(Repository.class);
    }

    public static EncryptionStrategy provideEncryptionStrategy() {
        return Mockito.mock(EncryptionStrategy.class);
    }

    public static Uri provideUri() {
        return Mockito.mock(Uri.class);
    }

    public static HttpHandler provideHttpHandler() {
        return Mockito.mock(HttpHandler.class);
    }

    public static RequestParams provideRequestParams() {
        return Mockito.mock(RequestParams.class);
    }

    public static DocumentScannerPluginCallback provideDocumentScannerPluginCallback() {
        return Mockito.mock(DocumentScannerPluginCallback.class);
    }

    public static TokenPluginCallback provideTokenPluginCallback() {
        return Mockito.mock(TokenPluginCallback.class);
    }

    public static ItemDomainMetadata provideItemDomainMetadata() {
        return Mockito.mock(ItemDomainMetadata.class);
    }

    public static ItemDomainData provideItemDomainData() {
        return Mockito.mock(ItemDomainData.class);
    }

    public static RootDetectionApi provideSecurityLeackCheckPlugin() {
        return Mockito.mock(RootDetectionApi.class);
    }

    public static ActionSelector provideActionSelector() {
        return Mockito.mock(ActionSelector.class);
    }
}

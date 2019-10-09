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

package de.telekom.smartcredentials.documentscanner.controllers;

import android.support.annotation.NonNull;

import com.microblink.util.RecognizerCompatibility;
import com.microblink.util.RecognizerCompatibilityStatus;

import java.util.Objects;

import de.telekom.smartcredentials.core.api.DocumentScannerApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.documentscanner.DocumentScannerCallback;
import de.telekom.smartcredentials.core.documentscanner.DocumentScannerLayout;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.documentscanner.config.SmartCredentialsDocumentScanConfiguration;
import de.telekom.smartcredentials.documentscanner.view.DocumentScannerLayoutImpl;

import static de.telekom.smartcredentials.documentscanner.callback.PluginCallbackDocScannerConverter.convertToDomainPluginCallback;

public class DocumentScannerController implements DocumentScannerApi<SmartCredentialsDocumentScanConfiguration> {

    private final CoreController mCoreController;

    public DocumentScannerController(CoreController coreController) {
        mCoreController = coreController;
    }

    private Object getDocumentScanner(SmartCredentialsDocumentScanConfiguration configuration, DocumentScannerPluginCallback pluginCallback) {
        return DocumentScannerLayoutImpl.getNewInstance(configuration, pluginCallback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<DocumentScannerLayout> getDocumentScannerView(@NonNull SmartCredentialsDocumentScanConfiguration configuration, DocumentScannerCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getDocumentScannerView");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.DOCUMENT_SCANNER)) {
            String errorMessage = SmartCredentialsFeatureSet.DOCUMENT_SCANNER.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        RecognizerCompatibilityStatus compatibilityStatus = RecognizerCompatibility
                .getRecognizerCompatibilityStatus(configuration.getContext());
        if (compatibilityStatus != RecognizerCompatibilityStatus.RECOGNIZER_SUPPORTED) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), compatibilityStatus.name());
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(compatibilityStatus.name()));
        }

        Objects.requireNonNull(configuration);

        DocumentScannerPluginCallback pluginCallback = convertToDomainPluginCallback(callback, getClass().getSimpleName());

        DocumentScannerLayout view = (DocumentScannerLayout) getDocumentScanner(configuration, pluginCallback);
        return new SmartCredentialsResponse<>(view);
    }
}

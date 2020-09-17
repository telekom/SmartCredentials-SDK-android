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

package de.telekom.smartcredentials.documentscanner.presenter;

import com.microblink.entities.recognizers.Recognizer;

import de.telekom.smartcredentials.core.documentscanner.CompletionCallback;
import de.telekom.smartcredentials.core.documentscanner.TorchState;
import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;
import de.telekom.smartcredentials.documentscanner.config.SmartCredentialsDocumentScanConfiguration;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
import de.telekom.smartcredentials.documentscanner.view.DocumentScannerView;

public interface DocumentScannerPresenter {

    void init(DocumentScannerView documentScannerLayout, SmartCredentialsDocumentScanConfiguration configuration,
              DocumentScannerPluginCallback pluginCallback);

    void changeRecognizer(ScannerRecognizer recognizer);

    void changeTorchMode(TorchState torchState, CompletionCallback callback);

    boolean isRecognizerSupported(ScannerRecognizer recognizer);

    void setScanningArea(float leftAreaPercentage, float topAreaPercentage, float widthPercentage, float heightPercentage);

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void validateRecognizer(Recognizer recognizer);
}

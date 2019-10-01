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

package de.telekom.smartcredentials.documentscanner.view;

import android.content.Context;

import com.microblink.entities.Entity;
import com.microblink.geometry.Rectangle;
import com.microblink.metadata.MetadataCallbacks;
import com.microblink.view.CameraEventsListener;
import com.microblink.view.recognition.ScanResultListener;

import de.telekom.smartcredentials.core.documentscanner.CompletionCallback;
import de.telekom.smartcredentials.documentscanner.config.SmartCredentialsDocumentScanConfiguration;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;

public interface DocumentScannerView {

    void createRecognizerView(Context context);

    void setRecognizerSettings(SmartCredentialsDocumentScanConfiguration scannerConfiguration);

    void setListeners(ScanResultListener scanResultListener, CameraEventsListener listener);

    void reconfigureRecognizer(ScannerRecognizer recognizer);

    void setTorch(boolean value, CompletionCallback callback);

    void setScanningRegion(Rectangle rectangle, boolean rotateRegionWithDevice);

    void createRecognizer();

    void startRecognizer();

    void resumeRecognizer();

    void setMetadataCallbacks(MetadataCallbacks metadataCallbacks);

    void pauseRecognizer();

    void stopRecognizer();

    void destroyRecognizer();

    Entity.Result getRecognizerResult();
}

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

/*
 * Created by Lucian Iacob on 6/29/18 2:04 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.core.plugins.callbacks;

import android.graphics.Rect;

import de.telekom.smartcredentials.core.model.DocumentScannerResult;

public abstract class DocumentScannerPluginCallback<PluginUnavailable> extends BaseScannerPluginCallback<DocumentScannerResult, PluginUnavailable> {

    public abstract void onFirstSideRecognitionFinished();

    public abstract void onScannedFailed();

    public abstract void onAutoFocusStopped(Rect[] rects);

    public abstract void onAutoFocusStarted(Rect[] rects);

    public abstract void onAutoFocusFailed();

    public abstract void onScannerStopped();
}

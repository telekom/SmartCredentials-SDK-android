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

package de.telekom.smartcredentials.camera;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.camera.barcode.BarcodeCameraScannerLayoutImpl;
import de.telekom.smartcredentials.camera.ocr.OcrCameraScannerLayoutImpl;
import de.telekom.smartcredentials.core.camera.CameraScannerLayout;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class CameraScannerPluginTest {

    private static final int BARCODE_TYPE = 1;
    private ScannerPluginCallback<ScannerPluginUnavailable> mPluginCallback;
    private Context mContext;
    private CameraScannerPlugin mCameraScannerPlugin;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        mPluginCallback = Mockito.mock(ScannerPluginCallback.class);
        mContext = Mockito.mock(Context.class);
        mCameraScannerPlugin = new CameraScannerPlugin();
    }

    @Test
    @PrepareForTest(BarcodeCameraScannerLayoutImpl.class)
    public void getBarcodeScannerReturnsAnInstanceOfBarcodeCameraScannerLayout() {
        PowerMockito.mockStatic(BarcodeCameraScannerLayoutImpl.class);
        when(BarcodeCameraScannerLayoutImpl.getNewInstance(mContext, mPluginCallback, BARCODE_TYPE))
                .thenReturn(Mockito.mock(BarcodeCameraScannerLayoutImpl.class));

        CameraScannerLayout cameraScannerLayout = mCameraScannerPlugin
                .getBarcodeScanner(mContext, mPluginCallback, BARCODE_TYPE);

        assertTrue(cameraScannerLayout instanceof BarcodeCameraScannerLayoutImpl);
    }

    @Test
    @PrepareForTest(OcrCameraScannerLayoutImpl.class)
    public void getTextScanner() {
        PowerMockito.mockStatic(OcrCameraScannerLayoutImpl.class);
        when(OcrCameraScannerLayoutImpl.getNewInstance(mContext, mPluginCallback))
                .thenReturn(Mockito.mock(OcrCameraScannerLayoutImpl.class));

        CameraScannerLayout cameraScannerLayout = mCameraScannerPlugin.getTextScanner(mContext, mPluginCallback);

        assertTrue(cameraScannerLayout instanceof OcrCameraScannerLayoutImpl);
    }

}
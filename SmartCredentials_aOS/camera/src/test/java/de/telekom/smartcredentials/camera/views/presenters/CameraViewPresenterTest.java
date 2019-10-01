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

package de.telekom.smartcredentials.camera.views.presenters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.SCANNER_NOT_STARTED;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ApiLoggerResolver.class)
public class CameraViewPresenterTest {

    private static final String TAG = "Some tag";

    private ScannerPluginCallback<ScannerPluginUnavailable> mPluginCallback;

    private CameraViewPresenter mPresenter;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        PowerMockito.mockStatic(ApiLoggerResolver.class);
        mPluginCallback = Mockito.mock(ScannerPluginCallback.class);

        mPresenter = new CameraViewPresenter() {
            @Override
            public String getTAG() {
                return TAG;
            }
        };
    }

    @Test
    public void onErrorCallsOnlyApiLoggerLogErrorWhenPluginCallbackNotSet() {
        mPresenter.setPluginCallback(null);

        ScannerPluginUnavailable errorMessage = SCANNER_NOT_STARTED;
        mPresenter.onError(errorMessage);

        PowerMockito.verifyStatic(ApiLoggerResolver.class, VerificationModeFactory.times(1));
        ApiLoggerResolver.logError(TAG, errorMessage.getDesc());
    }

    @Test
    public void onErrorCallsApiLoggerLogErrorAndPluginCallbackMethod() {
        mPresenter.setPluginCallback(mPluginCallback);

        ScannerPluginUnavailable errorMessage = SCANNER_NOT_STARTED;
        mPresenter.onError(errorMessage);

        PowerMockito.verifyStatic(ApiLoggerResolver.class, VerificationModeFactory.times(1));
        ApiLoggerResolver.logError(TAG, errorMessage.getDesc());
        verify(mPluginCallback).onPluginUnavailable(errorMessage);
    }

}
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

package de.telekom.smartcredentials.camera.ocr.presenters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.camera.ocr.TextParser;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest(ParserAsyncTask.class)
public class OcrCameraScannerPresenterTest {

    private OcrCameraScannerView mView;
    private ScannerPluginCallback mPluginCallback;
    private TextParser mTextParser;
    private ParserAsyncTask mParserAsyncTask;

    private OcrCameraScannerPresenter mPresenter;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(ParserAsyncTask.class);

        mView = Mockito.mock(OcrCameraScannerView.class);
        mPluginCallback = Mockito.mock(ScannerPluginCallback.class);
        mTextParser = Mockito.mock(TextParser.class);
        mParserAsyncTask = Mockito.mock(ParserAsyncTask.class);

        mPresenter = new OcrCameraScannerPresenter();
        mPresenter.viewReady(mView);
        mPresenter.setPluginCallback(mPluginCallback);
    }

    @Test
    public void prepareDetectionCallsPluginCallbackOnErrorMethodWhenCameraIsNotStarted() {
        mPresenter.mIsCameraStarted = false;
        mPresenter.prepareDetection(mTextParser, true);

        verify(mPluginCallback).onPluginUnavailable(ScannerPluginUnavailable.SCANNER_NOT_STARTED);
        verify(mView, never()).takeCurrentPicture();
    }

    @Test
    public void prepareDetectionCallsViewMethodWhenCameraIsStarted() {
        mPresenter.mIsCameraStarted = true;
        mPresenter.prepareDetection(mTextParser, true);

        verify(mPluginCallback, never()).onPluginUnavailable(ScannerPluginUnavailable.SCANNER_NOT_STARTED);
        verify(mView).takeCurrentPicture();
    }

    @Test
    public void onDetectedCreatesAndExecutesAsyncTask() {
        mPresenter.mTextParser = mTextParser;
        PowerMockito.when(ParserAsyncTask.getInstance(mPluginCallback, mTextParser))
                .thenReturn(mParserAsyncTask);

        List<String> textList = new ArrayList<>();
        mPresenter.onDetected(textList);

        verify(mParserAsyncTask).execute(textList.toArray(new String[0]));
    }

    @Test
    public void onDetectedDoesNotCreateAndExecuteAsyncTaskIfTextParserIsNull() {
        mPresenter.mTextParser = null;
        List<String> detected = new ArrayList<>();
        detected.add("some string");
        mPresenter.onDetected(detected);

        PowerMockito.verifyStatic(ParserAsyncTask.class);
        ParserAsyncTask.notifyScannedResultsReady(mPluginCallback, detected);
    }

    @Test
    public void onPictureTakenCallsMethodOnView() {
        byte[] bytes = new byte[0];
        mPresenter.onPictureTaken(bytes);

        verify(mView).loadPicture(bytes);
    }

    @Test
    public void onStopRequestedDoesNothingWhenAsyncTaskObjectIsNull() {
        mPresenter.mParserAsyncTask = null;

        mPresenter.onStopRequested();

        verify(mParserAsyncTask, never()).cancel(true);
    }

    @Test
    public void onStopRequestedDoesNothingWhenAsyncTaskObjectIsCancelled() {
        when(mParserAsyncTask.isCancelled()).thenReturn(true);
        mPresenter.mParserAsyncTask = mParserAsyncTask;

        mPresenter.onStopRequested();

        verify(mParserAsyncTask, never()).cancel(true);
    }

    @Test
    public void onStopRequestedCallsCancelOnAsyncTask() {
        when(mParserAsyncTask.isCancelled()).thenReturn(false);
        mPresenter.mParserAsyncTask = mParserAsyncTask;

        mPresenter.onStopRequested();

        verify(mParserAsyncTask).cancel(true);
    }

}
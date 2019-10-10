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

package de.telekom.smartcredentials.camera.ocr;

import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class OcrDetectorProcessorTest {

    private OcrBlockView mOcrBlockView;
    private OcrDetectorProcessor mOcrDetectorProcessor;

    @Before
    public void setUp() {
        mOcrBlockView = Mockito.mock(OcrBlockView.class);
        mOcrDetectorProcessor = new OcrDetectorProcessor(mOcrBlockView);
    }

    @Test
    public void receiveDetectionsCallsClearBlockAndAddBlock() {
        String detectedValue = "some text";

        TextBlock textBlock = Mockito.mock(TextBlock.class);
        when(textBlock.getValue()).thenReturn(detectedValue);

        SparseArray<TextBlock> detectedTextList = Mockito.mock(SparseArray.class);
        when(detectedTextList.size()).thenReturn(1);

        Detector.Detections<TextBlock> textBlockDetections = Mockito.mock(Detector.Detections.class);
        when(textBlockDetections.getDetectedItems()).thenReturn(detectedTextList);

        mOcrDetectorProcessor.receiveDetections(textBlockDetections);

        verify(mOcrBlockView).clearBlockView();
        verify(mOcrBlockView).addBlockView(any(OcrTextBlock.class));
    }

    @Test
    public void receiveDetectionsCallsClearBlockButNotAddBlockIsNoDetections() {
        String detectedValue = "some text";

        TextBlock textBlock = Mockito.mock(TextBlock.class);
        when(textBlock.getValue()).thenReturn(detectedValue);

        SparseArray<TextBlock> detectedTextList = Mockito.mock(SparseArray.class);
        when(detectedTextList.size()).thenReturn(0);

        Detector.Detections<TextBlock> textBlockDetections = Mockito.mock(Detector.Detections.class);
        when(textBlockDetections.getDetectedItems()).thenReturn(detectedTextList);

        mOcrDetectorProcessor.receiveDetections(textBlockDetections);

        verify(mOcrBlockView).clearBlockView();
        verify(mOcrBlockView, never()).addBlockView(any(OcrTextBlock.class));
    }

    @Test
    public void releaseClearsDetectedTextList() {
        mOcrDetectorProcessor.release();

        verify(mOcrBlockView).clearBlockView();
    }

}
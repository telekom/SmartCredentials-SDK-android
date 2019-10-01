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

package de.telekom.smartcredentials.camera.views;

import android.content.Context;
import android.util.AttributeSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.telekom.smartcredentials.camera.ocr.OcrTextBlock;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class DetectedBlockViewTest {

    private DetectedBlockView mDetectedBlockView;

    @Before
    public void setup() {
        mDetectedBlockView = new DetectedBlockView(Mockito.mock(Context.class),
                Mockito.mock(AttributeSet.class));
    }

    @Test
    public void clearingBlockViewIsNotAllowedIfDetectionIsBlocked() {
        OcrTextBlock ocrTextBlock = Mockito.mock(OcrTextBlock.class);
        mDetectedBlockView.mBlockHandlerSet.add(ocrTextBlock);
        assertEquals(1, mDetectedBlockView.mBlockHandlerSet.size());

        mDetectedBlockView.blockDetection();
        mDetectedBlockView.clearBlockView();
        assertEquals(1, mDetectedBlockView.mBlockHandlerSet.size());
    }

    @Test
    public void addingOcrTextBlockIsNotAllowedIfDetectionIsBlocked() {
        OcrTextBlock ocrTextBlock = Mockito.mock(OcrTextBlock.class);
        mDetectedBlockView.mBlockHandlerSet.add(ocrTextBlock);
        assertEquals(1, mDetectedBlockView.mBlockHandlerSet.size());

        mDetectedBlockView.blockDetection();
        OcrTextBlock ocrTextBlock2 = Mockito.mock(OcrTextBlock.class);
        mDetectedBlockView.addBlockView(ocrTextBlock2);
        assertEquals(1, mDetectedBlockView.mBlockHandlerSet.size());
    }
}
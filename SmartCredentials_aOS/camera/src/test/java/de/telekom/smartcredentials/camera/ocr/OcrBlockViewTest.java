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

import android.content.Context;
import android.util.AttributeSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class OcrBlockViewTest {
    private OcrBlockView mOcrBlockView;

    @Before
    public void setup() {
        mOcrBlockView = new OcrBlockView(Mockito.mock(Context.class), Mockito.mock(AttributeSet.class));
    }

    @Test
    public void clearingBlockViewIsNotAllowedIfDetectionIsBlocked() {
        OcrTextBlock ocrTextBlock = Mockito.mock(OcrTextBlock.class);
        mOcrBlockView.mBlockHandlerSet.add(ocrTextBlock);
        assertEquals(1, mOcrBlockView.mBlockHandlerSet.size());

        mOcrBlockView.blockDetection();
        mOcrBlockView.clearBlockView();
        assertEquals(1, mOcrBlockView.mBlockHandlerSet.size());
    }

    @Test
    public void addingOcrTextBlockIsNotAllowedIfDetectionIsBlocked() {
        OcrTextBlock ocrTextBlock = Mockito.mock(OcrTextBlock.class);
        mOcrBlockView.mBlockHandlerSet.add(ocrTextBlock);
        assertEquals(1, mOcrBlockView.mBlockHandlerSet.size());

        mOcrBlockView.blockDetection();
        OcrTextBlock ocrTextBlock2 = Mockito.mock(OcrTextBlock.class);
        mOcrBlockView.addBlockView(ocrTextBlock2);
        assertEquals(1, mOcrBlockView.mBlockHandlerSet.size());
    }
}
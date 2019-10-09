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

import com.google.android.gms.vision.text.Element;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.camera.views.DetectedBlock;

import static de.telekom.smartcredentials.camera.ocr.utils.RectComparator.isTextInBounds;

public class OcrTextBlock extends DetectedBlock<TextBlock> {

    private final TextBlock mText;

    OcrTextBlock(OcrBlockView overlay, TextBlock text) {
        super(overlay);
        mText = text;
    }

    @Override
    public TextBlock getBlockBetween(float left, float top, float right, float bottom) {
        TextBlock text = mText;
        if (text == null) {
            return null;
        }
        if (isTextInBounds(text, left, top, right, bottom, this))
            return mText;

        synchronized (mText) {
            List<Line> textLines = (List<Line>) text.getComponents();
            List<Line> linesToRemove = new ArrayList<>();
            if (!textLines.isEmpty()) {
                for (Line line : textLines) {
                    if (!isTextInBounds(line, left, top, right, bottom, this)) {
                        List<Element> lineElements = (List<Element>) line.getComponents();
                        List<Element> elementsToRemove = new ArrayList<>();
                        if (!lineElements.isEmpty()) {
                            for (Element element : lineElements) {
                                if (!isTextInBounds(element, left, top, right, bottom, this)) {
                                    elementsToRemove.add(element);
                                }
                            }
                            lineElements.removeAll(elementsToRemove);
                        }
                        if (lineElements.isEmpty()) {
                            linesToRemove.add(line);
                        }
                    }
                }
                textLines.removeAll(linesToRemove);
            }
            return mText;
        }
    }
}

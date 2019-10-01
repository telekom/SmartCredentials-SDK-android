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

import android.graphics.Point;
import android.os.AsyncTask;

import com.google.android.gms.vision.text.Element;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.TextBlock;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.telekom.smartcredentials.camera.views.presenters.WeakRefClassResolver;

public class DetectTextAsyncTask extends AsyncTask<Void, Void, List<String>> {

    private final OcrDetectorProcessor.OnDetectedTextListener mOnDetectedTextListener;
    private final WeakReference<OcrBlockView> mGraphicOverlay;
    private final float mLeft;
    private final float mTop;
    private final float mRight;
    private final float mBottom;

    DetectTextAsyncTask(OcrDetectorProcessor.OnDetectedTextListener onDetectedTextListener, OcrBlockView ocrBlockView, float left, float top, float right, float bottom) {
        mOnDetectedTextListener = onDetectedTextListener;
        mGraphicOverlay = new WeakReference<>(ocrBlockView);
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    @Override
    protected List<String> doInBackground(Void... ocrTextBlocks) {
        if (mOnDetectedTextListener == null) {
            return new ArrayList<>();
        }
        return filterDetectedTexts();
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        if (mOnDetectedTextListener != null) {
            mOnDetectedTextListener.onDetected(strings);
        }
    }

    private List<String> filterDetectedTexts() {
        List<String> detectedTexts = new ArrayList<>();
        new WeakRefClassResolver<OcrBlockView>() {
            @Override
            public void onWeakRefResolved(OcrBlockView ref) {
                List<TextBlock> textBlocks = ref.getBlocksAtLocation(mLeft, mTop, mRight, mBottom);

                List<TextBlockModel> textBlockModels = getDetectedTextBlockModels(textBlocks);
                orderByPosition(textBlockModels);

                for (TextBlockModel textBlockModel : textBlockModels) {
                    detectedTexts.add(textBlockModel.getText());
                }
            }
        }.execute(mGraphicOverlay);
        return detectedTexts;
    }

    private void orderByPosition(List<TextBlockModel> detectedGraphics) {
        Collections.sort(detectedGraphics, (someModel, t1) -> {
            int result = Double.compare(someModel.getPoint().y, t1.getPoint().y);
            if (result == 0) {
                result = Double.compare(someModel.getPoint().x, t1.getPoint().x);
            }
            return result;
        });
    }

    private List<TextBlockModel> getDetectedTextBlockModels(List<TextBlock> textBlocks) {
        List<TextBlockModel> detectedGraphics = new ArrayList<>();

        for (TextBlock textBlock : textBlocks) {
            if (textBlock == null || textBlock.getValue() == null) {
                continue;
            }

            Point graphicLeftTopPoint = new Point(textBlock.getBoundingBox().left, textBlock.getBoundingBox().top);
            String graphicText = getTextDetectedInLines((List<Line>) textBlock.getComponents());

            detectedGraphics.add(new TextBlockModel(graphicLeftTopPoint, graphicText));
        }
        return detectedGraphics;
    }

    private String getTextDetectedInLines(List<Line> lines) {
        String detectedText = "";
        for (Line line : lines) {
            if (!detectedText.isEmpty()) {
                detectedText = detectedText.concat("\n");
            }
            detectedText = detectedText.concat(getTextDetectedInElements((List<Element>) line.getComponents()));
        }
        return detectedText;
    }

    private String getTextDetectedInElements(List<Element> elements) {
        String detectedLine = "";
        for (Element element : elements) {
            if (!detectedLine.isEmpty()) {
                detectedLine = detectedLine.concat(" ");
            }
            detectedLine = detectedLine.concat(element.getValue());
        }
        return detectedLine;
    }

    private class TextBlockModel {
        private final String mText;
        private final Point mPoint;

        TextBlockModel(Point point, String text) {
            mPoint = point;
            mText = text;
        }

        Point getPoint() {
            return mPoint;
        }

        String getText() {
            return mText;
        }
    }
}

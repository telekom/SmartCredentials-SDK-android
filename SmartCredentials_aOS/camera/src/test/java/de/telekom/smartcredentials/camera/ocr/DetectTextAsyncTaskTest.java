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

import android.graphics.Rect;

import com.google.android.gms.vision.text.Element;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.TextBlock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class DetectTextAsyncTaskTest {

    private static final int LEFT = 100;
    private static final int TOP = 100;
    private static final int RIGHT = 900;
    private static final int BOTTOM = 900;

    private DetectTextAsyncTask mDetectTextAsyncTask;
    private OcrDetectorProcessor.OnDetectedTextListener mOnDetectedTextListener;
    private OcrBlockView mOcrBlockView;

    @Before
    public void setUp() {
        mOcrBlockView = Mockito.mock(OcrBlockView.class);
        mOnDetectedTextListener = Mockito.mock(OcrDetectorProcessor.OnDetectedTextListener.class);
    }

    @Test
    public void detectTextAsyncTaskFiltersElementsValues() {
        String finalValue = "some final value";
        String finalLineValue = finalValue + " for line";
        String finalTextBlockValue = finalLineValue + " for text block";

        List<TextBlock> textBlocks = getTextBlocksWithElements(finalValue, finalLineValue, finalTextBlockValue);
        when(mOcrBlockView.getBlocksAtLocation(LEFT, TOP, RIGHT, BOTTOM)).thenReturn(textBlocks);

        mDetectTextAsyncTask = new DetectTextAsyncTask(mOnDetectedTextListener, mOcrBlockView, LEFT, TOP, RIGHT, BOTTOM);

        List<String> detected = mDetectTextAsyncTask.doInBackground();
        assertFalse(detected.isEmpty());
        assertEquals(detected.size(), 1);
        assertEquals(detected.get(0), finalValue);
    }

    @Test
    public void detectTextAsyncTaskReturnsConcatenatedTextsFromTwoLines() {
        String finalValue = "some final value";
        String finalLineValue = finalValue + " for line";
        String finalTextBlockValue = finalLineValue + " for text block";

        List<TextBlock> textBlocks = getTextBlocksWithTwoLineValues(finalValue, finalTextBlockValue);
        when(mOcrBlockView.getBlocksAtLocation(LEFT, TOP, RIGHT, BOTTOM)).thenReturn(textBlocks);

        mDetectTextAsyncTask = new DetectTextAsyncTask(mOnDetectedTextListener, mOcrBlockView,
                LEFT, TOP, RIGHT, BOTTOM);

        List<String> detected = mDetectTextAsyncTask.doInBackground();
        assertFalse(detected.isEmpty());
        assertEquals(detected.size(), 1);
        assertEquals(detected.get(0), finalValue + "\n" + finalValue + "2");
    }

    private List<TextBlock> getTextBlocksWithElements(String finalValue, String finalLineValue, String finalTextBlockValue) {
        List<TextBlock> blocks = new ArrayList<>();
        TextBlock textBlock = Mockito.mock(TextBlock.class);
        when(textBlock.getBoundingBox()).thenReturn(new Rect(LEFT + 20, TOP + 20,
                RIGHT - 20, BOTTOM - 20));
        when(textBlock.getValue()).thenReturn(finalTextBlockValue);

        List lines = getLinesWithElements(finalValue, finalLineValue);
        when(textBlock.getComponents()).thenReturn(lines);

        blocks.add(textBlock);
        return blocks;
    }

    @Test
    public void detectTextAsyncTaskReturnsConcatenatedTextsFromTwoTextBlocks() {
        String finalValue = "some final value";
        String finalLineValue = finalValue + " for line";
        String finalTextBlockValue = finalLineValue + " for text block";

        List<TextBlock> textBlocks = getTwoTextBlocks(finalValue, finalTextBlockValue);
        when(mOcrBlockView.getBlocksAtLocation(LEFT, TOP, RIGHT, BOTTOM)).thenReturn(textBlocks);

        mDetectTextAsyncTask = new DetectTextAsyncTask(mOnDetectedTextListener, mOcrBlockView, LEFT, TOP, RIGHT, BOTTOM);

        List<String> detected = mDetectTextAsyncTask.doInBackground();
        assertFalse(detected.isEmpty());
        assertEquals(detected.size(), 2);
        assertEquals(detected.get(0), finalValue);
        assertEquals(detected.get(1), finalValue + "2");
    }

    @Test
    public void detectTextAsyncTaskReturnsEmptyValueIfElementsNotFound() {
        String finalValue = "some final value";
        String finalLineValue = finalValue + " for line";
        String finalTextBlockValue = finalLineValue + " for text block";

        List<TextBlock> textBlocks = getTextBlocksWithoutElements(finalLineValue, finalTextBlockValue);
        when(mOcrBlockView.getBlocksAtLocation(LEFT, TOP, RIGHT, BOTTOM)).thenReturn(textBlocks);

        mDetectTextAsyncTask = new DetectTextAsyncTask(mOnDetectedTextListener, mOcrBlockView,
                LEFT, TOP, RIGHT, BOTTOM);

        List<String> detected = mDetectTextAsyncTask.doInBackground();
        assertFalse(detected.isEmpty());
        assertEquals(detected.size(), 1);
        assertEquals(detected.get(0), "");
    }

    private List getLinesWithElements(String finalValue, String finalLineValue) {
        List lines = new ArrayList<>();
        Line line = Mockito.mock(Line.class);
        when(line.getBoundingBox()).thenReturn(new Rect(LEFT + 100, TOP + 100,
                RIGHT - 100, BOTTOM - 100));
        when(line.getValue()).thenReturn(finalLineValue);

        List elements = getElements(finalValue);
        when(line.getComponents()).thenReturn(elements);

        lines.add(line);
        return lines;
    }

    private List getElements(String finalValue) {
        List elements = new ArrayList<>();
        Element element = Mockito.mock(Element.class);
        when(element.getBoundingBox()).thenReturn(new Rect(LEFT + 200, TOP + 200,
                RIGHT - 200, BOTTOM - 200));
        when(element.getValue()).thenReturn(finalValue);
        elements.add(element);
        return elements;
    }

    private List<TextBlock> getTextBlocksWithoutElements(String finalLineValue, String finalTextBlockValue) {
        List<TextBlock> blocks = new ArrayList<>();
        TextBlock textBlock = Mockito.mock(TextBlock.class);
        when(textBlock.getBoundingBox()).thenReturn(new Rect(LEFT + 20, TOP + 20,
                RIGHT - 20, BOTTOM - 20));
        when(textBlock.getValue()).thenReturn(finalTextBlockValue);
        List lines = getLinesWithoutElements(finalLineValue);
        when(textBlock.getComponents()).thenReturn(lines);
        blocks.add(textBlock);
        return blocks;
    }

    private List getLinesWithoutElements(String finalLineValue) {
        List lines = new ArrayList<>();
        Line line = Mockito.mock(Line.class);
        when(line.getBoundingBox()).thenReturn(new Rect(LEFT + 100, TOP + 100,
                RIGHT - 100, BOTTOM - 100));
        when(line.getValue()).thenReturn(finalLineValue);
        when(line.getComponents()).thenReturn(new ArrayList<>());
        lines.add(line);
        return lines;
    }

    private List<TextBlock> getTextBlocksWithTwoLineValues(String finalValue, String finalTextBlockValue) {
        List<TextBlock> blocks = new ArrayList<>();
        TextBlock textBlock = Mockito.mock(TextBlock.class);
        when(textBlock.getBoundingBox()).thenReturn(new Rect(LEFT + 20, TOP + 20,
                RIGHT - 20, BOTTOM - 20));
        when(textBlock.getValue()).thenReturn(finalTextBlockValue);
        List lines = getTwoLines(finalValue);
        when(textBlock.getComponents()).thenReturn(lines);
        blocks.add(textBlock);
        return blocks;
    }

    private List getTwoLines(String finalValue) {
        List lines = new ArrayList<>();
        Line line1 = Mockito.mock(Line.class);
        when(line1.getBoundingBox()).thenReturn(new Rect(LEFT + 100, TOP + 100,
                RIGHT - 100, BOTTOM - 100));
        when(line1.getValue()).thenReturn("");
        List elements = getElements(finalValue);
        when(line1.getComponents()).thenReturn(elements);
        lines.add(line1);
        Line line2 = Mockito.mock(Line.class);
        when(line2.getBoundingBox()).thenReturn(new Rect(LEFT + 100, TOP + 100,
                RIGHT - 100, BOTTOM - 100));
        when(line2.getValue()).thenReturn("");
        List elements2 = getElements(finalValue + "2");
        when(line2.getComponents()).thenReturn(elements2);
        lines.add(line2);
        return lines;
    }

    private List<TextBlock> getTwoTextBlocks(String finalValue, String finalTextBlockValue) {
        List<TextBlock> blocks = new ArrayList<>();
        TextBlock textBlock1 = Mockito.mock(TextBlock.class);
        when(textBlock1.getBoundingBox()).thenReturn(new Rect(LEFT + 20, TOP + 20,
                RIGHT - 20, BOTTOM - 20));
        when(textBlock1.getValue()).thenReturn(finalTextBlockValue);
        List lines = getLinesWithElements(finalValue, finalTextBlockValue);
        when(textBlock1.getComponents()).thenReturn(lines);
        blocks.add(textBlock1);
        TextBlock textBlock2 = Mockito.mock(TextBlock.class);
        when(textBlock2.getBoundingBox()).thenReturn(new Rect(LEFT + 30, TOP + 20,
                RIGHT - 20, BOTTOM - 20));
        when(textBlock2.getValue()).thenReturn(finalTextBlockValue);
        List lines2 = getLinesWithElements(finalValue + "2", finalTextBlockValue);
        when(textBlock2.getComponents()).thenReturn(lines2);
        blocks.add(textBlock2);
        return blocks;
    }
}
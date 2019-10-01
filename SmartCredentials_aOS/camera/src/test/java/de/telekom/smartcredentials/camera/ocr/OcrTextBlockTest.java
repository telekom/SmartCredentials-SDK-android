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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.camera.ocr.utils.RectComparator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RectComparator.class)
public class OcrTextBlockTest {

    private static final int LEFT = 100;
    private static final int TOP = 100;
    private static final int RIGHT = 900;
    private static final int BOTTOM = 900;
    private TextBlock mText;
    private OcrTextBlock mOcrTextBlock;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(RectComparator.class);
        mText = Mockito.mock(TextBlock.class);
        mOcrTextBlock = new OcrTextBlock(Mockito.mock(OcrBlockView.class), mText);
    }

    @Test
    public void getTextBlockBetweenReturnsUnmodifiedTextBlockWhenTheWholeTextBlockIsInBounds() {
        when(RectComparator.isTextInBounds(mText, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(true);

        TextBlock textBlock = mOcrTextBlock.getBlockBetween(LEFT, TOP, RIGHT, BOTTOM);
        assertEquals(textBlock, mText);
    }

    @Test
    public void getTextBlockBetweenReturnsUnmodifiedTextBlockIfAllLinesInTextBlockAreInBounds() {
        when(RectComparator.isTextInBounds(mText, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(false);
        List textLines = new ArrayList<>();
        Line line = Mockito.mock(Line.class);
        textLines.add(line);
        when(RectComparator.isTextInBounds(line, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(true);
        Line line2 = Mockito.mock(Line.class);
        textLines.add(line2);
        when(RectComparator.isTextInBounds(line2, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(true);
        when(mText.getComponents()).thenReturn(textLines);

        TextBlock textBlock = mOcrTextBlock.getBlockBetween(LEFT, TOP, RIGHT, BOTTOM);
        assertEquals(textBlock, mText);
        assertEquals(mText.getComponents().size(), 2);
    }

    @Test
    public void getTextBlockBetweenChangesTextBlockAndRemovesLinesThatAreNotInBounds() {
        when(RectComparator.isTextInBounds(mText, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(false);
        List textLines = new ArrayList<>();
        Line line = Mockito.mock(Line.class);
        textLines.add(line);
        when(RectComparator.isTextInBounds(line, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(true);
        Line line2 = Mockito.mock(Line.class);
        textLines.add(line2);
        when(RectComparator.isTextInBounds(line2, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(false);
        when(mText.getComponents()).thenReturn(textLines);

        assertEquals(mText.getComponents().size(), 2);
        TextBlock textBlock = mOcrTextBlock.getBlockBetween(LEFT, TOP, RIGHT, BOTTOM);
        assertEquals(textBlock, mText);
        assertEquals(mText.getComponents().size(), 1);
    }

    @Test
    public void getTextBlockBetweenChangesTextBlockAndReturnsTextBlockWithLineAndTwoElementsInLine() {
        when(RectComparator.isTextInBounds(mText, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(false);
        List textLines = new ArrayList<>();
        Line line = Mockito.mock(Line.class);

        List elements = new ArrayList();
        Element element = Mockito.mock(Element.class);
        when(RectComparator.isTextInBounds(element, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(true);
        elements.add(element);
        Element element2 = Mockito.mock(Element.class);
        when(RectComparator.isTextInBounds(element2, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(true);
        elements.add(element2);

        when(line.getComponents()).thenReturn(elements);
        when(RectComparator.isTextInBounds(line, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(false);

        textLines.add(line);
        when(mText.getComponents()).thenReturn(textLines);

        TextBlock textBlock = mOcrTextBlock.getBlockBetween(LEFT, TOP, RIGHT, BOTTOM);
        assertEquals(textBlock, mText);
        assertEquals(mText.getComponents().size(), 1);
        assertNotNull(mText.getComponents().get(0));
        assertEquals(((Line) mText.getComponents().get(0)).getComponents().size(), 2);
    }

    @Test
    public void getTextBlockBetweenChangesTextBlockAndReturnsTextBlockWithLineAndOneElementInLineIfOneIsNotBetweenBounds() {
        when(RectComparator.isTextInBounds(mText, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(false);
        List textLines = new ArrayList<>();
        Line line = Mockito.mock(Line.class);

        List elements = new ArrayList();
        Element element = Mockito.mock(Element.class);
        when(RectComparator.isTextInBounds(element, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(true);
        elements.add(element);
        Element element2 = Mockito.mock(Element.class);
        when(RectComparator.isTextInBounds(element2, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(false);
        elements.add(element2);

        when(line.getComponents()).thenReturn(elements);
        when(RectComparator.isTextInBounds(line, LEFT, TOP, RIGHT, BOTTOM, mOcrTextBlock)).thenReturn(false);

        textLines.add(line);
        when(mText.getComponents()).thenReturn(textLines);

        TextBlock textBlock = mOcrTextBlock.getBlockBetween(LEFT, TOP, RIGHT, BOTTOM);
        assertEquals(textBlock, mText);
        assertEquals(mText.getComponents().size(), 1);
        assertNotNull(mText.getComponents().get(0));
        assertEquals(((Line) mText.getComponents().get(0)).getComponents().size(), 1);
    }

}
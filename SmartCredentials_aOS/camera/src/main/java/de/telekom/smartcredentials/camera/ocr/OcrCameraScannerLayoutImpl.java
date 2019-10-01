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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.google.android.gms.vision.Detector;

import java.io.IOException;
import java.nio.ByteBuffer;

import de.telekom.smartcredentials.camera.R;
import de.telekom.smartcredentials.camera.ocr.di.OcrObjectCreator;
import de.telekom.smartcredentials.camera.ocr.presenters.OcrCameraScannerPresenter;
import de.telekom.smartcredentials.camera.ocr.presenters.OcrCameraScannerView;
import de.telekom.smartcredentials.camera.views.CameraScannerLayoutImpl;
import de.telekom.smartcredentials.camera.views.DetectedBlockView;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

public class OcrCameraScannerLayoutImpl extends CameraScannerLayoutImpl implements OcrCameraScannerView, OcrCameraScannerLayout {

    OcrCameraScannerPresenter mOcrCameraScannerPresenter;
    Detector mDetector;
    OcrDetectorProcessor mProcessor;
    private static final String TAG = OcrCameraScannerLayoutImpl.class.getSimpleName();
    private OnBitmapLoadedCallback mOnBitmapLoadedCallback;
    private TextParser mTextParser;
    private OcrBlockView mOcrBlockView;

    public static OcrCameraScannerLayoutImpl getNewInstance(Context context, ScannerPluginCallback<ScannerPluginUnavailable> pluginCallback) {
        return new OcrCameraScannerLayoutImpl(context, pluginCallback);
    }

    public OcrCameraScannerLayoutImpl(Context context) {
        super(context, null);
    }

    public OcrCameraScannerLayoutImpl(Context context, ScannerPluginCallback<ScannerPluginUnavailable> scannerPluginCallback) {
        super(context, scannerPluginCallback);
    }

    @Override
    public int getCustomLayoutResource() {
        return R.layout.sc_layout_ocr_scanner;
    }

    @Override
    public void setupCustomDetectedBlockView() {
        mOcrBlockView = mCustomDetectedBlockView.findViewById(R.id.sc_view_ocr_block);
    }

    @Override
    public void assembleView() {
        OcrObjectCreator objectCreator = new OcrObjectCreator(mContext, mOcrBlockView);
        mOcrCameraScannerPresenter = objectCreator.provideOcrScannerPresenter();
        mDetector = objectCreator.provideDetector();
        mProcessor = objectCreator.provideProcessor();
    }

    @Override
    public void informPresenterViewIsReady() {
        mOcrCameraScannerPresenter.viewReady(this);
    }

    @Override
    public OcrCameraScannerPresenter getPresenter() {
        return mOcrCameraScannerPresenter;
    }

    @Override
    public Detector getDetector() {
        return mDetector;
    }

    @Override
    public Detector.Processor getProcessor() {
        return mProcessor;
    }

    @Override
    protected DetectedBlockView getDetectedBlockView() {
        return mOcrBlockView;
    }

    @Override
    public void processDetectedTextBlocks(OcrDetectorProcessor.OnDetectedTextListener onDetectedTextListener) {
        mProcessor.filterDetectedTexts(onDetectedTextListener, getRect().left, getRect().top, getRect().right, getRect().bottom);
    }

    @Override
    public void detect() {
        detect(getRect());
    }

    @Override
    public void detect(TextParser textParser, OnBitmapLoadedCallback onBitmapLoadCallback) {
        mTextParser = textParser;
        mOnBitmapLoadedCallback = onBitmapLoadCallback;
        detect();
    }

    @Override
    public void detect(OnBitmapLoadedCallback onBitmapLoadCallback) {
        detect(null, onBitmapLoadCallback);
    }

    @Override
    public void detect(TextParser textParser) {
        detect(textParser, (OnBitmapLoadedCallback) null);
    }

    @Override
    public void detect(TextParser textParser, OnBitmapLoadedCallback onBitmapLoadCallback, Rect rect) {
        mTextParser = textParser;
        mOnBitmapLoadedCallback = onBitmapLoadCallback;
        detect(rect);
    }

    @Override
    public void detect(OnBitmapLoadedCallback onBitmapLoadCallback, Rect rect) {
        detect(null, onBitmapLoadCallback, rect);
    }

    @Override
    public void detect(TextParser textParser, Rect rect) {
        detect(textParser, null, rect);
    }

    @Override
    public void loadPicture(byte[] bytes) {
        if (bytes == null) {
            return;
        }

        Bitmap bitmap = computeBitmap(bytes);
        if (mOnBitmapLoadedCallback != null) {
            mOnBitmapLoadedCallback.onLoaded(bitmap);
        }
    }

    @Override
    public void takeCurrentPicture() {
        if (mCameraSource != null) {
            mCameraSource.takePicture(
                    () -> getPresenter().onShutter(),
                    bytes -> getPresenter().onPictureTaken(bytes)
            );
        }
    }

    public void detect(Rect rect) {
        setRect(rect);
        mOcrCameraScannerPresenter.prepareDetection(mTextParser, mOnBitmapLoadedCallback != null);
    }

    private Bitmap computeBitmap(byte[] bytes) {
        Matrix matrix = new Matrix();
        if (mPreview.isPortraitMode()) {
            matrix.postRotate(90);
        }

        Bitmap bitmap;
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            try {
                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(byteBuffer));
            } catch (IOException e) {
                ApiLoggerResolver.logError(TAG, e.getMessage());
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        } else {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}

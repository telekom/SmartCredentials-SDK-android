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

package de.telekom.smartcredentials.documentscanner.utils;

import com.microblink.entities.recognizers.blinkid.imageoptions.FaceImageOptions;
import com.microblink.entities.recognizers.blinkid.imageoptions.FullDocumentImageOptions;
import com.microblink.entities.recognizers.blinkid.imageoptions.encode.EncodeFaceImageOptions;
import com.microblink.entities.recognizers.blinkid.imageoptions.encode.EncodeFullDocumentImagesOptions;

/**
 * Created by Lucian Iacob on January 03, 2019.
 */
public class MockRecognizer implements EncodeFullDocumentImagesOptions, FullDocumentImageOptions,
        EncodeFaceImageOptions, FaceImageOptions {

    private boolean mReturnFaceImage = false;
    private boolean mEncodeFaceImage = false;
    private boolean mEncodeFullDocumentImage = false;
    private boolean mFullDocumentImage = false;

    @Override
    public void setReturnFaceImage(boolean returnFaceImage) {
        mReturnFaceImage = returnFaceImage;
    }

    @Override
    public boolean shouldReturnFaceImage() {
        return mReturnFaceImage;
    }

    @Override
    public void setEncodeFaceImage(boolean encodeFaceImage) {
        mEncodeFaceImage = encodeFaceImage;
    }

    @Override
    public boolean shouldEncodeFaceImage() {
        return mEncodeFaceImage;
    }

    @Override
    public void setEncodeFullDocumentImage(boolean encodeFullDocumentImage) {
        mEncodeFullDocumentImage = encodeFullDocumentImage;
    }

    @Override
    public boolean shouldEncodeFullDocumentImage() {
        return mEncodeFullDocumentImage;
    }

    @Override
    public void setReturnFullDocumentImage(boolean fullDocumentImage) {
        mFullDocumentImage = fullDocumentImage;
    }

    @Override
    public boolean shouldReturnFullDocumentImage() {
        return mFullDocumentImage;
    }
}

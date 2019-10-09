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

/*
 * Created by Lucian Iacob on 6/29/18 2:03 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public abstract class CombinedResult extends DocumentScannerResult {

    private long    mDigitalSignatureVersion;
    private boolean mScanningFirstSideDone;
    private boolean mDocumentDataMatch;
    private byte[]  mEncodedFrontFullDocumentImage;
    private byte[]  mEncodedBackFullDocumentImage;
    private byte[]  mEncodedFaceImage;
    private byte[]  mDigitalSignature;

    protected CombinedResult(Recognizer.Result.State scannerResultState) {
        super(ModelConverter.convertResultState(scannerResultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "CombinedResult{\n" +
                "mDigitalSignatureVersion=" + mDigitalSignatureVersion +
                ",\nmScanningFirstSideDone=" + mScanningFirstSideDone +
                ",\nmDocumentDataMatch=" + mDocumentDataMatch +
                '}';
    }

    public byte[] getEncodedBackFullDocumentImage() {
        return mEncodedBackFullDocumentImage;
    }

    public CombinedResult setEncodedBackFullDocumentImage(byte[] encodedBackFullDocumentImage) {
        mEncodedBackFullDocumentImage = encodedBackFullDocumentImage;
        return this;
    }

    public byte[] getEncodedFrontFullDocumentImage() {
        return mEncodedFrontFullDocumentImage;
    }

    public CombinedResult setEncodedFrontFullDocumentImage(byte[] encodedFrontFullDocumentImage) {
        mEncodedFrontFullDocumentImage = encodedFrontFullDocumentImage;
        return this;
    }

    public boolean isScanningFirstSideDone() {
        return mScanningFirstSideDone;
    }

    public CombinedResult setScanningFirstSideDone(boolean scanningFirstSideDone) {
        mScanningFirstSideDone = scanningFirstSideDone;
        return this;
    }

    public boolean isDocumentDataMatch() {
        return mDocumentDataMatch;
    }

    public CombinedResult setDocumentDataMatch(boolean documentDataMatch) {
        mDocumentDataMatch = documentDataMatch;
        return this;
    }

    public long getDigitalSignatureVersion() {
        return mDigitalSignatureVersion;
    }

    public CombinedResult setDigitalSignatureVersion(long digitalSignatureVersion) {
        mDigitalSignatureVersion = digitalSignatureVersion;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public CombinedResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }

    public byte[] getDigitalSignature() {
        return mDigitalSignature;
    }

    public CombinedResult setDigitalSignature(byte[] digitalSignature) {
        mDigitalSignature = digitalSignature;
        return this;
    }
}

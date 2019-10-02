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

package de.telekom.smartcredentials.core.camera;

public enum BarcodeType {

    BARCODE_ALL_FORMATS("All supported formats", 0),
    BARCODE_1D_EAN_13("International Article Number", 32),
    BARCODE_1D_EAN_8("International Article Number", 64),
    BARCODE_1D_UPC_A("Universal Product Code", 512),
    BARCODE_1D_UPC_E("Universal Product Code", 1024),
    BARCODE_1D_CODE_39("Code 39 Barcode", 2),
    BARCODE_1D_CODE_93("Code 93 Barcode", 4),
    BARCODE_1D_CODE_128("Code 128, full-character encoding", 1),
    BARCODE_1D_ITF("Interleaved 2 of 5", 128),
    BARCODE_1D_CODABAR("Codabar", 8),
    BARCODE_2D_QR_CODE("QR code", 256),
    BARCODE_2D_DATA_MATRIX("Data Matrix", 16),
    BARCODE_2D_PDF_417("PDF417", 2048),
    BARCODE_2D_AZTEC("Aztec Code", 4096);

    private final String mDesc;
    private final int mFormat;

    BarcodeType(String desc, int format) {
        mDesc = desc;
        mFormat = format;
    }

    public String getDesc() {
        return mDesc;
    }

    public int getFormat() {
        return mFormat;
    }
}
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
 * Created by Lucian Iacob on 6/29/18 2:09 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.utils;

import com.microblink.entities.Entity;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdRecognizer;
import com.microblink.entities.recognizers.blinkid.idbarcode.IdBarcodeRecognizer;
import com.microblink.entities.recognizers.blinkid.usdl.UsdlCombinedRecognizer;

import de.telekom.smartcredentials.core.model.DocumentScannerResult;
import de.telekom.smartcredentials.documentscanner.model.results.IdBarcodeRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.IdCombinedRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.IdSimpleRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.UsdlCombinedRecognizerResult;


public class ScannerResultConverter {

    private ScannerResultConverter() {
        // required empty constructor
    }

    public static DocumentScannerResult convertToInternalModel(Entity.Result result) {
        DocumentScannerResult documentScannerResult = null;
        if (result instanceof BlinkIdCombinedRecognizer.Result) {
            documentScannerResult = parseIdCombinedResult((BlinkIdCombinedRecognizer.Result) result);
        } else if (result instanceof BlinkIdRecognizer.Result) {
            documentScannerResult = parseIdSimpleResult((BlinkIdRecognizer.Result) result);
        } else if (result instanceof IdBarcodeRecognizer.Result) {
            documentScannerResult = parseIdBarcodeResult((IdBarcodeRecognizer.Result) result);
        } else if (result instanceof UsdlCombinedRecognizer.Result) {
            documentScannerResult = parseUsdlCombinedResult((UsdlCombinedRecognizer.Result) result);
        }
        return documentScannerResult;
    }

    private static DocumentScannerResult parseIdCombinedResult(BlinkIdCombinedRecognizer.Result result) {
        return new IdCombinedRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parseIdSimpleResult(BlinkIdRecognizer.Result result) {
        return new IdSimpleRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parseIdBarcodeResult(IdBarcodeRecognizer.Result result) {
        return new IdBarcodeRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parseUsdlCombinedResult(UsdlCombinedRecognizer.Result result) {
        return new UsdlCombinedRecognizerResult(result.getResultState())
                .setResult(result);
    }

}

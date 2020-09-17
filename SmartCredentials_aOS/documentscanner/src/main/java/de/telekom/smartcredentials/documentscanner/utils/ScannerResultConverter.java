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
import com.microblink.entities.recognizers.blinkbarcode.usdl.UsdlRecognizer;
import com.microblink.entities.recognizers.blinkid.documentface.DocumentFaceRecognizer;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdRecognizer;
import com.microblink.entities.recognizers.blinkid.idbarcode.IdBarcodeRecognizer;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdRecognizer;
import com.microblink.entities.recognizers.blinkid.passport.PassportRecognizer;
import com.microblink.entities.recognizers.blinkid.usdl.UsdlCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.visa.VisaRecognizer;

import de.telekom.smartcredentials.core.model.DocumentScannerResult;
import de.telekom.smartcredentials.documentscanner.model.results.DocumentFaceRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.IdBarcodeRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.IdCombinedRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.IdSimpleRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.MrtdCombinedRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.MrtdSimpleRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.PassportRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.UsdlCombinedRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.UsdlSimpleRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.VisaRecognizerResult;


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
        } else if (result instanceof DocumentFaceRecognizer.Result) {
            documentScannerResult = parseDocumentFaceResult((DocumentFaceRecognizer.Result) result);
        } else if (result instanceof IdBarcodeRecognizer.Result) {
            documentScannerResult = parseIdBarcodeResult((IdBarcodeRecognizer.Result) result);
        } else if (result instanceof MrtdCombinedRecognizer.Result) {
            documentScannerResult = parseMrtdCombinedResult((MrtdCombinedRecognizer.Result) result);
        } else if (result instanceof MrtdRecognizer.Result) {
            documentScannerResult = parseMrtdSimpleResult((MrtdRecognizer.Result) result);
        } else if (result instanceof PassportRecognizer.Result) {
            documentScannerResult = parsePassportResult((PassportRecognizer.Result) result);
        } else if (result instanceof UsdlCombinedRecognizer.Result) {
            documentScannerResult = parseUsdlCombinedResult((UsdlCombinedRecognizer.Result) result);
        } else if (result instanceof UsdlRecognizer.Result) {
            documentScannerResult = parseUsdlSimpleResult((UsdlRecognizer.Result) result);
        } else if (result instanceof VisaRecognizer.Result) {
            documentScannerResult = parseVisaResult((VisaRecognizer.Result) result);
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

    private static DocumentScannerResult parseDocumentFaceResult(DocumentFaceRecognizer.Result result) {
        return new DocumentFaceRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parseIdBarcodeResult(IdBarcodeRecognizer.Result result) {
        return new IdBarcodeRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parseMrtdCombinedResult(MrtdCombinedRecognizer.Result result) {
        return new MrtdCombinedRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parseMrtdSimpleResult(MrtdRecognizer.Result result) {
        return new MrtdSimpleRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parsePassportResult(PassportRecognizer.Result result) {
        return new PassportRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parseUsdlCombinedResult(UsdlCombinedRecognizer.Result result) {
        return new UsdlCombinedRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parseUsdlSimpleResult(UsdlRecognizer.Result result) {
        return new UsdlSimpleRecognizerResult(result.getResultState())
                .setResult(result);
    }

    private static DocumentScannerResult parseVisaResult(VisaRecognizer.Result result) {
        return new VisaRecognizerResult(result.getResultState())
                .setResult(result);
    }
}

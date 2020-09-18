package de.telekom.scdocumentscannerdemo.recognizer;

import android.os.Bundle;

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

import de.telekom.scdocumentscannerdemo.result.ImageUtils;
import de.telekom.scdocumentscannerdemo.result.ResultActivity;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
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

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public class RecognizerFactory {

    public ScannerRecognizer getRecognizer(RecognizerType recognizerType) {
        switch (recognizerType) {
            case ID_SIMPLE_RECOGNIZER:
                return ScannerRecognizer.ID_SIMPLE_RECOGNIZER;
            case DOCUMENT_FACE_RECOGNIZER:
                return ScannerRecognizer.DOCUMENT_FACE_RECOGNIZER;
            case ID_BARCODE_RECOGNIZER:
                return ScannerRecognizer.ID_BARCODE_RECOGNIZER;
            case MRTD_COMBINED_RECOGNIZER:
                return ScannerRecognizer.MRTD_COMBINED_RECOGNIZER;
            case MRTD_SIMPLE_RECOGNIZER:
                return ScannerRecognizer.MRTD_SIMPLE_RECOGNIZER;
            case USDL_COMBINED_RECOGNIZER:
                return ScannerRecognizer.USDL_COMBINED_RECOGNIZER;
            case USDL_SIMPLE_RECOGNIZER:
                return ScannerRecognizer.USDL_SIMPLE_RECOGNIZER;
            case VISA_RECOGNIZER:
                return ScannerRecognizer.VISA_RECOGNIZER;
            default:
                return ScannerRecognizer.ID_COMBINED_RECOGNIZER;
        }
    }

    public Bundle getExtrasBundle(DocumentScannerResult result) {
        Bundle extraBundle = new Bundle();
        if (result instanceof IdCombinedRecognizerResult) {
            BlinkIdCombinedRecognizer.Result resultData = ((IdCombinedRecognizerResult) result).getResultData();
            if (resultData.getFullName().isEmpty()) {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFirstName() + " " + resultData.getLastName());
            } else {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFullName());
            }
            extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, resultData.getMrzResult().getMrzText());
            extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                    ImageUtils.transformImageToByteArray(resultData.getFullDocumentFrontImage()));
        } else if (result instanceof IdSimpleRecognizerResult) {
            BlinkIdRecognizer.Result resultData = ((IdSimpleRecognizerResult) result).getResultData();
            if (resultData.getFullName().isEmpty()) {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFirstName() + " " + resultData.getLastName());
            } else {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFullName());
            }
            extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, resultData.getMrzResult().getMrzText());
            extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                    ImageUtils.transformImageToByteArray(resultData.getFullDocumentImage()));
        } else if (result instanceof DocumentFaceRecognizerResult) {
            DocumentFaceRecognizer.Result resultData = ((DocumentFaceRecognizerResult) result).getResultData();
            extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                    ImageUtils.transformImageToByteArray(resultData.getFullDocumentImage()));
        } else if (result instanceof IdBarcodeRecognizerResult) {
            IdBarcodeRecognizer.Result resultData = ((IdBarcodeRecognizerResult) result).getResultData();
            if (resultData.getFullName().isEmpty()) {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFirstName() + " " + resultData.getLastName());
            } else {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFullName());
            }
        } else if (result instanceof MrtdCombinedRecognizerResult) {
            MrtdCombinedRecognizer.Result resultData = ((MrtdCombinedRecognizerResult) result).getResultData();
            extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, resultData.getMrzResult().getMrzText());
            extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                    ImageUtils.transformImageToByteArray(resultData.getFullDocumentFrontImage()));
        } else if (result instanceof MrtdSimpleRecognizerResult) {
            MrtdRecognizer.Result resultData = ((MrtdSimpleRecognizerResult) result).getResultData();
            extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, resultData.getMrzResult().getMrzText());
            extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                    ImageUtils.transformImageToByteArray(resultData.getFullDocumentImage()));
        } else if (result instanceof PassportRecognizerResult) {
            PassportRecognizer.Result resultData = ((PassportRecognizerResult) result).getResultData();
            extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, resultData.getMrzResult().getMrzText());
            extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                    ImageUtils.transformImageToByteArray(resultData.getFullDocumentImage()));
        } else if (result instanceof UsdlCombinedRecognizerResult) {
            UsdlCombinedRecognizer.Result resultData = ((UsdlCombinedRecognizerResult) result).getResultData();
            if (resultData.getFullName().isEmpty()) {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFirstName() + " " + resultData.getLastName());
            } else {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFullName());
            }
            extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                    ImageUtils.transformImageToByteArray(resultData.getFullDocumentImage()));
        } else if (result instanceof UsdlSimpleRecognizerResult) {
            UsdlRecognizer.Result resultData = ((UsdlSimpleRecognizerResult) result).getResultData();
            if (resultData.getFullName().isEmpty()) {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFirstName() + " " + resultData.getLastName());
            } else {
                extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, resultData.getFullName());
            }
        } else if (result instanceof VisaRecognizerResult) {
            VisaRecognizer.Result resultData = ((VisaRecognizerResult) result).getResultData();
            extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, resultData.getMrzResult().getMrzText());
            extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                    ImageUtils.transformImageToByteArray(resultData.getFullDocumentImage()));
        }
        return extraBundle;
    }
}

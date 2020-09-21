package de.telekom.scdocumentscannerdemo.recognizer;

import android.os.Bundle;

import com.microblink.entities.recognizers.blinkbarcode.usdl.UsdlRecognizer;
import com.microblink.entities.recognizers.blinkid.documentface.DocumentFaceRecognizer;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdRecognizer;
import com.microblink.entities.recognizers.blinkid.idbarcode.IdBarcodeRecognizer;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdRecognizer;
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
import de.telekom.smartcredentials.documentscanner.model.results.UsdlCombinedRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.UsdlSimpleRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.VisaRecognizerResult;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public class RecognizerFactory implements RecognizerData {

    private RecognizerType recognizerType;

    public RecognizerFactory(RecognizerType recognizerType) {
        this.recognizerType = recognizerType;
    }

    public void setRecognizer(RecognizerType recognizerType) {
        this.recognizerType = recognizerType;
    }

    public ScannerRecognizer getRecognizer() {
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
        switch (recognizerType) {
            case ID_SIMPLE_RECOGNIZER:
                BlinkIdRecognizer.Result idSimpleRecognizerResultData = ((IdSimpleRecognizerResult) result).getResultData();
                if (idSimpleRecognizerResultData.getFullName().isEmpty()) {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, idSimpleRecognizerResultData.getFirstName() +
                            " " + idSimpleRecognizerResultData.getLastName());
                } else {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, idSimpleRecognizerResultData.getFullName());
                }
                extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, idSimpleRecognizerResultData.getMrzResult().getMrzText());
                extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                        ImageUtils.transformImageToByteArray(idSimpleRecognizerResultData.getFullDocumentImage()));
                break;
            case DOCUMENT_FACE_RECOGNIZER:
                DocumentFaceRecognizer.Result documentFaceResultData = ((DocumentFaceRecognizerResult) result).getResultData();
                extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                        ImageUtils.transformImageToByteArray(documentFaceResultData.getFullDocumentImage()));
                break;
            case ID_BARCODE_RECOGNIZER:
                IdBarcodeRecognizer.Result idBarcodeResultData = ((IdBarcodeRecognizerResult) result).getResultData();
                if (idBarcodeResultData.getFullName().isEmpty()) {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, idBarcodeResultData.getFirstName() +
                            " " + idBarcodeResultData.getLastName());
                } else {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, idBarcodeResultData.getFullName());
                }
                break;
            case MRTD_COMBINED_RECOGNIZER:
                MrtdCombinedRecognizer.Result mrtdCombinedResultData = ((MrtdCombinedRecognizerResult) result).getResultData();
                extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, mrtdCombinedResultData.getMrzResult().getMrzText());
                extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                        ImageUtils.transformImageToByteArray(mrtdCombinedResultData.getFullDocumentFrontImage()));
                break;
            case MRTD_SIMPLE_RECOGNIZER:
                MrtdRecognizer.Result mrtdSimpleResultData = ((MrtdSimpleRecognizerResult) result).getResultData();
                extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, mrtdSimpleResultData.getMrzResult().getMrzText());
                extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                        ImageUtils.transformImageToByteArray(mrtdSimpleResultData.getFullDocumentImage()));
                break;
            case USDL_COMBINED_RECOGNIZER:
                UsdlCombinedRecognizer.Result usdlCombinedResultData = ((UsdlCombinedRecognizerResult) result).getResultData();
                if (usdlCombinedResultData.getFullName().isEmpty()) {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, usdlCombinedResultData.getFirstName() +
                            " " + usdlCombinedResultData.getLastName());
                } else {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, usdlCombinedResultData.getFullName());
                }
                extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                        ImageUtils.transformImageToByteArray(usdlCombinedResultData.getFullDocumentImage()));
                break;
            case USDL_SIMPLE_RECOGNIZER:
                UsdlRecognizer.Result usdlSimpleResultData = ((UsdlSimpleRecognizerResult) result).getResultData();
                if (usdlSimpleResultData.getFullName().isEmpty()) {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, usdlSimpleResultData.getFirstName() +
                            " " + usdlSimpleResultData.getLastName());
                } else {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, usdlSimpleResultData.getFullName());
                }
                break;
            case VISA_RECOGNIZER:
                VisaRecognizer.Result visaResultData = ((VisaRecognizerResult) result).getResultData();
                extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, visaResultData.getMrzResult().getMrzText());
                extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                        ImageUtils.transformImageToByteArray(visaResultData.getFullDocumentImage()));
                break;
            default:
                BlinkIdCombinedRecognizer.Result idCombinedResultData = ((IdCombinedRecognizerResult) result).getResultData();
                if (idCombinedResultData.getFullName().isEmpty()) {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, idCombinedResultData.getFirstName() +
                            " " + idCombinedResultData.getLastName());
                } else {
                    extraBundle.putString(ResultActivity.EXTRA_RESULT_NAME, idCombinedResultData.getFullName());
                }
                extraBundle.putString(ResultActivity.EXTRA_RESULT_MRZ, idCombinedResultData.getMrzResult().getMrzText());
                extraBundle.putByteArray(ResultActivity.EXTRA_RESULT_IMAGE,
                        ImageUtils.transformImageToByteArray(idCombinedResultData.getFullDocumentFrontImage()));
                break;
        }
        return extraBundle;
    }
}

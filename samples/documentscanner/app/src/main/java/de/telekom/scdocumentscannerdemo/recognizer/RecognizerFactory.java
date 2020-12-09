package de.telekom.scdocumentscannerdemo.recognizer;

import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdRecognizer;
import com.microblink.entities.recognizers.blinkid.idbarcode.IdBarcodeRecognizer;
import com.microblink.entities.recognizers.blinkid.usdl.UsdlCombinedRecognizer;

import de.telekom.scdocumentscannerdemo.repository.LocalRepository;
import de.telekom.scdocumentscannerdemo.repository.Repository;
import de.telekom.scdocumentscannerdemo.result.ImageUtils;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
import de.telekom.smartcredentials.documentscanner.model.results.IdBarcodeRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.IdCombinedRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.IdSimpleRecognizerResult;
import de.telekom.smartcredentials.documentscanner.model.results.UsdlCombinedRecognizerResult;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public class RecognizerFactory implements RecognizerData {

    private RecognizerType recognizerType;
    private Repository repository;

    public RecognizerFactory(RecognizerType recognizerType) {
        this.recognizerType = recognizerType;
        this.repository = new LocalRepository(SmartCredentialsStorageFactory.getStorageApi());
    }

    public void setRecognizer(RecognizerType recognizerType) {
        this.recognizerType = recognizerType;
    }

    public ScannerRecognizer getRecognizer() {
        switch (recognizerType) {
            case ID_SIMPLE_RECOGNIZER:
                return ScannerRecognizer.ID_SIMPLE_RECOGNIZER;
            case ID_BARCODE_RECOGNIZER:
                return ScannerRecognizer.ID_BARCODE_RECOGNIZER;
            case USDL_COMBINED_RECOGNIZER:
                return ScannerRecognizer.USDL_COMBINED_RECOGNIZER;
            default:
                return ScannerRecognizer.ID_COMBINED_RECOGNIZER;
        }
    }

    public void saveScanningResult(DocumentScannerResult result) {
        repository.clearData();
        switch (recognizerType) {
            case ID_SIMPLE_RECOGNIZER:
                BlinkIdRecognizer.Result idSimpleRecognizerResultData = ((IdSimpleRecognizerResult) result).getResultData();
                if (idSimpleRecognizerResultData.getFullName().isEmpty()) {
                    repository.storeName(idSimpleRecognizerResultData.getFirstName() + " " + idSimpleRecognizerResultData.getLastName());
                } else {
                    repository.storeName(idSimpleRecognizerResultData.getFullName());
                }
                repository.storeMrz(idSimpleRecognizerResultData.getMrzResult().getMrzText());
                if (idSimpleRecognizerResultData.getFullDocumentImage() != null) {
                    repository.storeCapture(1, ImageUtils.transformImageToByteArray(idSimpleRecognizerResultData.getFullDocumentImage()));
                }
                break;
            case ID_BARCODE_RECOGNIZER:
                IdBarcodeRecognizer.Result idBarcodeResultData = ((IdBarcodeRecognizerResult) result).getResultData();
                if (idBarcodeResultData.getFullName().isEmpty()) {
                    repository.storeName(idBarcodeResultData.getFirstName() + " " + idBarcodeResultData.getLastName());
                } else {
                    repository.storeName(idBarcodeResultData.getFullName());
                }
                break;
            case USDL_COMBINED_RECOGNIZER:
                UsdlCombinedRecognizer.Result usdlCombinedResultData = ((UsdlCombinedRecognizerResult) result).getResultData();
                if (usdlCombinedResultData.getFullName().isEmpty()) {
                    repository.storeName(usdlCombinedResultData.getFirstName() + " " + usdlCombinedResultData.getLastName());
                } else {
                    repository.storeName(usdlCombinedResultData.getFullName());
                }
                if (usdlCombinedResultData.getFullDocumentImage() != null) {
                    repository.storeCapture(1, ImageUtils.transformImageToByteArray(usdlCombinedResultData.getFullDocumentImage()));
                }
                break;
            default:
                BlinkIdCombinedRecognizer.Result idCombinedResultData = ((IdCombinedRecognizerResult) result).getResultData();
                if (idCombinedResultData.getFullName().isEmpty()) {
                    repository.storeName(idCombinedResultData.getFirstName() + " " + idCombinedResultData.getLastName());
                } else {
                    repository.storeName(idCombinedResultData.getFullName());
                }
                repository.storeMrz(idCombinedResultData.getMrzResult().getMrzText());
                if (idCombinedResultData.getFullDocumentFrontImage() != null) {
                    repository.storeCapture(1, ImageUtils.transformImageToByteArray(idCombinedResultData.getFullDocumentFrontImage()));
                }
                if (idCombinedResultData.getFullDocumentBackImage() != null) {
                    repository.storeCapture(2, ImageUtils.transformImageToByteArray(idCombinedResultData.getFullDocumentBackImage()));
                }
                break;
        }
    }
}

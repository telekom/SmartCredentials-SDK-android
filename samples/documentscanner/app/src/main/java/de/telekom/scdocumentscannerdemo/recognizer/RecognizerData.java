package de.telekom.scdocumentscannerdemo.recognizer;

import android.os.Bundle;

import de.telekom.smartcredentials.core.model.DocumentScannerResult;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;

/**
 * Created by gabriel.blaj@endava.com at 9/21/2020
 */
public interface RecognizerData {

    void setRecognizer(RecognizerType recognizerType);
    ScannerRecognizer getRecognizer();
    Bundle getExtrasBundle(DocumentScannerResult result);
}

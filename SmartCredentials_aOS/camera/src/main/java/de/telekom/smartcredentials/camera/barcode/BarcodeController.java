package de.telekom.smartcredentials.camera.barcode;

import androidx.camera.view.PreviewView;

import de.telekom.smartcredentials.core.camera.CameraScannerLayout;
import de.telekom.smartcredentials.core.camera.ScannerCallback;

public class BarcodeController {

    private final CameraScannerLayout<PreviewView> mCameraScannerLayout;
    private final ScannerCallback mScannerCallback;

    public BarcodeController(CameraScannerLayout<PreviewView> cameraScannerLayout,
                             ScannerCallback scannerCallback) {
        mCameraScannerLayout = cameraScannerLayout;
        mScannerCallback = scannerCallback;
    }
}

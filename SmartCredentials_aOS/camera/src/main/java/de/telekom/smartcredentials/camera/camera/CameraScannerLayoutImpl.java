package de.telekom.smartcredentials.camera.camera;

import android.content.Context;

import androidx.camera.view.PreviewView;

import de.telekom.smartcredentials.core.camera.CameraScannerLayout;
import de.telekom.smartcredentials.core.camera.ScannerCallback;

public abstract class CameraScannerLayoutImpl implements CameraScannerLayout<PreviewView> {

    protected final Context mContext;
    protected final PreviewView mPreviewView;
    protected final ScannerCallback mScannerCallback;

    public CameraScannerLayoutImpl(Context context, ScannerCallback scannerCallback) {
        this.mContext = context;
        this.mPreviewView = new PreviewView(mContext);
        this.mScannerCallback = scannerCallback;
    }

    @Override
    public PreviewView getView() {
        return mPreviewView;
    }
}

package de.telekom.smartcredentials.camera.camera;

import android.content.Context;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import de.telekom.smartcredentials.core.camera.ScannerCallback;

public abstract class CameraScanner {

    protected final UseCaseGroup.Builder mUseCaseGroupBuilder;
    protected final ScannerCallback mCallback;

    public CameraScanner(ScannerCallback callback) {
        mCallback = callback;
        mUseCaseGroupBuilder = new UseCaseGroup.Builder();
    }

    public void startCamera(Context context, PreviewView previewView, LifecycleOwner lifecycleOwner) {
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(context);
        future.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = future.get();
                bindPreview(cameraProvider, previewView, lifecycleOwner);
            } catch (ExecutionException | InterruptedException e) {
                mCallback.onScanFailed(e);
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void bindPreview(ProcessCameraProvider cameraProvider, PreviewView previewView,
                            LifecycleOwner lifecycleOwner) {
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        mUseCaseGroupBuilder.addUseCase(preview);
        addUseCases();
        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, mUseCaseGroupBuilder.build());
    }

    public abstract void addUseCases();
}

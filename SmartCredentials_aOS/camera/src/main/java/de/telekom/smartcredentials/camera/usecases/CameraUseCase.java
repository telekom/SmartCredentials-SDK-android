package de.telekom.smartcredentials.camera.usecases;

import androidx.camera.core.UseCase;

import de.telekom.smartcredentials.core.camera.ScannerCallback;

public interface CameraUseCase {

    UseCase create(ScannerCallback callback);
}

package de.telekom.smartcredentials.camera.usecases;

import android.media.Image;

import androidx.annotation.NonNull;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.UseCase;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import de.telekom.smartcredentials.core.camera.ScannerCallback;

public class OcrImageCapture implements CameraUseCase {

    private ImageCapture imageCapture;
    private ScannerCallback mCallback;


    @Override
    public UseCase create(ScannerCallback callback) {
        imageCapture = new ImageCapture.Builder()
                .build();
        mCallback = callback;
        return imageCapture;
    }

    @ExperimentalGetImage
    public void takePicture() {
        imageCapture.takePicture(Executors.newSingleThreadExecutor(), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy imageProxy) {
                super.onCaptureSuccess(imageProxy);
                TextRecognizer textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                Image imageMedia = imageProxy.getImage();

                if (imageMedia != null) {
                    InputImage inputImage = InputImage.fromMediaImage(imageMedia, imageProxy.getImageInfo().getRotationDegrees());
                    textRecognizer.process(inputImage)
                            .addOnSuccessListener(visionText -> {
                                ArrayList<String> ocrValues = new ArrayList<>();
                                for (Text.TextBlock textBlock : visionText.getTextBlocks()) {
                                    for (Text.Line line : textBlock.getLines()) {
                                        ocrValues.add(line.getText());
                                    }
                                }
                                mCallback.onDetected(ocrValues);
                            })
                            .addOnFailureListener(e -> mCallback.onScanFailed(e));
                }
                imageProxy.close();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                super.onError(exception);
                mCallback.onScanFailed(exception);
            }
        });
    }
}

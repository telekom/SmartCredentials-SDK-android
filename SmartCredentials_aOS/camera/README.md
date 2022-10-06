# Usage of the Smart Credentials Camera API

Smart Credentials Camera Module is used for optical character recognition and reading different type of barcodes.

### Add build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
implementation("de.telekom.smartcredentials:camera:$sc_version")
````

### Modules initialization

The Smart Credentials module initialization is recommended to be made in the application class.

**Core Module**
``` 
SmartCredentialsConfiguration coreConfig = new SmartCredentialsConfiguration.Builder(context, userId)
                .setLogger(new Logger())
                .setAppAlias(alias)
                .build();
CoreApi coreApi = SmartCredentialsCoreFactory.initialize(coreConfig);
````

**Camera module**
``` 
SmartCredentialsCameraFactory.initSmartCredentialsCameraModule(coreApi);
````

### OCR reading 

**Step 1:** Create a scanner callback. 

```
ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onDetected(List<String> detectedValues) {
            // handle the results of the scanner
        }

        @Override
        public void onScanFailed(Exception e) {
           // handle the failure case
        }
    };
```

**Step 2:** Create a SurfaceContainer by providing a PreviewView view.

```
PreviewView previewView = findViewById(R.id.preview_view);
SurfaceContainer<PreviewView> surfaceContainer = new SurfaceContainer<>(previewView);
```


**Step 3:** Call the ```getOcrScannerView()``` method from the CameraApi.

```
CameraApi<PreviewView> cameraApi = SmartCredentialsCameraFactory.getCameraApi();
SmartCredentialsApiResponse<SurfaceContainerInteractor> response = cameraApi.getOcrScannerView(context, surfaceContainer, lifecycleOwner, scannerCallback);
```
The Api response returns an interactor that can be called whenever you want to capture the image on which the OCR procedure will be performed.


**Step 4:** Trigger the image capture.

```
if (response.isSuccessful()) {
    response.getData().takePicture();
}
```
The ```takePicture()``` method will pass the captured image to a TextRecognizer, returning the OCR results to the scanner callback.

### Barcode reading

**Step 1:** Create a scanner callback.
```
ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onDetected(List<String> detectedValues) {
            // handle the results of the scanner
        }

        @Override
        public void onScanFailed(Exception e) {
           // handle the failure case
        }
    };
```

**Step 2:** Create a SurfaceContainer by providing a PreviewView view.

```
PreviewView previewView = findViewById(R.id.preview_view);
SurfaceContainer<PreviewView> surfaceContainer = new SurfaceContainer<>(previewView);
```

**Step 3:** Call the ```getBarcodeScannerView()``` method from the CameraApi.
```
CameraApi<PreviewView> cameraApi = SmartCredentialsCameraFactory.getCameraApi();
cameraApi.getBarcodeScannerView(context, surfaceContainer, lifecycleOwner, scannerCallback, BarcodeType.BARCODE_2D_QR_CODE);
```
The barcode scan results will be delivered to the scanner callback.\
All supported bacrodes can be found in the ```BarcodeType``` class.


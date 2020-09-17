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

**Step 1:** Obtain the OCR camera scanner layout.

```
CameraApi cameraApi = SmartCredentialsCameraFactory.getCameraApi();
SmartCredentialsApiResponse<CameraScannerLayout> response = cameraApi.getOcrScannerView(context, callback);
CameraScannerLayout cameraScannerLayout = response.getData();
```

**Step 2:** Add the camera scanner layout to the activity/fagment layout and start the scanner.

```
view.add(cameraScannerLayout);
cameraScannerLayout.startScanner();
```

**Step 3:** Use one of the ```detect``` methods provided by the OCR camera scanner layout to start the OCR reading.

```
OcrCameraScannerLayout ocrScanner = (OcrCameraScannerLayout) cameraScannerLayout;
ocrScanner.detect()
```

The results of the OCR reading will be delivered on the callback provided in the first step.

**Step 4:** Don't forget to clean up the camera scanner layout when it's not needed anymore.

```
cameraScannerLayout.releaseCamera();
```

### Barcode reading

**Step 1:** Obtain the barcode camera scanner layout. All supported bacrodes can be found in the ```BarcodeType``` class and one of them must be provided to the Camera API.

```
CameraApi cameraApi = SmartCredentialsCameraFactory.getCameraApi();
SmartCredentialsApiResponse<CameraScannerLayout> response = cameraApi.getBarcodeScannerView(context, callback, BarcodeType.BARCODE_ALL_FORMATS);
CameraScannerLayout cameraScannerLayout = response.getData();
````

**Step 2:** Add the camera scanner layout to the activity/fagment layout and start the scanner.

```
view.add(cameraScannerLayout);
cameraScannerLayout.startScanner();
```

The results of the barcode reading will be delivered on the callback provided in the first step.

**Step 3:** Don't forget to clean up the camera scanner layout when it's not needed anymore.

```
cameraScannerLayout.releaseCamera();
```
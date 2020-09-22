# Usage of the Smart Credentials DocumentScanner API

Smart Credentials Document Scanner Module, based on the [BlinkId SDK](https://microblink.com/products/blinkid), handles 
the scanning of various identity documents.

### Add build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
implementation("de.telekom.smartcredentials:documentscanner:$sc_version@aar"){
	transitive = true
}
````

### Modules initialization

**Core Module**

``` 
SmartCredentialsConfiguration coreConfig = new SmartCredentialsConfiguration.Builder(context, userId)
                .setLogger(new Logger())
                .setAppAlias(alias)
                .build();
CoreApi coreApi = SmartCredentialsCoreFactory.initialize(coreConfig);
````

**DocumentScanner module**

```
SmartCredentialsAuthenticationFactory.initSmartCredentialsDocumentScannerModule(coreApi);
```

**Set up the Microblink licence**

```
SmartCredentialsDocumentScannerFactory.setLicense(licenceKey, licenceName, context);
```

### Document scaning 

**Step 1:** Choose the desired document scanner recognizer

```
ScannerRecognizer scannerRecognizer = ScannerRecognizer.ID_COMBINED_RECOGNIZER;
```

Recognizers available in the document scanner module: ```ID_SIMPLE_RECOGNIZER```, ```DOCUMENT_FACE_RECOGNIZER```, 
```ID_BARCODE_RECOGNIZER```, ```MRTD_COMBINED_RECOGNIZER```, ```MRTD_SIMPLE_RECOGNIZER```, ```USDL_COMBINED_RECOGNIZER```, 
```USDL_SIMPLE_RECOGNIZER```, ```VISA_RECOGNIZER``` and ```ID_COMBINED_RECOGNIZER```.

**Step 2:** Implement the document scanner configuration

```
SmartCredentialsDocumentScanConfiguration documentScannerConfiguration = 
	new SmartCredentialsDocumentScanConfiguration.Builder(context)
            .setScannerRecognizer(scannerRecognizer)
            .setCameraType(CameraType.BACK_CAMERA)
            .setZoomLevel(0f)
            .setAspectMode(AspectMode.FILL)
            .setPinchToZoomAllowed(true)
            .setVideoResolution(VideoResolution.DEFAULT)
            .shouldAllowTapToFocus(true)
            .shouldOptimizeCameraForNearScan(false)
            .build();
```

**Step 3:** Implement the document scanner callback

```
DocumentScannerCallback documentScannerCallback = new DocumentScannerCallback() {
    @Override
    public void onDetected(DocumentScannerResult result) {
		// document scanned successfuly
        documentScannerLayout.pauseScanning();
		// map the document scanner result
    }

    @Override
    public void onFirstSideRecognitionFinished() {
		// first side scanned successfuly
    }

    @Override
    public void onInitialized() {
        // scanner initialized
    }

    @Override
    public void onScannerUnavailable(ScannerPluginUnavailable errorMessage) {
        // scanner unavailable
    }
};
```

**Step 4:** Map the document scanner result

For getting the data from the document scanner result, you will have to cast the abstract ```DocumentScannerResult``` 
delivered in the callback to the chosen document scanner recognizer result type.

```
switch (recognizerType) {
	case ID_COMBINED_RECOGNIZER:
		BlinkIdCombinedRecognizer.Result idCombinedResultData = ((IdCombinedRecognizerResult) result).getResultData();
		break;
    case ID_SIMPLE_RECOGNIZER:
		BlinkIdRecognizer.Result idSimpleRecognizerResultData = ((IdSimpleRecognizerResult) result).getResultData();
		break;
	case DOCUMENT_FACE_RECOGNIZER:
	    DocumentFaceRecognizer.Result documentFaceResultData = ((DocumentFaceRecognizerResult) result).getResultData();
	    break;
	case ID_BARCODE_RECOGNIZER:
	    IdBarcodeRecognizer.Result idBarcodeResultData = ((IdBarcodeRecognizerResult) result).getResultData();
		break;
	case MRTD_COMBINED_RECOGNIZER:	
		MrtdCombinedRecognizer.Result mrtdCombinedResultData = ((MrtdCombinedRecognizerResult) result).getResultData();
		break;
    case MRTD_SIMPLE_RECOGNIZER:	
		MrtdRecognizer.Result mrtdSimpleResultData = ((MrtdSimpleRecognizerResult) result).getResultData(); 
		break;
	case USDL_COMBINED_RECOGNIZER:	
		UsdlCombinedRecognizer.Result usdlCombinedResultData = ((UsdlCombinedRecognizerResult) result).getResultData();
		break;
	case USDL_SIMPLE_RECOGNIZER:	
	    UsdlRecognizer.Result usdlSimpleResultData = ((UsdlSimpleRecognizerResult) result).getResultData();
		break;
	case VISA_RECOGNIZER:	
	    VisaRecognizer.Result visaResultData = ((VisaRecognizerResult) result).getResultData();
	    break;
	default:
		// default implementation
}
```

**Step 5:** Obtain the document camera scanner layout.

```
DocumentScannerApi<SmartCredentialsDocumentScanConfiguration, ScannerRecognizer> documentScannerApi = 
	SmartCredentialsDocumentScannerFactory.getDocumentScannerApi();
SmartCredentialsApiResponse<DocumentScannerLayout<ScannerRecognizer>> response =
                api.getDocumentScannerView(documentScannerConfiguration, documentScannerCallback);
DocumentScannerLayout<ScannerRecognizer> documentScannerLayout = response.getData();
```

**Step 6:** Set the layout parameters and add the document scanner layout to the activity/fagment layout

```
LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
	LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
documentScannerLayout.setLayoutParams(layoutParams);
view.addView(documentScannerLayout);
```

**Step 7:** Bind the document scanner lifecycle methods to the activity/fragment ones

```
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    if (documentScannerLayout != null) {
        documentScannerLayout.onCreate();
    }
}

@Override
protected void onStart() {
    super.onStart();
	if (documentScannerLayout != null) {
        documentScannerLayout.onStart();
    }
}		

@Override
protected void onResume() {
    super.onResume();
    if (documentScannerLayout != null) {
        documentScannerLayout.onResume();
    }
	
@Override
protected void onPause() {
    super.onPause();
    if (documentScannerLayout != null) {
        documentScannerLayout.onPause();
    }
}

@Override
protected void onStop() {
    super.onStop();
    if (documentScannerLayout != null) {
        documentScannerLayout.onStop();
    }
}

@Override
public void onDestroy() {
    super.onDestroy();
    if (documentScannerLayout != null) {
        documentScannerLayout.onDestroy();
    }
}	
```
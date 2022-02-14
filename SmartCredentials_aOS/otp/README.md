# Usage of the Smart Credentials OTP API

Smart Credentials OTP Module is used for generating Time-based One-time Passwords(TOTPs) and 
HMAC-based One-time Passwords(HOTPs).

### Add build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
implementation("de.telekom.smartcredentials:camera:$sc_version")
implementation("de.telekom.smartcredentials:security:$sc_version")
implementation("de.telekom.smartcredentials:storage:$sc_version")
implementation("de.telekom.smartcredentials:otp:$sc_version")
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

**Camera, Security and Storage modules**

``` 
CameraApi cameraApi = SmartCredentialsCameraFactory.initSmartCredentialsCameraModule(coreApi);
SecurityApi securityApi = SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(context, coreApi);
StorageApi storageApi = SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(context, coreApi, securityApi);
```

**OTP module**
``` 
SmartCredentialsOtpFactory.initSmartCredentialsOtpModule(coreApi, securityApi, storageApi, cameraApi);
````

**Support extra Mac Algorithms**
``` 
SmartCredentialsOtpFactory.addAcceptedAlgorithms(Collections.singletonList("SHA224"));
````

### Set up Item Envelope for OTP generation

**Step 1:** Create item envelope

```
JSONObject identifierJson = new JSONObject();
identifierJson.put(OTPTokenKeyExtras.USER_LABEL, userLabel);
JSONObject detailsJson = new JSONObject();
detailsJson.put(OTPTokenKey.key, twoFactorAuthKey);
ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(String.valueOf(
	System.currentTimeMillis()), identifierJson, detailsJson);
```

**Step 2:** Create an encrypted sensitive item context

```
OTPType otpType = OTPType.TOTP;
ItemContext itemContext = ItemContextFactory.createEncryptedSensitiveItemContext(otpType.getDesc());
```

For HMAC-based One-time Passwords(Hotps) use ```OTPType.HOTP```.

**Step 3:** Add item to storage
```
StorageApi storageApi = SmartCredentialsStorageFactory.getStorageApi();
storageApi.putItem(itemEnvelope, itemContext);
```

### Import OTP Item Envelope from a scanned QR code

**Step 1:** Implement the OTP importer callback

```
private OTPImporterCallback otpImporterCallback = new OTPImporterCallback() {
        @Override
        public void onOTPItemImported(OTPImportItem otpImportItem) {
			// successful import 
        }

        @Override
        public void onOTPItemImportFailed(OTPImportFailed otpHandlerFailed) {
			// failed import 
        }

        @Override
        public void onInitialized() {
			// scanner initialized
        }

        @Override
        public void onScannerUnavailable(ScannerPluginUnavailable scannerPluginUnavailable) {
			// scanner unavailable
        }
    };
```

**Step 2:** Obtain the camera scanner layout.

```
OtpApi otpApi = SmartCredentialsOtpFactory.getOtpApi();
SmartCredentialsApiResponse<CameraScannerLayout> response =
    otpApi.importOTPItemViaQRForId(this, String.valueOf(System.currentTimeMillis()),
        otpImporterCallback);
        if (response.isSuccessful()) {
            cameraScannerLayout = response.getData();
            // start scanner
        } else {
            // unsuccessful response
        }
    }
```

**Step 3:** Add the camera scanner layout to the activity/fragment layout and start the scanner
```
view.addView(cameraScannerLayout);
cameraScannerLayout.startScanner();
```

**Step 4:** Don't forget to clean up the camera scanner layout when it's not needed anymore

```
cameraScannerLayout.releaseCamera();
```

### Generate HOTP 

**Step 1:** Implement the HOTP callback

```
private HOTPCallback hotpCallback = new HOTPCallback()() {
        @Override
		public void onOTPGenerated(String hotpValue) {
			// hotp generated successfuly 
        }

        @Override
        public void onFailed(OTPPluginError otpPluginError) {
			// hotp generation failed
        }
    };
```

**Step 2:** Implement the HOTP handler callback

```
private HOTPHandlerCallback hotpHandlerCallback = new HOTPHandlerCallback() {
        @Override
        public void onOTPHandlerReady(HOTPHandler hotpHandler) {
			// handler ready 
			macAlgorithm = MacAlgorithm.SHA256;
			hotpHandler.generateHOTP(hotpCallback, macAlgorithm)
        }

		@Override
        public void onOTPHandlerInitializationFailed(OTPHandlerFailed otpHandlerFailed) {
			// failed handler initialization 
        }
    };
```

The macAlgorithm parameter is the string representation of the mac algorithm that will be used 
for generating the OTP value. It will be used if the OTP item envelope have been set up manually or 
if there is no algorithm retrieved from the OTP QR Code.

**Step 3:** Generate HOTP value

```
OtpApi otpApi = SmartCredentialsOtpFactory.getOtpApi();
otpApi.createHOTPGenerator(itemEnvelope.getItemId(), hotpHandlerCallback);
```

### Generate TOTP 

**Step 1:** Implement the TOTP callback

```
private TOTPCallback totpCallback = new TOTPCallback()() {
        @Override
		public void onOTPGenerated(TokenResponse tokenResponse) {
			// totp generated successfuly 
        }

        @Override
        public void onFailed(OTPPluginError otpPluginError) {
			// totp generation failed
        }
    };
```

**Step 2:** Implement the TOTP handler callback

```
private TOTPHandlerCallback totpHandlerCallback = new TOTPHandlerCallback() {
        @Override
        public void onOTPHandlerReady(TOTPHandler totpHandler) {
			// handler ready 
			macAlgorithm = MacAlgorithm.SHA256;
			totpHandler.startGeneratingTOTP(totpCallback, macAlgorithm)
        }

		@Override
        public void onOTPHandlerInitializationFailed(OTPHandlerFailed otpHandlerFailed) {
			// failed handler initialization 
			totpHandler.stop();
        }
    };
```

**Step 3:** Generate TOTP value

```
OtpApi otpApi = SmartCredentialsOtpFactory.getOtpApi();
otpApi.createTOTPGenerator(itemEnvelope.getItemId(), totpHandlerCallback);
```
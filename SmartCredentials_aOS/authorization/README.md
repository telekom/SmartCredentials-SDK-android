# Usage of the Smart Credentials Authorization API

Smart Credentials Authorization Module is used for device credentials(PIN , password, pattern) and biometrics(fingerprint, iris scanning, face Id) authorization.

### Add build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
implementation("de.telekom.smartcredentials:security:$sc_version")
implementation("de.telekom.smartcredentials:storage:$sc_version")
implementation("de.telekom.smartcredentials:authorization:$sc_version")
````

### Modules initialization

The Smart Credentials module initialization is recommended to be made in the application class.

**Core Module**
``` 
SmartCredentialsConfiguration coreConfig = new SmartCredentialsConfiguration.Builder(context, userId)
                .setLogger(new Logger())
                .setRootCheckerEnabled(RootDetectionOption.ALL)
                .setAppAlias(alias)
                .build();
CoreApi coreApi = SmartCredentialsCoreFactory.initialize(coreConfig);
````

**Security and Storage modules**

``` 
SecurityApi securityApi = SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(context, coreApi);
StorageApi storageApi = SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(context, coreApi, securityApi);
```

**Authorization module**
``` 
SmartCredentialsAuthenticationFactory.initSmartCredentialsAuthenticationModule(coreApi, securityApi, storageApi);
````

### Authorization 

#### Authorize

**Step 1:** Obtain the Authorization API.

```
 AuthorizationApi authorizationApi = SmartCredentialsAuthorizationFactory.getAuthorizationApi();
````

**Step 2:** Implement the Authorization Callback.

```
 AuthorizationCallback authorizationCallback = new AuthorizationCallback() {
            @Override
            public void onAuthorizationSucceeded() {
             // successful authorization
            }

            @Override
            public void onAuthorizationError(String error) {
             // an error occurred during the authorization
            }

            @Override
            public void onAuthorizationFailed(String error) {
             // failed authorization
            }
        };
```

**Step 3:** Implement the Authorization Configuration.

```
 AuthorizationView authorizationView = new AuthorizationView.Builder("viewTitle", "viewNegativeButtonText")
									.setDescription("viewDescription")
									.setSubtitle("viewSubtitle")
									.build();
 AuthorizationConfiguration authorizationConfiguration = new AuthorizationConfiguration.Builder(authorizationView)
									.allowDeviceCredentialsFallback(true)
									.requireFaceRecognitionConfirmation(true)
									.build();
```
 * The **viewTitle** can not not be empty or null.
 * Set **allowDeviceCredentialsFallback** true if you want to allow the user to choose between biometrics and device credentials authorization if both of them are available, false otherwise.
 * If **allowDeviceCredentialsFallback** is set to 'false', the negative button text can not be empty or null.
 * Set **requireFaceRecognitionConfirmation** true if you want the user to validate the face Id authorization by pressing a confirmation button, false otherwise.

**Step 4:** Authorize the user.

```
 authorizationApi.authorize(fragmentActivity, authorizationConfiguration, authorizationCallback);
```

If the respone data is empty it means that the device is not secured(is rooted).
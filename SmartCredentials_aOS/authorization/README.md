# Usage of the Smart Credentials Authorization API

Smart Credentials Authorization Module is used for fingerprint, PIN ,pattern or faceId authorization.

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
SmartCredentialsAuthenticationFactory.initSmartCredentialsAuthenticationModule(context, coreApi, 
securityApi, storageApi);
````

### Authorization 

#### Authorization for API < 28

**Step 1:** Obtain the Authorization API.

```
AuthorizationApi authorizationApi = SmartCredentialsAuthorizationFactory.getAuthorizationApi();
````

**Step 2:** Implement the Authorization Callback.

```
 AuthorizationCallback authorizationCallback = new AuthorizationCallback() {
            @Override
            public void onAuthorized() {
				// successful authorization 
				if (authorizationDialog instanceof DialogFragment) {
                    ((DialogFragment) authorizationDialog).dismiss();
                } else {
                    getSupportFragmentManager().popBackStackImmediate();
                }
            }

            @Override
            public void onUnavailable(AuthorizationPluginUnavailable error) {
                // unavailable authorization
            }

            @Override
            public void onFailure(AuthorizationPluginError error) {
				// failed authorization
            }
        };
```

**Step 3:** Obtain the Authorization fragment response.

```
SmartCredentialsApiResponse<Fragment> response = authorizationApi.getAuthorizeUserFragment(authorizationCallback);
```

**Step 4:** Handle the fragment response.

```
if (response.isSuccessful()) {
    authorizationDialog = response.getData();
    if (authorizationDialog == null) {
        // unsecured device
    } else if (authorizationDialog instanceof DialogFragment) {
        // display dialog fragment
    } else {
        // display fragment
    }
} else {
    // failed response
}
```

#### Authorization for API >= 28

**Step 1:** Obtain the Authorization API.

```
AuthorizationApi authorizationApi = SmartCredentialsAuthorizationFactory.getAuthorizationApi();
````

**Step 2:** Implement the Authorization Callback.

```
 AuthorizationCallback authorizationCallback = new AuthorizationCallback() {
            @Override
            public void onAuthorized() {
				// successful authorization 
				if (biometricsAuthorizationPresenter != null) {
                    biometricsAuthorizationPresenter.stopListeningForUserAuth();
                }
            }

            @Override
            public void onUnavailable(AuthorizationPluginUnavailable error) {
                // unavailable authorization
            }

            @Override
            public void onFailure(AuthorizationPluginError error) {
				// failed authorization
            }
        };
```

**Step 3:** Obtain the Biometric Authorization presenter response.

```
SmartCredentialsApiResponse<Fragment> response = authorizationApi.getAuthorizeUserFragment(authorizationCallback);
```

**Step 4:** Handle the presenter response.

```
if (response.isSuccessful()) {
    biometricsAuthorizationPresenter = response.getData();
    if (biometricsAuthorizationPresenter == null) {
        // unsecured device
    } else {
		biometricsAuthorizationPresenter.startListeningForUserAuth();
	}
} else {
    // failed response
}
```

If the respone data is empty it means that the device is not secured. It is either rooted or does not have a PIN set.
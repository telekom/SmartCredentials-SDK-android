# Usage of the Smart Credentials OneClickBusiness Client Module

Smart Credentials OneClickBusiness Client module offers the possibility to create recommendations for specified products.
Once the recommendation is received, the module can be used to generate a UI flow that in the end will redirect the user to the available offers.

### Add build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
implementation("de.telekom.smartcredentials:security:$sc_version")
implementation("de.telekom.smartcredentials:storage:$sc_version")
implementation("de.telekom.smartcredentials:identityprovider:$sc_version")
implementation("de.telekom.smartcredentials:oneclickbusinessclient:$sc_version")
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

**Security module**
``` 
SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(context, coreApi);
````

**Storage module**
```
SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(context, coreApi, securityApi);
````

**IdentityProvider module**
```
SmartCredentialsIdentityProviderFactory.initSmartCredentialsIdentityProviderModule(coreApi);
````
#### OneClickBusiness Client module
**Step 1:** Implement the OneClickClient configuration
```
OneClickClientConfiguration configuration = new OneClickClientConfiguration.Builder()
                        .setCredentials(CREDENTIALS)
                        .setServerUrl(SERVER_URL)
                        .setFirebaseServerKey(FirebaseConstants.SERVER_KEY)
                        .setLogoResId(R.drawable.logo)
                        .setClientAppName(APP_NAME)
                        .build()
        );
````

**Step 2:** Initialise OneClickBusiness Client module
```
SmartCredentialsOneClickBusinessClientFactory.initSmartCredentialsOneClickBusinessClientModule(
                context,
                coreApi,
                identityProviderApi,
                storageApi,
                configuration
        );
````
#### Making a recommendation

**Step 1:** Obtain the OneClickBusiness Client Api.

```
OneClickApi oneClickApi = SmartCredentialsOneClickBusinessClientFactory.getOneClickBusinessClientApi()
````

**Step 2:** Binding your activity with ```OneClickApi```.

```
oneClickApi.bind(requireActivity())
````
The bind should be initialize in the ```onCreate()``` method of your activity/fragment.

**Step 3:** Set the ComposeView for ```OneClickApi```.
```
oneClickApi.setComposeView(view.findViewById(R.id.compose_view))
````
This step should be done in the ```onCreate()``` or ```onViewCreated()``` method of your activity/fragment.

**Step 4:** Make a recommendation.
```
oneClickApi.makeRecommendation(products)
````
The type of the variable ```products``` is ```List<String>``` which contains the recommended products id's.

**Step 5:** Unbind your activity from ```OneClickApi```.
```
oneClickApi.unbind()
````
This step should be done in the ```onDestroyView()``` or ```onDestroy()``` method of your activity/fragment.

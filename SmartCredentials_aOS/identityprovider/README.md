# Usage of the Smart Credentials Identity Provider API

Smart Credentials Identity Provider Module is used for obtaining operator token from the partner application.

### Add build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
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

**IdentityProvider module**
``` 
SmartCredentialsIdentityProviderFactory.initSmartCredentialsIdentityProviderModule(coreApi);
````

#### Obtaining Operator Token

**Step 1:** Obtain the Identity Provider API.

```
IdentityProviderApi identityProviderApi = SmartCredentialsIdentityProviderFactory.INSTANCE.getIdentityProviderApi()
````

**Step 2:** Fetch Operator Token.

```
identityProviderApi.getOperatorToken(
        @NonNull Context context,
        @NonNull String PARTNER_APPLICATION_URL,
        @NonNull String CREDENTIALS,
        @NonNull String CLIENT_ID,
        @NonNull String SCOPE,
        @NonNull IdentityProviderCallback this
)
```

# Usage of the Smart Credentials Authentication API

Smart Credentials Authentication Module handles the communication with OAuth 2.0 and OpenID Connect providers and store the data obtained in a secure manner.

### Add build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
implementation("de.telekom.smartcredentials:security:$sc_version")
implementation("de.telekom.smartcredentials:storage:$sc_version")
implementation("de.telekom.smartcredentials:authentication:$sc_version")
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

**Security and Storage modules**

``` 
SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(context, coreApi);
SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(context, 
		coreApi, SmartCredentialsSecurityFactory.getSecurityApi());
```

**Authentication module**

```
SmartCredentialsAuthenticationFactory.initSmartCredentialsAuthenticationModule(coreApi, storageApi);
```

### API initialization
In order to use the authentication APIs, you have to initialize the API with an OAuth 2.0 or OpenID Connect provider.

```
AuthenticationApi authenticationApi = SmartCredentialsAuthenticationFactory.getAuthenticationApi();  
authenticationApi.initialize(context, identity-provider-name,  
        identity-provider-configuration, provider-color,  
        listener);
```

Then, on the ```AuthenticationServiceInitListener``` attached to the initialize call you will be noticed if the provider was successfully initialized or not.

### Declare Internet permission and RedirectUriReceiverActivity in AndroidManifest
```
<uses-permission android:name="android.permission.INTERNET" />

[..]

<activity  
  android:name="net.openid.appauth.RedirectUriReceiverActivity"  
  tools:node="replace">  
    <intent-filter>  
        <action android:name="android.intent.action.VIEW" />  
  
        <category android:name="android.intent.category.DEFAULT" />  
        <category android:name="android.intent.category.BROWSABLE" />  
  
        <data android:scheme="provider-data-scheme" />  
    </intent-filter>  
</activity>
````

### Login API
After setup and initialization you can call the login API.

```
Intent completionIntent = new Intent(activity, CompletionActivity.class);  
Intent cancelIntent = new Intent(activity, CancelActivity.class);  
return authenticationApi.login(context, completionIntent, cancelIntent);
```
If the login was performed successfully, the ```CompletionActivity``` will start, otherwise in case of failure or cancellation of the login the ```CancelActivity``` will start.

The login call is performed by default on the main thread! Please make sure you call it from a background thread, in order to avoid a screen freeze.

### Access tokens
After login, access, refresh and ID token can be accessed from the ```AuthStateManager```.

```
AuthStateManager authStateManager = AuthStateManager.getInstance(context, identity-provider-name);
authStateManager.getAccessToken()
authStateManager.getRefreshToken()
authStateManager.getIdToken()
```

### Refresh access token
```
authenticationApi.refreshAccessToken(new TokenRefreshListener<AuthenticationTokenResponse>() {  
    @Override  
  public void onTokenRequestCompleted(@Nullable AuthenticationTokenResponse response, @Nullable AuthorizationException exception) {  
		// success callback
    }  
  
    @Override  
  public void onFailed(AuthenticationError errorDescription) {  
        // failed callback
    }  
});
```

### Perform action with fresh tokens
```
authenticationApi.performActionWithFreshTokens(new OnFreshTokensRetrievedListener() {  
    @Override  
  public void onRefreshComplete(@Nullable String accessToken, @Nullable String idToken, @Nullable AuthorizationException exception) {  
        // success callback 
    }  
  
    @Override  
  public void onFailed(AuthenticationError errorDescription) {  
        // failed callback
    }  
});
```


### Logout
```
authenticationApi.logOut();
```
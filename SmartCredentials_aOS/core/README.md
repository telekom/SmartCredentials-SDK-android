# Usage of the Smart Credentials Core API

Smart Credentials Core Module is the main module of the library on which all other modules are 
dependent. It contains all the data needed for the extension of the rest of the modules.
Beside this, it also assure root detection and actions execution.

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

#### Check if the device is rooted 

**Step 1:** Obtain the Core API.

```
 CoreApi coreApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi();
````

**Step 2:** Check the root status.

```
 boolean rootStatus = coreApi.isDeviceRooted().getData();
```

#### Execute actions
Method used to execute an action stored inside an item envelope based on its ID. The execution 
events are forwarded to the caller application by the executionCallback argument.

**Step 1:** Obtain the Core API.

```
 CoreApi coreApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi();
````

**Step 2:** Populate a Json with the data needed for executing the action

```
 JSONObject data = new JSONObject()
 data.put(ActionItemToJson.KEY_CHANNEL, ShareChannel.Default_SYSTEM.getName());
```

**Step 3:** Create a custom SmartCredentialsAction or use a default one.
Default SmartCredentials actions:  ```ActionCallService```, ```ActionConfirmation```,
```ActionItemToJSON```,  ```ActionQrLogin```

```
 SmartCredentialsAction itemToJsonAction = new ActionItemToJSON(actionId, actionName, data);
```

**Step 4:** Add the action to an item envelope.
Check the [Storage module documentation](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/storage) for the creation of an item envelope.

```
 itemEnvelope.getItemMetadata().addAction(itemToJsonAction);
 storageApi.update(itemEnvelope, itemContext);
```

**Step 5:** Initialize the execution callback.

```
 ExecutionCallback callback = new ExecutionCallback() {
    @Override
    public void onComplete(Object response) {
        //handle response
    }
    @Override
    public void onFailed(Object error) {
        //handle response
    }
    @Override
    public void onUnavailable(String error) {
        //handle response
    }
    @Override
    public void onAuthorizationRequired(Object status) {
        //handle response
    }    
 }
```

**Step 6:** Execute the action.

```
 coreApi.execute(itemEnvelope, actionId, callback);
```
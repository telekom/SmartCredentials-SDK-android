
# Usage of the Smart Credentials eID API

Smart Credentials eID Module, based on the [AusweissApp2 SDK](https://www.ausweisapp.bund.de/en/ausweisapp2-home/), facilitates electronic identification with the German National Identity card.

### Add build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
api("de.telekom.smartcredentials:eid:$sc_version")
```

### Modules initialization
The Smart Credentials module initialization is recommended to be made in the application class.

**Core Module**
``` 
SmartCredentialsConfiguration coreConfig = new SmartCredentialsConfiguration.Builder(context, userId)
                .setLogger(new Logger())
                .setAppAlias(alias)
                .build();
SmartCredentialsCoreFactory.initialize(coreConfig);
```

**Eid module**
``` 
EidConfiguration configuration = EidConfiguration.ConfigurationBuilder(
                productionUrl,
                testUrl,
                tlsConfiguration).build()
SmartCredentialsEidFactory.initSmartCredentialsEidModule(coreApi, configuration);
```

### Setup foreground dispatcher
Call ```ForegroundDispatcher.enable(activity)``` in the ```onResume``` lifecycle callback and ```ForegroundDispatcher.disable(activity)``` in the ```onPause``` lifecycle callback of the activity that will receive the NFC tags.
### Pass NFC tags to eID module
Override the ```onNewIntent``` method in the activity responsible for receiving the NFC tags as it follows:
```
@Override  
protected void onNewIntent(Intent intent) {  
    super.onNewIntent(intent);  
    final Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);  
	if (tag != null) {  
	    mainViewModel.updateNfcTag(tag);  
	}
}
```
### Start eID API

**Step 1:** Prepare eID message listener
```
EidMessageReceivedCallback callback = new EidMessageReceivedCallback() {
	@Override  
	public <T extends EidMessage> void onMessageReceived(T message) {  
        // handle messages  
	}  
};
```
**Step 2:** Provide eID API with the eID message listener
```
EidApi eidApi = SmartCredentialsEidFactory.getEidApi();
eidApi.setMessageReceiverCallback(callback);
```
**Step 3:** Start eID flow by binding to eID API
```
eidApi.bind(context, appPackageName);
```
From this moment, messages coming from the eID API are received in the ```EidMessageReceivedCallback``` declared above. Messages available in the eID module: ```AccessRightsMessage```, ```ApiLevelMessage```, ```AuthMessage```, ```BadStateMessage```, ```CertificateMessage```, ```EnterCanMessage```, ```EnterPinMessage```, ```EnterPukMessage```, ```InfoMessage```, ```InsertCardMessage```, ```InternalErrorMessage```, ```InvalidMessage```, ```ReaderListMessage```, ```ReaderMessage```, ```SdkConnectedMessage```, 	```SdkDisconnectedMessage```, ```SdkNotConnectedMessage```, ```SdkNotInitializedMessage```, ```SessionDisconnectedMessage```, ```SessionGeneratedMessage``` and ```UnknownCommandMessage```.

For reading the extra data from the message, you will have to cast the abstract ```EidMessage``` delivered in the callback to a more concrete type of message, using the message type from the ```EidMessage```.
```
switch (EidMessageType.valueOf(message.getMessageType())) {
	case ACCESS_RIGHTS:
		AccessRightsMessage accessRightsMessage = (AccessRightsMessage) message;
		break;
	case AUTH:
		AuthMessage authMessage = (AuthMessage) message;
		break;
	...
}
```
### Stop eID API
```
eidApi.unbind(context);
```
### Send command through eID API
In the example below, it is exemplified how an ```AcceptMessage``` is sent.
```
eidApi.sendCommand(new AcceptCommand(), new EidSendCommandCallback() {
  
    @Override  
    public void onSuccess() {  
        // success callback
    }  
  
    @Override  
    public void onFailed(Exception e) {  
        // failed callback
    }  
});
```
Commands available in the eID module: ```AcceptCommand```, ```CancelCommand```, ```GetAccessRightsCommand```,  ```GetApiLevelCommand```, ```GetCertificateCommand```, ```GetInfoCommand```, ```GetReaderCommand```, ```GetReaderListCommand```, ```RunAuthCommand```, ```SetAccessRightsCommand```, ```SetApiLevelCommand```, ```SetCanCommand```, ```SetPinCommand``` and ```SetPukCommand```.
### Electronic identification flow
The eID module is following and enabling all the identification flows supported by AusweissApp2. Please consult the information from [here](https://www.ausweisapp.bund.de/sdk/) to find out how the commands and messaged should be used.
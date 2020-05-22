## Usage of the Smart Credentials Push Notifications API

build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
implementation("de.telekom.smartcredentials:security:$sc_version")
implementation("de.telekom.smartcredentials:storage:$sc_version")
implementation("de.telekom.smartcredentials:pushnotifications:$sc_version")
````

### FCM example with callbacks
Modules initialization
```
//core module 
SmartCredentialsConfiguration coreConfig = new SmartCredentialsConfiguration.Builder(context, userId)
                .setLogger(new Logger())
                .setAppAlias(alias)
                .build();
CoreApi coreApi = SmartCredentialsCoreFactory.initialize(coreConfig);

//security and storage modules
SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(context, coreApi);
SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(context, 
		coreApi, SmartCredentialsSecurityFactory.getSecurityApi());

//pushnotifications module
//All builder parameters except the context and serviceType can be found in the google-service.json 
that can be generated from the firebase console 
PushNotificationsConfiguration pushnotificationsConfig = new PushNotificationsConfiguration
	.ConfigurationBuilder(
					context,
					apiKey,
					projectId,
					databaseUrl,
					applicationId,
					gcmSenderId,
					storageBucket,
					ServiceType.FCM)
					.setAutoSubscribeState(false)
					.build();
SmartCredentialsPushNotificationsFactory.initSmartCredentialsRxPushNotificationsModule(
                coreApi,
                SmartCredentialsStorageFactory.getStorageApi(),
                pushnotificationsConfig);			
````

Module Usage
```
PushNotificationsApi pushNotificationsApi = SmartCredentialsPushNotificationsFactory
		.getPushNotificationsApi();

//subscribing to notifications
pushNotificationsApi.subscribeAllNotifications(new PushNotificationsCallback() {
                @Override
                public void onSuccess(String message) {
                    //Successfully subscribed to push notifications
                }

                @Override
                public void onFailure(String message, List<PushNotificationsError> errors) {
                    //Could not subscribe to push notifications
                }
            });

//unsubscribing from notifications
pushNotificationsApi.unsubscribeAllNotifications(new PushNotificationsCallback() {
                @Override
                public void onSuccess(String message) {
                    //Successfully unsubscribed from push notifications
                }

                @Override
                public void onFailure(String message, List<PushNotificationsError> errors) {
                    //Could not unsubscribe from push notifications
                }
            });
			
//listening for refreshed tokens
pushNotificationsApi.setTokenRefreshedCallback(refreshedToken -> {
    //token received
    });
	
//listening for remote messages
pushNotificationsApi.setMessageReceivedCallback(remoteMessage -> {
    //message received
    });
````

### TPNS example with reactive java
Modules initialization
```
//core module 
SmartCredentialsConfiguration coreConfig = new SmartCredentialsConfiguration.Builder(context, userId)
                .setLogger(new Logger())
                .setAppAlias(alias)
                .build();
CoreApi coreApi = SmartCredentialsCoreFactory.initialize(coreConfig);

//security and storage modules
SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(context, coreApi);
SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(context, 
		coreApi, SmartCredentialsSecurityFactory.getSecurityApi());

//pushnotifications module
//All builder parameters except the context and serviceType can be found in the google-service.json 
that can be generated from the firebase console
//Tpns Application key can be found in the tpns console
PushNotificationsConfiguration pushnotificationsConfig = new PushNotificationsConfiguration
	.ConfigurationBuilder(
					context,
					apiKey,
					projectId,
					databaseUrl,
					applicationId,
					gcmSenderId,
					storageBucket,
					ServiceType.TPNS)
					.setTpnsEnvironment(TpnsEnvironment.TESTING)
					.setTpnsApplicationKey(applicationKey)
					.setAutoSubscribeState(true)
					.build();
SmartCredentialsPushNotificationsFactory.initSmartCredentialsRxPushNotificationsModule(
                coreApi,
                SmartCredentialsStorageFactory.getStorageApi(),
                pushnotificationsConfig);			
````

Module Usage
```
PushNotificationsRxApi pushNotificationsApi = SmartCredentialsPushNotificationsFactory
		.getRxPushNotificationsApi();

//subscribing to notifications
pushNotificationsRxApi.subscribeAllNotifications().subscribe( () -> {
        //Successfully subscribed to push notifications    
    }, throwable -> {
        //Could not subscribe to push notifications    
    });

//unsubscribing from notifications
pushNotificationsRxApi.unsubscribeAllNotifications().subscribe( () -> {
        //Successfully unsubscribed from push notifications    
    }, throwable -> {
        //Could not unsubscribe from push notifications    
    });
			
//listening for refreshed tokens
pushNotificationsRxApi.observeTokenRefreshed().subscribe(
    refreshedToken -> {
        //token received   
    });
	
//listening for remote messages
pushNotificationsRxApi.observeMessageReceived().subscribe(
    remoteMessage -> {
        //message received   
    });
````








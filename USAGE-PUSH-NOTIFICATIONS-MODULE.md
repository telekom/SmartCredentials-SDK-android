# Usage of the Smart Credentials Push Notifications API

Smart Credentials Push Notifications Module handles the Firebase Cloud Messaging(FCM) and 
Telekom Push Notification Service(TPNS) subscription, registration and remote messages and tokens
receiving. It is an easy to use module that allow developers to have push notifications in 
their applications in just few minutes.

The Security and Storage modules are mandatory dependencies in order to securely store the
needed tokens and keys in the application.

build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
implementation("de.telekom.smartcredentials:security:$sc_version")
implementation("de.telekom.smartcredentials:storage:$sc_version")
implementation("de.telekom.smartcredentials:pushnotifications:$sc_version")
````

## Module Initialization

### Mandatory modules initialization

Core Module
``` 
SmartCredentialsConfiguration coreConfig = new SmartCredentialsConfiguration.Builder(context, userId)
                .setLogger(new Logger())
                .setAppAlias(alias)
                .build();
CoreApi coreApi = SmartCredentialsCoreFactory.initialize(coreConfig);
````

Security and Storage modules
``` 
SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(context, coreApi);
SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(context, 
		coreApi, SmartCredentialsSecurityFactory.getSecurityApi());
````

### Push Notifications Configuration for FCM 
All of the builder's parameters except the context and serviceType can be found in the google-service.json 
that can be generated from the firebase console. 
- Auto Subscribe is an optional parameter that determines if the application 
subscribes for push notifications on module initialization. Default value is true.
```
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
````

### Push Notifications Configuration for TPNS 
All of the builder's parameters except the context and serviceType can be found in the google-service.json 
that can be generated from the firebase console.
- TPNS Environment parameter determines if the registration request is made on the production or
the testing environment. Default value is production.
- TPNS Application Key parameter is a mandatory parameter for TPNS and can be found in the TPNS
application console.
- Auto Subscribe is an optional parameter that determines if the application 
subscribes for push notifications on module initialization. Default value is true.
```
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
````

### Push Notifications Module initialization
```
SmartCredentialsPushNotificationsFactory.initSmartCredentialsRxPushNotificationsModule(
                coreApi,
                SmartCredentialsStorageFactory.getStorageApi(),
                pushnotificationsConfig);	
````

## Push Notifications usage example with callbacks
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

### Push Notifications usage example with reactive java
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








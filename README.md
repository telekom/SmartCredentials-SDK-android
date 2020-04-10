# SmartCredentials

SmartCredentials is a programmer’s library, presenting a unified interface for various solutions and protocols used to authenticate, 
authorize and conduct any kind of transactions between a user or a user-facing app and services. 
It uses the abstraction of a credential, which can handily be made available to the user for choosing the right 
identity/ entitlement and authenticating themselves for appropriate security. 
On the ‘other end’ actual technological functions are structured in modules that can be ‘plugged’ in to the SmartCredentials core 
to extend and alter its behavior behind the unifying credentialing APIs.

SmartCredentials builds on earlier works of T-Labs’ identity and payment team on a user-centric wallet. It is still feasible to add similar,
user-friendly UI, support for secure elements (SE), NFC-based payments, card-based identity management and more if ever needed.

## Usage

Add the SmartCredentials repository to your project's gradle file. Username and password for the repository are provided by the SmartCredentials team. 


```
repositories {
    google()
    jcenter()
    ...
    maven {
        url "https://dol.telekom.de/artifactory/smartcredentials-test-maven"
        credentials {
            username = "a_username"
            password = "a_password"
        }
    }
}
```

Add SmartCredentials dependency to your app gradle file.

```
implementation("de.telekom.smartcredentials:core:x.y.z")
implementation("de.telekom.smartcredentials:authentication:x.y.z")
implementation("de.telekom.smartcredentials:authorization:x.y.z")
implementation("de.telekom.smartcredentials:camera:$x.y.z")
implementation("de.telekom.smartcredentials:documentscanner:x.y.z")
implementation("de.telekom.smartcredentials:networking:x.y.z")
implementation("de.telekom.smartcredentials:otp:x.y.z")
implementation("de.telekom.smartcredentials:qrlogin:x.y.z")
implementation("de.telekom.smartcredentials:security:x.y.z")
implementation("de.telekom.smartcredentials:storage:x.y.z")
```

Sync gradle files. You should be able now to use all SmartCredentials features.

**Initialization of the SDK and Core module.**
 
```
SmartCredentialsConfiguration configuration = new SmartCredentialsConfiguration.Builder(getApplicationContext(), getString(R.string.current_user_id))
    .setLogger(DemoLogger.getLogger())
    .setRootCheckerEnabled(RootDetectionOption.ALL)
    .setAppAlias(getString(R.string.app_alias))
    .build();
SmartCredentialsCoreFactory.initialize(configuration);
CoreApi coreApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi();
```
In order to initialize the Smart Credentials SDK you need to provide a configuration containing the application contexta nd the user ID. Additionally, you can set a logger, the root detection strategy and the application alias. 

Some predefined root detection strategies can be found in the ```RootDetectionOption``` class, such as: ```NONE``` or ```ALL```. If they don't meet your needs, you are able to define your own root detection strategy, by creating a set of root detection strategies, using the available options from ```RootDetectionOption```. 

Intercepting the SDK internal logging can be made by implementing the ```ApiLogger``` interface from the Smart Credentials SKD and providing it in the builder.

A core module need to be initialized, because all other modules are dependent to it and they need a ```CoreApi``` instance for their initialization.

**Initialization and usage of Smart Credentials SDK modules**

Each module should be instantiated using their own factory classes either in the components in which they are used or on the application level. After their work is done, their instance should be destroyed by calling the 'clear' method which can be found in each factory.

Authentication Module
```
SmartCredentialsAuthenticationFactory.initSmartCredentialsAuthenticationModule(coreApi, storageApi);
AuthenticationApi authenticationApi= SmartCredentialsAuthenticationFactory.getAuthenticationApi();
//use the Authentication Api
SmartCredentialsAuthenticationFactory.clear()
````
Authorization Module
```
SmartCredentialsAuthorizationFactory.initSmartCredentialsAuthorizationModule(context, coreApi, securityApi, storageApi);
AuthorizationApi authorizationApi = SmartCredentialsAuthorizationFactory.getAuthorizationApi();
//use the Authorization Api
SmartCredentialsAuthorizationFactory.clear()
````
Camera Module
```
SmartCredentialsCameraFactory.initSmartCredentialsCameraModule(coreApi);
CameraApi cameraApi = SmartCredentialsCameraFactory.getCameraApi();
//use the Camera Api
SmartCredentialsCameraFactory.clear()
````
DocumentScanner Module
```
SmartCredentialsDocumentScannerFactory.initSmartCredentialsDocumentScannerModule(coreApi);
DocumentScannerApi documentScannerApi = SmartCredentialsDocumentScannerFactory.getDocumentScannerApi();
//use the DocumentScanner Api
SmartCredentialsDocumentScannerFactory.clear()
````
Networking Module
```
SmartCredentialsNetworkingFactory.initSmartCredentialsNetworkingModule(coreApi);
NetworkingApi networkingApi = SmartCredentialsNetworkingFactory.getNetworkingApi();
//use the Networking Api
SmartCredentialsNetworkingFactory.clear()
````
Otp Module
```
SmartCredentialsOtpFactory.initSmartCredentialsOtpModule(coreApi, securityApi, storageApi, cameraApi);
OtpApi otpApi = SmartCredentialsOtpFactory.getOtpApi();
//use the Otp Api
SmartCredentialsOtpFactory.clear()
````
QrLogin Module
```
SmartCredentialsQrLoginFactory.initSmartCredentialsQrLoginModule(coreApi, authorizationApi, networkingApi);
QrLogin qrLoginApi = SmartCredentialsQrLoginFactory.getQrLoginApi();
//use the QrLogin Api
SmartCredentialsQrLoginFactory.clear()
````
Security Module
```
SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(context, coreApi);
SecurityApi securityApi = SmartCredentialsSecurityFactory.getSecurityApi();
//use the Security Api
SmartCredentialsSecurityFactory.clear()
````
Storage Module
```
SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(context,  coreApi, securityApi);
StorageApi storageApi = SmartCredentialsStorageFactory.getStorageApi();
//use the Storage Api (see the example below)
SmartCredentialsStorageFactory.clear()
````

Example of working with Smart Credentials Storage API
```
//Create an Item Envelope
ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(
				item_id,
				identifierJson,
				detailsJson,
				isAutoLockOn,
				isItemlocked);
				
// Create an Item Context that specifies if an item will be encrypted or not and the place where it will be stored
ItemContext itemContext = ItemContextFactory.createNonEncryptedSensitiveItemContext(itemType);

// Creates an Item Filter in regards to an item type. This filter specify the place where the items are held
SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createSensitiveItemFilter(itemType);

// Insert an item in the database
storageApi.putItem(itemEnvelope, itemContext);

// Updates an item from the database
storageApi.updateItem(itemEnvelope, itemContext);

// Deletes an item from the database
storageApi.deleteItem(filter);

// Retrieve all items according to the filter
List<ItemEnvelope> mItemsList = storageApi.getAllitemsByItemType(filter);
````
If the items are stored in the non-sensitive database, all operations from the storage api must be executed on a background thread.

## Support

Discussions about the SmartCredentials library take place on this [Slack](https://smartcredentialssdk.slack.com/) channel. Anybody is welcome to join these conversations. 

## Contributing

We are open for any contribution on the topic of credential management, authentication and user-centric identity.
Even more are we looking for partners who have an interest in adding their solutions to the list of existing modules. However, if you have good reason to think it would be good to have **someone else's solution** adapted, you might just be the person who simply does it. Just make sure you are **allowed** to handle the code or executable in question.

Pull requests for small improvements or bug fixing are welcomed. For major changes, please open an issue first to discuss what you would like to change.

In case of contributing, please check the [contributing guidelines](https://github.com/kreincke/SmartCredentials-SDK-android/blob/us_195_migrate_library/CONTRIBUTING.md) and [coding standards](https://github.com/kreincke/SmartCredentials-SDK-android/blob/us_195_migrate_library/CODING-STANDARDS.md).

## Extending
Smart Credentials is an open and easily extendable library. In fact, it is so open and extendable that anyone can come with a parallel implementation of any module, except the core module.

By using Smart Credentials to build your module, not only you save time and energy for your implementation by making use of the existing features, but the new created module can also be used by others in combination with any of Smart Credentials existing modules.

Currently, there are 9 extendable modules:

1. Authentication
2. Authorization
3. Camera
4. Document scanner
5. Networking
6. Otp
7. QR login
8. Security
9. Storage
 
In order to implement your own module you have to follow the next steps:

### 1. Add Smart Credentials core module as a dependency:
```
implementation("de.telekom.smartcredentials:core:x.y.z")
```

### 2. Implement the API interface from core module

The API interface exposes an unified and standard protocol for the Smart Credentials SDK, in regard to the the specific functionality you chose to extend.

### 3. Use exposed classes 

In the core module, beside the API, you will find different exposed classes for each module. They are intentionally left here for you in order to 
reuse the functionality and save a lot of time, or because they standardize some functionality or a way of declaring/delivering data.

You are advised to look over these classes before starting to extend a module and try to use these classes as much as you can.

### 4. Expose your API implementation

Don't forget to make you API implementation public to end-users and provide them with a way of creating an instance of your API implementation.

### 5. Upload your artifact on a Maven repository (optional)

For a better user experience, integration and accessibility, we recommend to upload your artifacts on a Maven repository. You can use JFrog, jitpack or other publishing tools.

### 6. Congratulations
If you followed all the steps above and provided a working implementation for the APIs, you should have your own Smart Credentials module!

## Authors and acknowledgment
Main contributors to the vision, the architecture, and code of SmartCredentials were: Jochen Klaffer, Zhiyun Ren, Axel Nennker and several others from the wallet projects under the auspices of Jörg Heuer.

## License
```
The code in this repository is licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
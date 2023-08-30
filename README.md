
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

Make sure you have **mavenCentral** declared as a repository in your project's build.gradle file.
```
repositories {
    google()
    mavenCentral()
    ...
}
```
Continue by adding one or more Smart Credentials dependencies to your application or SDK build.gradle file, using the latest Smart Credentials version available.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.telekom.smartcredentials/core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.telekom.smartcredentials/core)
```
implementation("de.telekom.smartcredentials:core:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:authentication:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:authorization:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:camera:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:documentscanner:$smartCredentialsVersion@aar"){
	transitive = true
}
implementation("de.telekom.smartcredentials:eid:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:identityprovider:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:networking:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:otp:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:persistentlogging:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:pushnotifications:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:qrlogin:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:security:$smartCredentialsVersion")
implementation("de.telekom.smartcredentials:storage:$smartCredentialsVersion")
```

Sync gradle files. You should be able now to start using Smart Credentials in your code.

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
In order to initialize the Smart Credentials SDK you need to provide a configuration containing the application context and the user ID. Additionally, you can set a logger, the root detection strategy and the application alias. 

Some predefined root detection strategies can be found in the ```RootDetectionOption``` class, such as: ```NONE``` or ```ALL```. If they don't meet your needs, you are able to define your own root detection strategy, by creating a set of root detection strategies, using the available options from ```RootDetectionOption```. 

Intercepting the SDK internal logging can be made by implementing the ```ApiLogger``` interface from the Smart Credentials SKD and providing it in the builder.

A core module need to be initialized, because all other modules are dependent to it and they need a ```CoreApi``` instance for their initialization.

**Usage of Smart Credentials modules**

1. Authentication module  
An user guide for the Authentication module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/authentication), as well as a [demo application](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/samples/authentication).
2. Authorization module  
An user guide for the Authorization module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/authorization), as well as a [demo application](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/samples/authorization).
3. Camera module  
An user guide for the Camera module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/camera), as well as a [demo application](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/samples/camera).
4. Core module  
An user guide for the Core module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/core), as well as a [demo application](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/samples/core).
5. Document Scanner module  
An user guide for the Document Scanner module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/documentscanner), as well as a [demo application](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/samples/documentscanner).
6. eID module  
An user guide for the eID module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/eid).
7. Identity Provider module
An user guide for the identity provider module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/identityprovider), as well as a [demo application](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/samples/oneclickbusiness).
8. OTP module
An user guide for the OTP module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/otp), as well as a [demo application](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/samples/otp).
9. Push notifications module  
An user guide for the Push notifications module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/pushnotifications), as well as a [demo application](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/samples/pushnotifications).
10. Storage module  
An user guide for the Storage module can be found [here](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/SmartCredentials_aOS/storage), as well as a [demo application](https://github.com/telekom/SmartCredentials-SDK-android/tree/develop/samples/storage).
 
## Support

Discussions about the SmartCredentials library take place on this [Slack](https://smartcredentialssdk.slack.com/) channel. Anybody is welcome to join these conversations. 

## Contributing

We are open for any contribution on the topic of credential management, authentication and user-centric identity.
Even more are we looking for partners who have an interest in adding their solutions to the list of existing modules. However, if you have good reason to think it would be good to have **someone else's solution** adapted, you might just be the person who simply does it. Just make sure you are **allowed** to handle the code or executable in question.

Pull requests for small improvements or bug fixing are welcomed. For major changes, please open an issue first to discuss what you would like to change.

In case of contributing, please check the [contributing guidelines](https://github.com/kreincke/SmartCredentials-SDK-android/blob/develop/CONTRIBUTING.md) and [coding standards](https://github.com/kreincke/SmartCredentials-SDK-android/blob/develop/CODING-STANDARDS.md).

## Extending
Smart Credentials is an open and easily extendable library. In fact, it is so open and extendable that anyone can come with a parallel implementation of any module, except the core module.

By using Smart Credentials to build your module, not only you save time and energy for your implementation by making use of the existing features, but the new created module can also be used by others in combination with any of Smart Credentials existing modules.

Currently, there are 13 extendable modules:

1.  Authentication
2.  Authorization
3.  Camera
4.  Document scanner
5.  Eid
6.  Networking
7.  OneClick Business client
8.  Identity Provider
9.  Otp
10. Persistent logging
11. Push notifications
12. QR login
13. Security
14. Storage

In order to implement your own module you have to follow the next steps:

### 1. Add Smart Credentials core module as a dependency:
```
implementation("de.telekom.smartcredentials:core:$smartCredentialsVersion")
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

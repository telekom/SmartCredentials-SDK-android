
# Usage of the Smart Credentials Storage API

Smart Credentials Storage Module is used for persisting items in a secure and reliable manner.

### Add build.gradle dependencies
```
implementation("de.telekom.smartcredentials:core:$sc_version")
implementation("de.telekom.smartcredentials:security:$sc_version")
implementation("de.telekom.smartcredentials:storage:$sc_version")
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

**Security module**
``` 
SecurityApi securityApi = SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(this, coreApi);  
````

**Storage module**
``` 
SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(this, coreApi, securityApi);
````

### Add item to storage

**Step 1:** Create item context

The item context defines the item as being sensitive or non-sensitive. The sensitive repository use Android's ```SharedPreferences``` as the storage location and the non-sensitive repository use a database as the storage location. 
The sensitive repository is recommended for small items and operations on it does not need to be performed on a background thread, while the non-sensitive repository is recommended for large items and operations on it must be made on a background thread.

Also, it defines if the item will be stored encrypted or plain.

Hence, 4 possibilities are available, each one being exemplified below:

**Sensitive & encrypted**
```
ItemContext itemContext = ItemContextFactory.createEncryptedSensitiveItemContext(itemType);
```

**Sensitive & non-encrypted**
```
ItemContext itemContext = ItemContextFactory.createNonEncryptedSensitiveItemContext(itemType)
```

**Non-sensitive & encrypted**
```
ItemContext itemContext = ItemContextFactory.createEncryptedNonSensitiveItemContext(itemType);
```

**Non-sensitive & non-encrypted**
```
ItemContext itemContext = ItemContextFactory.createNonEncryptedNonSensitiveItemContext(itemType)

```
**Step 2:** Create item envelope

The item envelope is an abstraction of the data being saved, composed from 3 main components: id, identifier and details.

The id should be unique for every item type, having a similar role as a primary key in relational databases. The identifier should contain the most important parts of the data being saved, in the case of non-sensitive repositories being very easy to fetch, while the details should contain the rest of the data. **Both identifier and details must be provided in a JSON format.**
```
ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(itemId, identifierJson, detailsJson);
```

**Step 3:** Add item to storage
```
StorageApi storageApi = SmartCredentialsStorageFactory.getStorageApi();
storageApi.putItem(itemEnvelope, getItemContext());
```
### Fetch items from storage

**Step 1:** Create item filter

The item filter defines if the fetch will be made from the sensitive or non-sensitive repository.

**Sensitive repository filter**
```
SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createSensitiveItemFilter(itemType);
```
**Non-sensitive repository filter**
```
SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createNonSensitiveItemFilter(itemType);
```
**Step 2:** Fetch items by item type
```
storageApi.getAllItemsByItemType(filter);
```
In case of non-sensitive items, the item details are **not** being fetched by the ```getAllItemsByItemType``` API. They have to be fetched manually using the ```getItemDetailsById``` API, as it follows:
```
SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createNonSensitiveItemFilter(itemId, itemType);
storageApi.getItemDetailsById(filter);
```
### Delete items from storage
```
SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createNonSensitiveItemFilter(itemId, itemType);
storageApi.deleteItem(filter);
```
The example above deletes a non-sensitive item. The approach is the same for sensitive items, by replacing the non-sensitive filter with a sensitive one.
### Update item in storage
```
storageApi.updateItem(itemEnvelope, itemContext);
```
The update operation is very similar with the add item operation. You can see the **Add item to storage** chapter in order to see how an item envelope and item context are created.
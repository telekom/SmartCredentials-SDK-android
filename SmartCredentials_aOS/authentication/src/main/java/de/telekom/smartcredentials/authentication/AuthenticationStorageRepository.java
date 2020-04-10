package de.telekom.smartcredentials.authentication;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.context.ItemContextFactory;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class AuthenticationStorageRepository {

    private static AuthenticationStorageRepository INSTANCE;

    private final static String KEY_AUTH_CONFIG_TYPE = "auth_config";
    private final static String KEY_AUTH_STATE_TYPE = "auth_state";
    private final static String KEY_AUTH_CONFIG_ID = "smartcredentials_auth_config";

    private StorageApi storageApi;
    private ItemContext authConfigItemContext;

    public static AuthenticationStorageRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticationStorageRepository();
        }
        return INSTANCE;
    }

    private AuthenticationStorageRepository() {
    }

    public void init(StorageApi storageApi) {
        this.storageApi = storageApi;
        authConfigItemContext = ItemContextFactory.createEncryptedSensitiveItemContext(KEY_AUTH_CONFIG_TYPE);
    }

    private ItemEnvelope getAuthConfigItem() {
        SmartCredentialsFilter filter = SmartCredentialsFilterFactory
                .createSensitiveItemFilter(KEY_AUTH_CONFIG_ID, KEY_AUTH_CONFIG_TYPE);
        ItemContext itemContext = ItemContextFactory
                .createEncryptedSensitiveItemContext(KEY_AUTH_CONFIG_TYPE);
        SmartCredentialsApiResponse<ItemEnvelope> response = storageApi.getItemDetailsById(filter);
        if (response.isSuccessful()) {
            return response.getData();
        } else {
            ItemEnvelope itemEnvelope = ItemEnvelopeFactory
                    .createItemEnvelope(KEY_AUTH_CONFIG_ID, new JSONObject());
            storageApi.putItem(itemEnvelope, itemContext);
            return itemEnvelope;
        }
    }

    public void saveAuthConfigValue(String key, String value) {
        ItemEnvelope authConfig = getAuthConfigItem();
        JSONObject identifier = authConfig.getIdentifier();
        try {
            identifier.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        authConfig.setIdentifier(identifier);
        storageApi.putItem(authConfig, authConfigItemContext);
    }

    String getAuthConfig(String key) {
        try {
            return getAuthConfigItem().getIdentifier().getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    String getAuthState(String itemId) {
        SmartCredentialsFilter filter = SmartCredentialsFilterFactory
                .createSensitiveItemFilter(itemId, KEY_AUTH_STATE_TYPE);
        SmartCredentialsApiResponse<ItemEnvelope> response = storageApi.getItemDetailsById(filter);
        if (response.isSuccessful()) {
            return response.getData().getIdentifier().toString();
        }
        return null;
    }

    void deleteAuthState(String itemId) {
        SmartCredentialsFilter filter = SmartCredentialsFilterFactory
                .createSensitiveItemFilter(itemId, KEY_AUTH_STATE_TYPE);
        storageApi.deleteItemsByType(filter);
    }

    void saveAuthState(String itemId, JSONObject jsonSerialize) {
        ItemContext authStateItemContext = ItemContextFactory
                .createEncryptedSensitiveItemContext(KEY_AUTH_STATE_TYPE);
        ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(itemId, jsonSerialize);
        storageApi.putItem(itemEnvelope, authStateItemContext);
    }
}

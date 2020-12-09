package de.telekom.scdocumentscannerdemo.repository;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.context.ItemContextFactory;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class LocalRepository implements Repository {

    private final static String TAG = "LocalRepository";
    public static final String CAPTURE_FRONT_SIDE_ID = "capture_front_side_id";
    public static final String CAPTURE_BACK_SIDE_ID = "capture_back_side_id";
    public static final String CAPTURE_NAME_ID = "capture_name_id";
    public static final String CAPTURE_MRZ_ID = "capture_mrz_id";
    public static final String CAPTURE_ITEM_KEY = "capture_key";
    public static final String CAPTURE_ITEM_TYPE = "capture_type";

    private final StorageApi mStorageApi;

    public LocalRepository(StorageApi storageApi) {
        mStorageApi = storageApi;
    }

    @Override
    public void storeCapture(int side, byte[] capture) {
        if (side == 1) {
            store(CAPTURE_FRONT_SIDE_ID, CAPTURE_ITEM_TYPE, CAPTURE_ITEM_KEY,
                    Base64.encodeToString(capture, 0, capture.length, Base64.NO_WRAP));
        } else {
            store(CAPTURE_BACK_SIDE_ID, CAPTURE_ITEM_TYPE, CAPTURE_ITEM_KEY,
                    Base64.encodeToString(capture, 0, capture.length, Base64.NO_WRAP));
        }
    }

    @Override
    public void storeName(String name) {
        store(CAPTURE_NAME_ID, CAPTURE_ITEM_TYPE, CAPTURE_ITEM_KEY, name);
    }

    @Override
    public void storeMrz(String mrz) {
        store(CAPTURE_MRZ_ID, CAPTURE_ITEM_TYPE, CAPTURE_ITEM_KEY, mrz);
    }

    @Override
    public byte[] retrieveCapture(int side) {
        if (side == 1) {
            return Base64.decode(retrieve(CAPTURE_FRONT_SIDE_ID, CAPTURE_ITEM_TYPE, CAPTURE_ITEM_KEY,
                    ""), Base64.NO_WRAP);
        } else {
            return Base64.decode(retrieve(CAPTURE_BACK_SIDE_ID, CAPTURE_ITEM_TYPE, CAPTURE_ITEM_KEY,
                    ""), Base64.NO_WRAP);
        }
    }

    @Override
    public String retrieveName() {
        return retrieve(CAPTURE_NAME_ID, CAPTURE_ITEM_TYPE, CAPTURE_ITEM_KEY, "");
    }

    @Override
    public String retrieveMrz() {
        return retrieve(CAPTURE_MRZ_ID, CAPTURE_ITEM_TYPE, CAPTURE_ITEM_KEY, "");
    }

    @Override
    public void clearData() {
        clearData(CAPTURE_ITEM_TYPE);
    }

    @SuppressWarnings("SameParameterValue")
    private void clearData(String itemType) {
        SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createSensitiveItemFilter(itemType);
        mStorageApi.deleteItemsByType(filter);
    }

    @SuppressWarnings("SameParameterValue")
    private void store(String itemId, String itemType, String itemKey, String value) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(itemKey, value);
            ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(itemId, jsonObject);
            ItemContext itemContext = ItemContextFactory.createNonEncryptedSensitiveItemContext(itemType);
            mStorageApi.putItem(itemEnvelope, itemContext);
        } catch (JSONException e) {
            ApiLoggerResolver.logError(TAG, e.getMessage());
        }
    }

    @SuppressWarnings("SameParameterValue")
    private String retrieve(String itemId, String itemType, String itemKey, String defaultValue) {
        SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createSensitiveItemFilter(itemId, itemType);
        SmartCredentialsApiResponse<ItemEnvelope> itemEnvelope = mStorageApi.getItemSummaryById(filter);

        if (itemEnvelope.isSuccessful()) {
            try {
                return itemEnvelope.getData().getIdentifier().getString(itemKey);
            } catch (JSONException e) {
                ApiLoggerResolver.logError(TAG, e.getMessage());
                return defaultValue;
            }
        }
        return defaultValue;
    }

}

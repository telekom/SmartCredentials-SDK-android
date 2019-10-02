/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.storage.database.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Objects;

import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.utils.ModelGenerator;

import static de.telekom.smartcredentials.storage.utils.ModelGenerator.DATA_IS_NOT_ENCRYPTED;
import static de.telekom.smartcredentials.storage.utils.ModelGenerator.ID;
import static de.telekom.smartcredentials.storage.utils.ModelGenerator.IDENTIFIER;
import static de.telekom.smartcredentials.storage.utils.ModelGenerator.NON_SENSITIVE_REPO_TYPE;
import static de.telekom.smartcredentials.storage.utils.ModelGenerator.PRIVATE;
import static de.telekom.smartcredentials.storage.utils.ModelGenerator.TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(PowerMockRunner.class)
public class ModelConverterTest {
    private Item mItem;
    private ItemDomainModel mItemDomainModel;
    private Gson mGson;

    @Before
    public void setUp() {
        mItem = ModelGenerator.generateItem();
        mItemDomainModel = ModelGenerator.generateNonEncryptedNonSensitiveItemDomainModel();
        mGson = new GsonBuilder().create();
    }

    @Test
    public void generateItemDomainModelReturnsNullIfItemIsNull() {
        ItemDomainModel itemDomainModel =
                ModelConverter.generateItemDomainModel(null, PRIVATE, NON_SENSITIVE_REPO_TYPE);
        //noinspection ConstantConditions
        assertNull(itemDomainModel);
    }

    @Test
    public void generateItemDomainModelReturnsItemDomainModel() {
        ItemDomainModel itemDomainModel =
                ModelConverter.generateItemDomainModel(mItem, PRIVATE, NON_SENSITIVE_REPO_TYPE);
        assertNotNull(itemDomainModel);
        assertEquals(itemDomainModel.getUid(), ID);

        assertNotNull(itemDomainModel.getMetadata());
        assertEquals(itemDomainModel.getMetadata().getActionList().get(0).getId(), ID);
        assertEquals(itemDomainModel.getMetadata().getContentType(), NON_SENSITIVE_REPO_TYPE.getContentType());
        assertEquals(itemDomainModel.getMetadata().getItemType(), TYPE);

        assertNotNull(itemDomainModel.getData());
        assertEquals(itemDomainModel.getData().getIdentifier(), IDENTIFIER);
        assertEquals(itemDomainModel.getData().getPrivateData(), PRIVATE);
    }

    @Test
    public void generateItemDomainModelReturnsItemDomainModelWithPrivateDataEvenWhenIdentifierIsNull() {
        mItem.setIdentifier(null);
        ItemDomainModel itemDomainModel =
                ModelConverter.generateItemDomainModel(mItem, PRIVATE, NON_SENSITIVE_REPO_TYPE);
        assertNotNull(itemDomainModel);
        assertEquals(itemDomainModel.getUid(), ID);

        assertNotNull(itemDomainModel.getMetadata());
        assertEquals(itemDomainModel.getMetadata().getActionList().get(0).getId(), ID);
        assertEquals(itemDomainModel.getMetadata().getContentType(), NON_SENSITIVE_REPO_TYPE.getContentType());
        assertEquals(itemDomainModel.getMetadata().getItemType(), TYPE);

        assertNotNull(itemDomainModel.getData());
        assertNull(itemDomainModel.getData().getIdentifier());
        assertEquals(itemDomainModel.getData().getPrivateData(), PRIVATE);
    }

    @Test
    public void generateItemDomainModelReturnsItemDomainModelWithNullContentTypeWhenRepoTypeIsNull() {
        ItemDomainModel itemDomainModel =
                ModelConverter.generateItemDomainModel(mItem, PRIVATE, null);
        assertNotNull(itemDomainModel);

        assertNotNull(itemDomainModel.getMetadata());
        assertNull(itemDomainModel.getMetadata().getContentType());
    }

    @Test
    public void getItemSummaryReturnsNullIfItemDomainModelIsNull() {
        Item item = ModelConverter.getItemSummary(null);
        //noinspection ConstantConditions
        assertNull(item);
    }

    @Test
    public void getItemSummaryReturnsItem() {
        Item item = ModelConverter.getItemSummary(mItemDomainModel);
        assertNotNull(item);
        assertEquals(item.getUid(), ID);
        assertEquals(item.getActionList().get(0).getId(), ID);
        assertEquals(item.getType(), TYPE);
        assertEquals(item.getIdentifier(), IDENTIFIER);
        assertEquals(item.isSecuredData(), mItemDomainModel.getMetadata().isDataEncrypted());
    }

    @Test
    public void getItemSummaryReturnsItemWithNoIdentifierWhenDomainModelDataIsNull() {
        mItemDomainModel.setData(null);
        Item item = ModelConverter.getItemSummary(mItemDomainModel);
        assertNull(item.getIdentifier());
    }

    @Test
    public void getItemSummaryReturnsItemWithNullFieldsWhenDomainModelMetadataIsNull() {
        mItemDomainModel.setMetadata(null);
        Item item = ModelConverter.getItemSummary(mItemDomainModel);
        assertNull(item.getType());
        assertNull(item.getUserId());
        assertNull(item.getActionList());
        assertFalse(item.isSecuredData());
    }

    @Test
    public void getItemPrivateDataReturnsNullIfItemDomainModelIsNull() {
        assertNull((ModelConverter.getItemPrivateData(null)));
    }

    @Test
    public void getItemPrivateDataReturnsItemPrivateData() {
        ItemPrivateData itemPrivateData = (ModelConverter.getItemPrivateData(mItemDomainModel));
        assertNotNull(itemPrivateData);
        assertEquals(itemPrivateData.getUid(), ID);
        assertEquals(itemPrivateData.getInfo(), PRIVATE);
    }

    @Test
    public void getItemPrivateDataReturnsItemPrivateDataWithNoInfoWhenDomainModelDataIsNull() {
        mItemDomainModel.setData(null);
        ItemPrivateData privateData = ModelConverter.getItemPrivateData(mItemDomainModel);
        assertNull(privateData.getInfo());
    }

    @Test
    public void getItemJSONObjectReturnsNullIfItemDomainModelIsNull() throws JSONException {
        assertNull((ModelConverter.getItemJSONObject(null, null)));
    }

    @Test
    public void getItemJSONObject() throws JSONException {
        JSONObject itemJSONObject = ModelConverter.getItemJSONObject(mItemDomainModel, mGson);
        assertNotNull(itemJSONObject);

        JSONObject itemJSON = new JSONObject(itemJSONObject.getString(Item.DESCRIPTION));
        assertNotNull(itemJSON);
        assertEquals(itemJSON.get(Item.UID_NAME), ID);
        assertEquals(itemJSON.get(Item.IDENTIFIER_NAME), IDENTIFIER);
        assertEquals(itemJSON.get(Item.TYPE_NAME), TYPE);
        assertEquals(itemJSON.get(Item.SECURED_NAME), DATA_IS_NOT_ENCRYPTED);


        JSONObject itemPrivateDataJSON = new JSONObject(itemJSONObject.getString(ItemPrivateData.DESCRIPTION));
        assertNotNull(itemPrivateDataJSON);
        assertEquals(itemPrivateDataJSON.get(ItemPrivateData.UID_NAME), ID);
        assertEquals(itemPrivateDataJSON.get(ItemPrivateData.INFO_NAME), PRIVATE);
    }

    @PrepareForTest(ModelConverter.class)
    @Test(expected = NullPointerException.class)
    public void getItemJSONObjectReturnsJSONWithNoPrivateDataWhenItemPrivateDataIsNull() throws JSONException {
        PowerMockito.mockStatic(ModelConverter.class);
        JSONObject jsonObject = ModelConverter.getItemJSONObject(mItemDomainModel, mGson);
        jsonObject.get(ItemPrivateData.DESCRIPTION);
    }

    @Test
    public void getItemDomainModelFromJSONObjectReturnsNullIfJsonIsNull() throws JSONException {
        assertNull(ModelConverter.getItemDomainModelFromJSONObject(null, mGson, NON_SENSITIVE_REPO_TYPE));
    }

    @Test
    public void getItemDomainModelFromJSONObjectReturnsItemDomainModel() throws JSONException {
        String json = Objects.requireNonNull(
                ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).toString();
        ItemDomainModel itemDomainModel =
                ModelConverter.getItemDomainModelFromJSONObject(json, mGson, NON_SENSITIVE_REPO_TYPE);

        assertNotNull(itemDomainModel);
        assertEquals(itemDomainModel.getUid(), mItemDomainModel.getUid());

        assertNotNull(itemDomainModel.getMetadata());
        assertEquals(itemDomainModel.getMetadata().getContentType(), NON_SENSITIVE_REPO_TYPE.getContentType());
        assertEquals(itemDomainModel.getMetadata().getItemType(), TYPE);
        assertEquals(itemDomainModel.getMetadata().getActionList().get(0).getId(), ID);

        assertNotNull(itemDomainModel.getData());
        assertEquals(itemDomainModel.getData().getIdentifier(), IDENTIFIER);
        assertEquals(itemDomainModel.getData().getPrivateData(), PRIVATE);
    }

    @Test
    public void getItemSummaryFromJSONObjectReturnsNullIfJsonIsNull() throws JSONException {
        assertNull(ModelConverter.getItemSummaryFromJSONObject(null, mGson, NON_SENSITIVE_REPO_TYPE));
    }

    @Test
    public void getItemSummaryFromJSONObjectReturnsItemSummaryAndNullItemPrivateData() throws JSONException {
        String json = Objects.requireNonNull(ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).toString();
        ItemDomainModel itemDomainModel = ModelConverter.getItemSummaryFromJSONObject(json, mGson, NON_SENSITIVE_REPO_TYPE);

        assertNotNull(itemDomainModel);
        assertEquals(itemDomainModel.getUid(), mItemDomainModel.getUid());

        assertNotNull(itemDomainModel.getMetadata());
        assertEquals(itemDomainModel.getMetadata().getItemType(), TYPE);
        assertEquals(itemDomainModel.getMetadata().getContentType(), NON_SENSITIVE_REPO_TYPE.getContentType());
        assertEquals(itemDomainModel.getMetadata().getActionList().get(0).getId(), ID);

        assertNotNull(itemDomainModel.getData());
        assertEquals(itemDomainModel.getData().getIdentifier(), IDENTIFIER);
        assertNull(itemDomainModel.getData().getPrivateData());
    }

}
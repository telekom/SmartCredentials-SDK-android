package de.telekom.scstoragedemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.telekom.scstoragedemo.PreferenceManager;
import de.telekom.scstoragedemo.R;
import de.telekom.scstoragedemo.add.AddItemActivity;
import de.telekom.scstoragedemo.edit.ItemDetailsActivity;
import de.telekom.scstoragedemo.storagetype.StorageType;
import de.telekom.scstoragedemo.storagetype.StorageTypeActivity;
import de.telekom.scstoragedemo.threading.SmartTask;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;

public class MainActivity extends AppCompatActivity implements OnItemInteractionListener {

    private StorageApi storageApi;
    private View rootView;
    private RecyclerView recyclerView;
    private List<ItemEnvelope> items;
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageApi = SmartCredentialsStorageFactory.getStorageApi();

        rootView = findViewById(R.id.main_root_view);
        recyclerView = findViewById(R.id.items_recycler_view);

        items = new ArrayList<>();
        adapter = new ItemsAdapter(items, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle();
        fetchItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_item:
                Intent typeIntent = new Intent(this, StorageTypeActivity.class);
                startActivity(typeIntent);
                return true;
            case R.id.add_item:
                Intent addIntent = new Intent(this, AddItemActivity.class);
                startActivity(addIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(ItemEnvelope item) {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        Intent intent = new Intent(this, ItemDetailsActivity.class);
        intent.putExtra(ItemDetailsActivity.EXTRA_ITEM_ID, item.getItemId());
        intent.putExtra(ItemDetailsActivity.EXTRA_ITEM_SENSITIVE, preferenceManager.getStorageType().equals(StorageType.SENSITIVE));
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(ItemEnvelope item) {
        SmartTask.with(this)
                .assign(() -> storageApi.deleteItem(getDeleteItemsFilter(item.getItemId())))
                .finish(result -> fetchItems())
                .execute();
    }

    @SuppressWarnings("unchecked")
    private void fetchItems() {
        SmartTask.with(this)
                .assign(() -> storageApi.getAllItemsByItemType(getFetchItemsFilter()))
                .finish(result -> {
                    SmartCredentialsApiResponse<List<ItemEnvelope>> response =
                            (SmartCredentialsApiResponse<List<ItemEnvelope>>) result;
                    if (response != null && response.isSuccessful()) {
                        items.clear();
                        items.addAll(response.getData());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);

                    } else {
                        Snackbar.make(rootView, R.string.failed_retrieve_items, Snackbar.LENGTH_SHORT).show();
                    }
                })
                .execute();
    }

    private void setTitle() {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        if (preferenceManager.getStorageType().equals(StorageType.SENSITIVE)) {
            setTitle(R.string.sensitive);
        } else {
            setTitle(R.string.non_sensitive);
        }
    }

    private SmartCredentialsFilter getFetchItemsFilter() {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        if (preferenceManager.getStorageType().equals(StorageType.SENSITIVE)) {
            return SmartCredentialsFilterFactory.createSensitiveItemFilter(AddItemActivity.ITEM_TYPE);
        } else {
            return SmartCredentialsFilterFactory.createNonSensitiveItemFilter(AddItemActivity.ITEM_TYPE);
        }
    }

    private SmartCredentialsFilter getDeleteItemsFilter(String itemId) {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        if (preferenceManager.getStorageType().equals(StorageType.SENSITIVE)) {
            return SmartCredentialsFilterFactory.createSensitiveItemFilter(itemId, AddItemActivity.ITEM_TYPE);
        } else {
            return SmartCredentialsFilterFactory.createNonSensitiveItemFilter(itemId, AddItemActivity.ITEM_TYPE);
        }
    }
}
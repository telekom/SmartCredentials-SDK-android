package de.telekom.scstoragedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;

public class MainActivity extends AppCompatActivity implements OnItemInteractionListener {

    private StorageApi storageApi;
    private View rootView;
    private TextView noItemsTextView;
    private RecyclerView recyclerView;
    private List<ItemEnvelope> items;
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageApi = SmartCredentialsStorageFactory.getStorageApi();

        rootView = findViewById(R.id.main_root_view);
        noItemsTextView = findViewById(R.id.no_items_text_view);
        recyclerView = findViewById(R.id.items_recycler_view);

        items = new ArrayList<>();
        adapter = new ItemsAdapter(items, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        noItemsTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if (item.getItemId() == R.id.add_item) {
            Intent intent = new Intent(this, AddItemActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteClicked(ItemEnvelope item) {
        SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createSensitiveItemFilter(item.getItemId(), AddItemActivity.ITEM_TYPE);
        storageApi.deleteItem(filter);
        fetchItems();
    }

    private void fetchItems() {
        new Thread(() -> {
            SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createSensitiveItemFilter(AddItemActivity.ITEM_TYPE);
            SmartCredentialsApiResponse<List<ItemEnvelope>> response = storageApi.getAllItemsByItemType(filter);
            runOnUiThread(() -> {
                if (response.isSuccessful()) {
                    if (response.getData().size() > 0) {
                        items.clear();
                        items.addAll(response.getData());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        noItemsTextView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        noItemsTextView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    Snackbar.make(rootView, R.string.failed_retrieve_items, Snackbar.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
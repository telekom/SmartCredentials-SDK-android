package de.telekom.scstoragedemo.storagetype;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import de.telekom.scstoragedemo.PreferenceManager;
import de.telekom.scstoragedemo.R;

/**
 * Created by Alex.Graur@endava.com at 8/27/2020
 */
public class StorageTypeActivity extends AppCompatActivity implements OnStorageTypeInteractionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_type);

        RecyclerView recyclerView = findViewById(R.id.storage_type_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        StorageTypeAdapter adapter = new StorageTypeAdapter(Arrays.asList(StorageType.values()), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStorageTypeClicked(StorageType type) {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        preferenceManager.setStorageType(type);
        finish();
    }
}

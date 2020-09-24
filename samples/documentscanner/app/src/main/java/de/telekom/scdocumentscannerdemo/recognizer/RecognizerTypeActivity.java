package de.telekom.scdocumentscannerdemo.recognizer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import de.telekom.scdocumentscannerdemo.BaseUpActivity;
import de.telekom.scdocumentscannerdemo.PreferenceManager;
import de.telekom.scdocumentscannerdemo.R;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public class RecognizerTypeActivity extends BaseUpActivity implements RecognizerTypeAdapter.OnRecognizerTypeInteractionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_recognizer);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        RecognizerTypeAdapter adapter = new RecognizerTypeAdapter(Arrays.asList(RecognizerType.values()), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecognizerTypeClicked(RecognizerType type) {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        preferenceManager.setRecognizerType(type);
        finish();
    }
}


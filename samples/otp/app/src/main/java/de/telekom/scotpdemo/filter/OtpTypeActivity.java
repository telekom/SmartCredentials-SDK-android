package de.telekom.scotpdemo.filter;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import de.telekom.scotpdemo.BaseUpActivity;
import de.telekom.scotpdemo.PreferenceManager;
import de.telekom.scotpdemo.R;
import de.telekom.smartcredentials.core.model.otp.OTPType;

/**
 * Created by gabriel.blaj@endava.com at 9/1/2020
 */
public class OtpTypeActivity extends BaseUpActivity implements OtpTypeAdapter.OnOtpTypeInteractionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_otp);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        OtpTypeAdapter adapter = new OtpTypeAdapter(Arrays.asList(OTPType.values()), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onOtpTypeClicked(OTPType type) {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        preferenceManager.setOtpType(type);
        finish();
    }
}

package de.telekom.scotpdemo.add;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import de.telekom.scotpdemo.BaseUpActivity;
import de.telekom.scotpdemo.R;

/**
 * Created by gabriel.blaj@endava.com at 9/2/2020
 */
public class AddOtpActivity extends BaseUpActivity implements OtpAddModeAdapter.OnOtpAddTypeInteractionListener {

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
        OtpAddModeAdapter adapter = new OtpAddModeAdapter(this, Arrays.asList(OtpAddMode.values()), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onOtpTypeClicked(OtpAddMode type) {
        if (type == OtpAddMode.PROVIDED_KEY) {
            startActivity(new Intent(this, AddManualOtpActivity.class));
        } else {
            startActivity(new Intent(this, AddQrOtpActivity.class));
        }
        finish();
    }
}

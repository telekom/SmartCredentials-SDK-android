package de.telekom.sccoredemo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;

public class MainActivity extends AppCompatActivity {

    private CoreApi coreApi;

    private ImageView rootStatusIcon;
    private TextView rootStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coreApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi();
        rootStatusIcon = findViewById(R.id.root_status_icon);
        rootStatus = findViewById(R.id.root_status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (coreApi.isDeviceRooted().getData()) {
            rootStatus.setText(R.string.rooted);
            rootStatusIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_rooted));
        } else {
            rootStatus.setText(R.string.not_rooted);
            rootStatusIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_rooted));
        }
    }
}
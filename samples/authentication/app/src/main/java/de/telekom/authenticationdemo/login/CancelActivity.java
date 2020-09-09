package de.telekom.authenticationdemo.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import de.telekom.authenticationdemo.R;

public class CancelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        finish();
    }
}
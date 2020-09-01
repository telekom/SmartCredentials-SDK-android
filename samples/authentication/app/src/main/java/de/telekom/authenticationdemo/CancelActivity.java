package de.telekom.authenticationdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CancelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        finish();
    }
}
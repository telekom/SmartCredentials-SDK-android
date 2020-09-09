package de.telekom.authenticationdemo.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import de.telekom.authenticationdemo.tokens.TokensActivity;
import de.telekom.authenticationdemo.R;

public class CompletionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion);

        startActivity(new Intent(this, TokensActivity.class));
        finish();
    }
}
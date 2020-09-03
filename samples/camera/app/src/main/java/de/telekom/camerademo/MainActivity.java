package de.telekom.camerademo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import de.telekom.camerademo.ocr.OcrActivity;
import de.telekom.camerademo.qr.QrActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView qrCardView = findViewById(R.id.qr_card_view);
        qrCardView.setOnClickListener(view -> startActivity(new Intent(this, QrActivity.class)));
        CardView ocrCardView = findViewById(R.id.ocr_card_View);
        ocrCardView.setOnClickListener(view -> startActivity(new Intent(this, OcrActivity.class)));
    }
}
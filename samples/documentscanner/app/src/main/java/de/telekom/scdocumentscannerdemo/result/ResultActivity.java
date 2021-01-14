package de.telekom.scdocumentscannerdemo.result;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import de.telekom.scdocumentscannerdemo.BaseUpActivity;
import de.telekom.scdocumentscannerdemo.R;
import de.telekom.scdocumentscannerdemo.repository.LocalRepository;
import de.telekom.scdocumentscannerdemo.repository.Repository;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public class ResultActivity extends BaseUpActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Repository repository = new LocalRepository(SmartCredentialsStorageFactory.getStorageApi());
        setImage(repository.retrieveCapture(1), repository.retrieveCapture(2));
        setName(repository.retrieveName());
        setMrz(repository.retrieveMrz());
    }

    private void setMrz(String mrz) {
        TextView mrzTextValue = findViewById(R.id.mrz_value);
        TextView mrzText = findViewById(R.id.mrz_text_view);
        CardView mrzView = findViewById(R.id.mrz_layout);
        if (mrz != null && !mrz.isEmpty()) {
            mrzTextValue.setText(mrz);
        } else {
            mrzText.setVisibility(View.GONE);
            mrzView.setVisibility(View.GONE);
        }
    }

    private void setName(String documentName) {
        TextView nameTextValue = findViewById(R.id.name_value);
        TextView nameText = findViewById(R.id.name_text_view);
        CardView nameView = findViewById(R.id.name_layout);
        if (documentName != null && !documentName.isEmpty()) {
            nameTextValue.setText(documentName);
        } else {
            nameText.setVisibility(View.GONE);
            nameView.setVisibility(View.GONE);
        }
    }

    private void setImage(byte[] documentFrontImage, byte[] documentBackImage) {
        ImageView resultFrontImage = findViewById(R.id.result_front_images);
        ImageView resultBackImage = findViewById(R.id.result_back_images);
        TextView imageText = findViewById(R.id.image_text_view);
        CardView cardView = findViewById(R.id.image_layout);
        if (documentFrontImage != null && documentFrontImage.length != 0) {
            resultFrontImage.setImageBitmap(ImageUtils.transformByteArrayToBitmap(documentFrontImage));
            if (documentBackImage != null && documentBackImage.length != 0) {
                resultBackImage.setImageBitmap(ImageUtils.transformByteArrayToBitmap(documentBackImage));
            } else {
                resultBackImage.setVisibility(View.GONE);
            }
        } else {
            imageText.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }
    }
}

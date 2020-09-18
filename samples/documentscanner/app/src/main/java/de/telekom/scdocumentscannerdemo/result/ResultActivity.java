package de.telekom.scdocumentscannerdemo.result;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import de.telekom.scdocumentscannerdemo.BaseUpActivity;
import de.telekom.scdocumentscannerdemo.R;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public class ResultActivity extends BaseUpActivity {

    public final static String EXTRA_RESULT_BUNDLE = "extra:result_bundle";
    public final static String EXTRA_RESULT_IMAGE = "extra:result_image";
    public final static String EXTRA_RESULT_NAME = "extra:result_name";
    public final static String EXTRA_RESULT_MRZ = "extra:result_mrz";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extraBundle = getIntent().getBundleExtra(EXTRA_RESULT_BUNDLE);
        if (extraBundle != null) {
            setImage(extraBundle.getByteArray(EXTRA_RESULT_IMAGE));
            setName(extraBundle.getString(EXTRA_RESULT_NAME));
            setMrz(extraBundle.getString(EXTRA_RESULT_MRZ));
        }
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

    private void setImage(byte[] documentImage) {
        ImageView resultImage = findViewById(R.id.result_images);
        TextView imageText = findViewById(R.id.image_text_view);
        CardView cardView = findViewById(R.id.image_layout);
        if (documentImage != null) {
            resultImage.setImageBitmap(ImageUtils.transformByteArrayToBitmap(documentImage));
        } else {
            imageText.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
        }
    }
}

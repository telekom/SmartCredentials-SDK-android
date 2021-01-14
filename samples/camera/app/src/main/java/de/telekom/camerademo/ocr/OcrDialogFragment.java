package de.telekom.camerademo.ocr;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import de.telekom.camerademo.R;

/**
 * Created by Alex.Graur@endava.com at 9/3/2020
 */
public class OcrDialogFragment extends DialogFragment {

    public static final String TAG = "ocr_dialog_tag";
    private static final String ARG_OCR_VALUES = "arg:ocr_values";

    private OcrDialogInteractionListener listener;

    public static OcrDialogFragment newInstance(ArrayList<String> ocrValues) {
        OcrDialogFragment dialogFragment = new OcrDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putStringArrayList(ARG_OCR_VALUES, ocrValues);
        dialogFragment.setArguments(arguments);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_ocr, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button okButton = view.findViewById(R.id.ok_button);
        TextView ocrTextView = view.findViewById(R.id.ocr_text_view);

        if (getArguments() != null) {
            List<String> ocrValues = getArguments().getStringArrayList(ARG_OCR_VALUES);

            if (ocrValues != null) {
                ocrTextView.setText(ocrValues.toString());
            }
        }

        okButton.setOnClickListener(view1 -> {
            if (listener != null) {
                listener.onOkButtonClicked();
            }
            dismiss();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OcrDialogInteractionListener) {
            listener = (OcrDialogInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}

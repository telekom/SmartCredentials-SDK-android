package de.telekom.camerademo;

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

/**
 * Created by Alex.Graur@endava.com at 9/2/2020
 */
public class QrResultDialogFragment extends DialogFragment {

    public static final String TAG = "qr_result_dialog_tag";
    private static final String ARG_QR_VALUES = "arg:qr_values";

    private QrResultDialogInteractionListener listener;

    public static QrResultDialogFragment newInstance(ArrayList<String> qrValues) {
        QrResultDialogFragment dialogFragment = new QrResultDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putStringArrayList(ARG_QR_VALUES, qrValues);
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
        return inflater.inflate(R.layout.dialog_qr_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button okButton = view.findViewById(R.id.ok_button);
        TextView qrTextView = view.findViewById(R.id.qr_text_view);

        if (getArguments() != null) {
            List<String> qrCodes = getArguments().getStringArrayList(ARG_QR_VALUES);

            if (qrCodes != null) {
                qrTextView.setText(qrCodes.toString());
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

        if (context instanceof QrResultDialogInteractionListener) {
            listener = (QrResultDialogInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}

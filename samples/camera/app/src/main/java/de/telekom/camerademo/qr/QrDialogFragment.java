package de.telekom.camerademo.qr;

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
 * Created by Alex.Graur@endava.com at 9/2/2020
 */
public class QrDialogFragment extends DialogFragment {

    public static final String TAG = "qr_dialog_tag";
    private static final String ARG_QR_VALUES = "arg:qr_values";

    private QrDialogInteractionListener listener;

    public static QrDialogFragment newInstance(ArrayList<String> qrValues) {
        QrDialogFragment dialogFragment = new QrDialogFragment();
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
        return inflater.inflate(R.layout.dialog_qr, container, false);
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

        if (context instanceof QrDialogInteractionListener) {
            listener = (QrDialogInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}

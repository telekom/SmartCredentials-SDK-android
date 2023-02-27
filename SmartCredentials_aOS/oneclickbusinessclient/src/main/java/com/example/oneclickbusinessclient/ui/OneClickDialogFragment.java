package com.example.oneclickbusinessclient.ui;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

import de.telekom.smartcredentials.oneclickbusinessclient.R;

public class OneClickDialogFragment extends DialogFragment {

    public final static String TAG = "OneClickDialogFragment";
    private final static String ARG_TITLE = "arg:title";
    private final static String ARG_DESCRIPTION = "arg:description";
    private static Activity mActivity = null;
    private static DialogListener mDialogListener;

    public static OneClickDialogFragment newInstance(int titleResId, int descriptionResId, Activity activity, DialogListener dialogListener) {
        OneClickDialogFragment oneClickDialogFragment = new OneClickDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_TITLE, titleResId);
        arguments.putInt(ARG_DESCRIPTION, descriptionResId);
        oneClickDialogFragment.setArguments(arguments);
        mActivity = activity;
        mDialogListener = dialogListener;
        return oneClickDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext(),
                R.style.MaterialRoundedAlertDialogStyle);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_one_click, null);
        TextView titleTextView = view.findViewById(R.id.one_click_dialog_title_text_view);
        titleTextView.setText(getString(getArguments().getInt(ARG_TITLE)));
        TextView descriptionTextView = view.findViewById(R.id.one_click_dialog_description_text_view);
        descriptionTextView.setText(getString(getArguments().getInt(ARG_DESCRIPTION)));
        Button viewCatalogueButton = view.findViewById(R.id.view_catalogue_button);
        viewCatalogueButton.setOnClickListener(v -> {
            mDialogListener.onContinue();
            dismiss();
        });
        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> dismiss());
        builder.setView(view);
        return builder.create();
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}

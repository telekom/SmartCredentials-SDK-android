package de.telekom.scotpdemo.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.telekom.scotpdemo.R;
import de.telekom.smartcredentials.core.model.otp.OTPType;

/**
 * Created by gabriel.blaj@endava.com at 9/1/2020
 */
public class OtpTypeAdapter extends RecyclerView.Adapter<OtpTypeAdapter.ViewHolder> {

    private final List<OTPType> types;
    private final OnOtpTypeInteractionListener listener;

    public OtpTypeAdapter(List<OTPType> types, OnOtpTypeInteractionListener listener) {
        this.types = types;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_otp_type,
                parent, false);
        return new OtpTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(types.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView typeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.type_text_view);
        }

        public void bind(OTPType type, OnOtpTypeInteractionListener listener) {
            typeTextView.setText(type.name());
            itemView.setOnClickListener(view -> listener.onOtpTypeClicked(type));
        }
    }

    public interface OnOtpTypeInteractionListener {

        void onOtpTypeClicked(OTPType type);
    }
}
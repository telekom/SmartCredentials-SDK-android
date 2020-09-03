package de.telekom.scotpdemo.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.telekom.scotpdemo.R;

/**
 * Created by gabriel.blaj@endava.com at 9/2/2020
 */
public class OtpAddModeAdapter extends RecyclerView.Adapter<OtpAddModeAdapter.ViewHolder> {

    final Context context;
    private final List<OtpAddMode> modes;
    private final OnOtpAddTypeInteractionListener listener;

    public OtpAddModeAdapter(Context context, List<OtpAddMode> modes, OnOtpAddTypeInteractionListener listener) {
        this.context = context;
        this.modes = modes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_otp_add_mode,
                parent, false);
        return new OtpAddModeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(modes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return modes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView modeIcon;
        private final TextView modeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modeIcon = itemView.findViewById(R.id.mode_icon);
            modeTextView = itemView.findViewById(R.id.mode_text_view);
        }

        public void bind(OtpAddMode type, OnOtpAddTypeInteractionListener listener) {
            modeIcon.setImageDrawable(ContextCompat.getDrawable(context, type.getIconId()));
            modeTextView.setText(type.getDescription());
            itemView.setOnClickListener(view -> listener.onOtpTypeClicked(type));
        }
    }

    public interface OnOtpAddTypeInteractionListener {

        void onOtpTypeClicked(OtpAddMode type);
    }
}
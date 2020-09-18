package de.telekom.scdocumentscannerdemo.recognizer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.telekom.scdocumentscannerdemo.R;

/**
 * Created by gabriel.blaj@endava.com at 9/16/2020
 */
public class RecognizerTypeAdapter extends RecyclerView.Adapter<RecognizerTypeAdapter.ViewHolder> {

    private final List<RecognizerType> types;
    private final OnRecognizerTypeInteractionListener listener;

    public RecognizerTypeAdapter(List<RecognizerType> types, OnRecognizerTypeInteractionListener listener) {
        this.types = types;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recognizer_type,
                parent, false);
        return new RecognizerTypeAdapter.ViewHolder(view);
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

        public void bind(RecognizerType type, OnRecognizerTypeInteractionListener listener) {
            typeTextView.setText(type.getDesc());
            itemView.setOnClickListener(view -> listener.onRecognizerTypeClicked(type));
        }
    }

    public interface OnRecognizerTypeInteractionListener {

        void onRecognizerTypeClicked(RecognizerType type);
    }
}
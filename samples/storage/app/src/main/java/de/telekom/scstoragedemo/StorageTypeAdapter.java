package de.telekom.scstoragedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Alex.Graur@endava.com at 8/27/2020
 */
public class StorageTypeAdapter extends RecyclerView.Adapter<StorageTypeAdapter.ViewHolder> {

    private final List<StorageType> types;
    private final OnStorageTypeInteractionListener listener;

    public StorageTypeAdapter(List<StorageType> types, OnStorageTypeInteractionListener listener) {
        this.types = types;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storage_type,
                parent, false);
        return new StorageTypeAdapter.ViewHolder(view);
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

        private TextView typeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.type_text_view);
        }

        public void bind(StorageType type, OnStorageTypeInteractionListener listener) {
            typeTextView.setText(type.getResId());
            itemView.setOnClickListener(view -> listener.onStorageTypeClicked(type));
        }
    }
}

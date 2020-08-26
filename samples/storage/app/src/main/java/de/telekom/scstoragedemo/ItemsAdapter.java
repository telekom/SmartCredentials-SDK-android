package de.telekom.scstoragedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;

/**
 * Created by Alex.Graur@endava.com at 8/26/2020
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<ItemEnvelope> items;
    private OnItemInteractionListener listener;

    public ItemsAdapter(List<ItemEnvelope> items, OnItemInteractionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_envelope,
                parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView idTextView;
        private TextView identifierTextView;
        private ImageView deleteImageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.id_text_view);
            identifierTextView = itemView.findViewById(R.id.identifier_text_view);
            deleteImageView = itemView.findViewById(R.id.delete_image_view);
        }

        public void bind(ItemEnvelope item, OnItemInteractionListener listener) {
            idTextView.setText(item.getItemId());
            identifierTextView.setText(item.getIdentifier().toString());
            deleteImageView.setOnClickListener(view -> listener.onDeleteClicked(item));
        }
    }
}

package de.telekom.scstoragedemo.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.List;

import de.telekom.scstoragedemo.DemoApplication;
import de.telekom.scstoragedemo.R;
import de.telekom.scstoragedemo.add.AddItemActivity;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import timber.log.Timber;

/**
 * Created by Alex.Graur@endava.com at 8/26/2020
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private final List<ItemEnvelope> items;
    private final OnItemInteractionListener listener;

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

        private final View rootView;
        private final TextView idTextView;
        private final TextView identifierTextView;
        private final ImageView deleteImageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            idTextView = itemView.findViewById(R.id.id_text_view);
            identifierTextView = itemView.findViewById(R.id.identifier_value_text_view);
            deleteImageView = itemView.findViewById(R.id.delete_image_view);
        }

        public void bind(ItemEnvelope item, OnItemInteractionListener listener) {
            idTextView.setText(item.getItemId());
            try {
                identifierTextView.setText(item.getIdentifier().getString(AddItemActivity.ITEM_KEY_IDENTIFIER));
            } catch (JSONException e) {
                Timber.tag(DemoApplication.TAG).d("Failed to fetch identifier or details.");
            }
            rootView.setOnClickListener(view -> listener.onItemClicked(item));
            deleteImageView.setOnClickListener(view -> listener.onDeleteClicked(item));
        }
    }
}

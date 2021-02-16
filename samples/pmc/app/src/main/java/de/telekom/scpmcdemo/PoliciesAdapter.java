package de.telekom.scpmcdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.telekom.smartcredentials.pmc.Policy;

/**
 * Created by Alex.Graur@endava.com at 2/11/2021
 */
public class PoliciesAdapter extends RecyclerView.Adapter<PoliciesAdapter.ViewHolder> {

    private final List<Policy> schemas;

    public PoliciesAdapter(List<Policy> schemas) {
        this.schemas = schemas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_policy,
                parent, false);
        return new ViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(schemas.get(position));
    }

    @Override
    public int getItemCount() {
        return schemas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
        }

        public void bind(Policy schema) {
            titleTextView.setText(schema.getTitle());
            descriptionTextView.setText(schema.getDescription());
        }
    }
}

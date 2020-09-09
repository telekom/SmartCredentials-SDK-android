package de.telekom.scotpdemo.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.telekom.scotpdemo.view.OtpViewHolder;
import de.telekom.scotpdemo.R;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.model.token.TokenResponse;

/**
 * Created by gabriel.blaj@endava.com at 8/31/2020
 */
public class OtpAdapter extends RecyclerView.Adapter<OtpViewHolder>{

    private OTPType type;
    private List<ItemEnvelope> data = new ArrayList<>();
    private final Map<Integer, TokenResponse> dataMap = new HashMap<>();
    private OtpClickListener listener;

    public OtpAdapter() {
    }

    @NonNull
    @Override
    public OtpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OtpViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_otp, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OtpViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty() && payloads.get(0) instanceof TokenResponse) {
            holder.bind(data.get(position), type, listener, (TokenResponse) payloads.get(0));
        } else if (dataMap.get(position) != null) {
            holder.bind(data.get(position), type, listener, dataMap.get(position));
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OtpViewHolder holder, int position) {
        holder.bind(data.get(position), type, listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(OTPType type, List<ItemEnvelope> data) {
        this.type = type;
        this.data.clear();
        this.dataMap.clear();
        this.data = data;
        notifyDataSetChanged();
    }

    public void updateItem(ItemEnvelope itemEnvelope, TokenResponse tokenResponse, OtpItemsLoading otpItemsLoading) {
        dataMap.put(data.indexOf(itemEnvelope), tokenResponse);
        if (dataMap.size() == data.size() && otpItemsLoading != null) {
            otpItemsLoading.onLoadStarted();
        }
        notifyItemChanged(data.indexOf(itemEnvelope), tokenResponse);
    }

    public void setListener(OtpClickListener listener) {
        this.listener = listener;
    }

    public void removeItem(ItemEnvelope itemEnvelope) {
        int index = data.indexOf(itemEnvelope);
        if (index == dataMap.size() - 1) {
            dataMap.remove(index);
        } else {
            for (int i = index; i < dataMap.size() - 1; i++) {
                dataMap.put(i, dataMap.get(i + 1));
            }
            dataMap.remove(dataMap.size() - 1);
        }
        data.remove(itemEnvelope);
        notifyItemRemoved(index);
    }

    public List<ItemEnvelope> getData() {
        return data;
    }

    public interface OtpClickListener {
        void onOtpAccountClick(ItemEnvelope itemEnvelope);
        void onOptDeleteClick(ItemEnvelope itemEnvelope);
    }

    public interface OtpItemsLoading {
        void onLoadStarted();
    }
}

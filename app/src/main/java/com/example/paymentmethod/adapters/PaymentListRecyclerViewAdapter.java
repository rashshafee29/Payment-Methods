package com.example.paymentmethod.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paymentmethod.R;
import com.example.paymentmethod.models.ListResult;
import com.squareup.picasso.Picasso;

public class PaymentListRecyclerViewAdapter extends RecyclerView.Adapter<PaymentListRecyclerViewAdapter.PaymentListViewHolder> {

    private Context mContext;
    private ListResult mListResult;

    public PaymentListRecyclerViewAdapter(Context applicationContext, ListResult listResult) {
        mContext = applicationContext;
        mListResult = listResult;
    }

    @NonNull
    @Override
    public PaymentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_list_item, parent, false);
        return new PaymentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentListViewHolder holder, int position) {
        holder.mItemName.setText(mListResult.getNetworks().getApplicable().get(position).getLabel());
        String logoUrl = mListResult.getNetworks().getApplicable().get(position).getLinks().getLogo();
        Picasso.get().load(logoUrl).placeholder(R.drawable.circular_progress_animation).into(holder.mItemLogo);
    }

    @Override
    public int getItemCount() {
        return mListResult.getNetworks().getApplicable().size();
    }

    static class PaymentListViewHolder extends RecyclerView.ViewHolder{
        private TextView mItemName;
        private ImageView mItemLogo;

        public PaymentListViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemName = itemView.findViewById(R.id.id_item_text);
            mItemLogo = itemView.findViewById(R.id.id_item_logo);
        }
    }
}

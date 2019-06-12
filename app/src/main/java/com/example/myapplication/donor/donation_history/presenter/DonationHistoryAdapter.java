package com.example.myapplication.donor.donation_history.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewClickListener;
import com.example.myapplication.donor.benificiary_list.model.BenificiaryListModel;
import com.example.myapplication.donor.benificiary_list.presenter.BenificiaryListAdapter;
import com.example.myapplication.donor.donation_history.model.DonationHistoryModel;

import java.util.ArrayList;
import java.util.List;

public class DonationHistoryAdapter extends RecyclerView.Adapter<DonationHistoryAdapter.ViewHolder>  {
    List<DonationHistoryModel> list=new ArrayList<>();
    Context context;
    private RecyclerViewClickListener mListener;
    public DonationHistoryAdapter(List<DonationHistoryModel> list, Context context, RecyclerViewClickListener mListener) {
        this.list = list;
        this.context = context;
        this.mListener=mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_donation_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DonationHistoryModel item = list.get(position);
        holder.tv_city.setText(item.getLoc_name());
        holder.tv_full_name.setText(item.getBeneficaryname());
        holder.tv_amt.setText("Rs "+item.getDonated_amount());
        holder.tv_donor_name.setText(item.getDonerfullname());

        // Picasso.with(context).load(WebServiceURLs.CAT_IMAGE+item.getCat_image()).into(holder.iv_cat_image);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_city)
        TextView tv_city;
        @BindView(R.id.tv_full_name)
        TextView tv_full_name;
        @BindView(R.id.tv_amt)
        TextView tv_amt;
        @BindView(R.id.tv_donor_name)
        TextView tv_donor_name;

        private RecyclerViewClickListener mListener;
        public ViewHolder(View itemView,RecyclerViewClickListener listener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mListener = listener;

        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
}

package com.example.myapplication.donor.benificiary_list.presenter;

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

import java.util.ArrayList;
import java.util.List;


public class BenificiaryListAdapter extends RecyclerView.Adapter<BenificiaryListAdapter.ViewHolder>  {
    List<BenificiaryListModel> list=new ArrayList<>();
    Context context;
    private RecyclerViewClickListener mListener;
    public BenificiaryListAdapter(List<BenificiaryListModel> list, Context context, RecyclerViewClickListener mListener) {
        this.list = list;
        this.context = context;
        this.mListener=mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_benificiary_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BenificiaryListModel item = list.get(position);
        holder.tv_cat.setText(item.getCat_name());
        holder.tv_full_name.setText(item.getFull_name());
        holder.tv_age.append(item.getAge());
        holder.tv_price.append("Rs "+item.getTotal_amount());
        holder.tv_loc.setText(item.getLoc_name());
       // Picasso.with(context).load(WebServiceURLs.CAT_IMAGE+item.getCat_image()).into(holder.iv_cat_image);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_loc)
        public TextView tv_loc;
        @BindView(R.id.btn_donate)
        Button btn_donate;
        @BindView(R.id.btn_view_details)
        Button btn_view_details;
        @BindView(R.id.tv_cat)
        TextView tv_cat;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_age)
        TextView tv_age;
        @BindView(R.id.tv_full_name)
        TextView tv_full_name;

        private RecyclerViewClickListener mListener;
        public ViewHolder(View itemView,RecyclerViewClickListener listener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mListener = listener;
            btn_view_details.setOnClickListener(this);
            btn_donate.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }
}

package com.example.myapplication.donor.home.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.donor.benificiary_list.view.BenificiaryListActivity;
import com.example.myapplication.donor.home.model.HomeModel;
import com.example.myapplication.network.WebServiceURLs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
   List<HomeModel> list=new ArrayList<>();
   Context context;

    public CategoryListAdapter(List<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HomeModel item = list.get(position);
        holder.tv_cat_name.setText(item.getCat_name());
        Picasso.with(context).load(WebServiceURLs.CAT_IMAGE+item.getCat_image()).into(holder.iv_cat_image);

        holder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, BenificiaryListActivity.class);
                i.putExtra("cat_id",item.getCat_id());
                i.putExtra("cat_name",item.getCat_name());
                context.startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cat_image)
        ImageView iv_cat_image;
        @BindView(R.id.tv_cat_name)
        public TextView tv_cat_name;
        @BindView(R.id.cv_main)
        CardView cv_main;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

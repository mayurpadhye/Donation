package com.example.myapplication.benificiary.request_donation;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventImagesAdapter extends RecyclerView.Adapter<EventImagesAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<ImageVideoData> result_list;
    Activity activity;
    String event_from;
    public EventImagesAdapter(Context context, Activity activity, ArrayList<ImageVideoData> asm_list, String event_from) {

        this.mContext = context;
        this.result_list = asm_list;
        this.activity=activity;
        this.event_from=event_from;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_creation, parent, false);
        return new EventImagesAdapter.MyViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ImageVideoData imageVideoData=result_list.get(position);
        if(result_list.get(position).getBitmap()!=null){
            holder.iv_activity.setImageBitmap(result_list.get(position).getBitmap());


           // Picasso.with(mContext).load(result_list.get(position).getPath()).into(holder.iv_activity);
            // Picasso.with(mctx).load(WebServiceURLs.SHOP_IMAGE + items.getImage2()).into(holder.iv_product_image2);



            Log.i("imagePathhhhhBitmap",result_list.get(position).getPath());
        }else{
            Picasso.with(mContext).load(result_list.get(position).getPath()).into(holder.iv_activity);
        }

    }


    @Override
    public int getItemCount() {
        return result_list.size();
    }


    @Override
    public long getItemId(int i) {
        return i;
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iv_activity,iv_icon,iv_remove;

        MyViewHolder(View view) {
            super(view);
            iv_activity = view.findViewById(R.id.img_event_file);
            iv_activity.setOnClickListener(this);
            iv_remove=view.findViewById(R.id.img_remove);
            // iv_remove.setOnClickListener(this);
            iv_icon = view.findViewById(R.id.img_icon);
            iv_icon.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition=this.getAdapterPosition();
            switch (v.getId())
            {
                case R.id.img_event_file:
                    ArrayList<String> arrayList=new ArrayList<>();
                    for(int i=0;i<result_list.size();i++){
                        arrayList.add(result_list.get(i).getPath());
                    }
                    break;




            }
        }
    }
}

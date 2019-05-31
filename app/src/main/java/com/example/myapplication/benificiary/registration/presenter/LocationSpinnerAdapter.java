package com.example.myapplication.benificiary.registration.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.registration.model.LocationModel;

import java.util.List;

public class LocationSpinnerAdapter extends ArrayAdapter<LocationModel> {
    LayoutInflater flater;

    public LocationSpinnerAdapter(Activity context, int resouceId, int textviewId, List<LocationModel> list){

        super(context,resouceId,textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        LocationModel rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.row_location, null, false);

            holder.tv_loc = (TextView) rowview.findViewById(R.id.tv_loc);

            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        holder.tv_loc.setText(rowItem.getLoc_name());


        return rowview;
    }

    private class viewHolder{
        TextView tv_loc;

    }


}

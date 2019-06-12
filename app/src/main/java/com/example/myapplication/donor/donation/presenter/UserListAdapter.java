package com.example.myapplication.donor.donation.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.registration.model.LocationModel;
import com.example.myapplication.benificiary.registration.presenter.LocationSpinnerAdapter;
import com.example.myapplication.donor.donation.model.UserListModel;

import java.util.List;

public class UserListAdapter  extends ArrayAdapter<UserListModel> {
    LayoutInflater flater;

    public UserListAdapter(Activity context, int resouceId, int textviewId, List<UserListModel> list) {

        super(context, resouceId, textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView, position);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        UserListModel rowItem = getItem(position);

        viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.row_user_list, null, false);

            holder.tv_user = (TextView) rowview.findViewById(R.id.tv_user);

            rowview.setTag(holder);
        } else {
            holder = (viewHolder) rowview.getTag();
        }

        holder.tv_user.setText(rowItem.getUser_name());


        return rowview;
    }

    private class viewHolder {
        TextView tv_user;

    }
}

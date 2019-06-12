package com.example.myapplication.donor.donation.model;

import com.example.myapplication.benificiary.login.model.UserModel;

import java.util.List;

public interface IUserListModel {
    interface onUserListListener{
        void onUserListFinished(String status,List<UserListModel> userModelList);
        void onUserListFailure(Throwable t);
    }

    public void getUserList(String loc_id,String cat_id,onUserListListener onUserListListener);


}

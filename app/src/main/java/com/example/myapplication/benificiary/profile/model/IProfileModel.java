package com.example.myapplication.benificiary.profile.model;

public interface IProfileModel {
    interface  onProfileDataListener
    {
        void onProfileDataFinished(String full_name,String age,String mobile_no,String profile_pic,String total_amount,String loc_name,String cat_name);
        void onProfileDataFailue(Throwable t);
    }
    public void getProfileData(String user_id,onProfileDataListener onProfileDataListener);
}

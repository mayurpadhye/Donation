package com.example.myapplication.benificiary.profile.view;

public interface IAccountView {
    public void setVisibility(int visibility);
    public void getProfileData(String full_name,String age,String mobile_no,String profile_pic,String total_amount,String loc_name,String cat_name);
    public void getProfileDataFailure(Throwable t);
}

package com.example.myapplication.benificiary.profile.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.example.myapplication.benificiary.profile.model.IProfileModel;
import com.example.myapplication.benificiary.profile.model.ProfileModel;
import com.example.myapplication.benificiary.profile.view.IAccountView;
import com.example.myapplication.helperClass.PrefManager;

public class ProfilePresenter implements IProfilePresenter,IProfileModel.onProfileDataListener {
   Context context;
   IAccountView iAccountView;
IProfileModel iProfileModel;
    public ProfilePresenter(Context context, IAccountView iAccountView) {
        this.context = context;
        this.iAccountView = iAccountView;
        iProfileModel=new ProfileModel();
    }

    @Override
    public void requestProfileData() {
        Toast.makeText(context, ""+PrefManager.getInstance(context).getUserId(), Toast.LENGTH_SHORT).show();
iProfileModel.getProfileData(PrefManager.getInstance(context).getUserId(),this);
    }

    @Override
    public void onProfileDataFinished(String full_name, String age, String mobile_no, String profile_pic, String total_amount, String loc_name, String cat_name) {

        iAccountView.setVisibility(View.GONE);
        iAccountView.getProfileData(full_name,age,mobile_no,profile_pic,total_amount,loc_name,cat_name);


    }

    @Override
    public void onProfileDataFailue(Throwable t) {
iAccountView.setVisibility(View.GONE);
iAccountView.getProfileDataFailure(t);
    }
}

package com.example.myapplication.benificiary.registration.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.example.myapplication.benificiary.registration.model.*;
import com.example.myapplication.benificiary.registration.view.IRegistrationView;

import java.util.List;


public class RegistrationCompl implements IRegistrationPresenter,ILocationModel.onLocationFinishListener,IUserRegis.onRegistrationRequestListener,IUserRegis.onCategoryListListener {
    Context context;
    IRegistrationView iRegistrationView;
    ILocationModel iLocationModel;
int check_validation=0;
IUserRegis iUserRegis;
    public RegistrationCompl(Context context, IRegistrationView iRegistrationView) {
        this.context = context;
        this.iRegistrationView = iRegistrationView;
        iLocationModel=new LocationModel();
         intRegis();

    }

    public void intRegis()
    {
        iUserRegis=new UserRegistrationModel(context);
    }

    @Override
    public void clearText() {

    }

    @Override
    public void checkValidation(String name,String age,String cat_id,String location_id,String mobile_no,String pass,String role) {

       if (name.isEmpty())
       {
           iRegistrationView.validationResult(0,1);
       }
       else if (age.isEmpty())
       {
           iRegistrationView.validationResult(0,2);
       }
       else if (cat_id.isEmpty())
       {
           iRegistrationView.validationResult(0,3);
       }

       else if (location_id.isEmpty())
       {
           iRegistrationView.validationResult(0,4);
       }
       else if (!isValidPhone(mobile_no))
       {
           iRegistrationView.validationResult(0,5);
       }
       else if (pass.isEmpty())
       {
           iRegistrationView.validationResult(0,6);
       }
       else
       {
           iRegistrationView.validationResult(1,7);
       }
    }
    public boolean isValidPhone(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    @Override
    public void doRegistration(String name,String age,String cat_id,String location_id,String mobile_no,String pass,String role) {

        iRegistrationView.setProgressVisibility(View.VISIBLE);
       iUserRegis.signUpUser(name,age,cat_id,location_id,mobile_no,pass,role,this);


    }

    @Override
    public void setProgressVisibility(int visibility) {

    }

    @Override
    public void requesrLOcationDetails() {
        iLocationModel.getLocationResult(this);
        iRegistrationView.setProgressVisibility(View.VISIBLE);
    }

    @Override
    public void requestCategoryData() {
        iUserRegis.getCategoryList(this);
    }


    @Override
    public void onLocationRequestFinish(List<LocationModel> locationModelList) {
        iRegistrationView.getLocationList(locationModelList);
        iRegistrationView.setProgressVisibility(View.GONE);
    }

    @Override
    public void onLocationRequestFailure(Throwable t) {
        iRegistrationView.getLocationFailure(t);
        iRegistrationView.setProgressVisibility(View.GONE);

    }

    @Override
    public void onRegistrationFinshedListener(String status, String message) {
        iRegistrationView.setProgressVisibility(View.GONE);
iRegistrationView.RegistrationResult(status,message);
    }

    @Override
    public void RegistrationFailureListener(Throwable t) {
        iRegistrationView.setProgressVisibility(View.GONE);
        iRegistrationView.RegistrationFailure(t);
    }

    @Override
    public void onCategoryListFinished(List<CategoryListModel> list) {
        iRegistrationView.setProgressVisibility(View.GONE);
        iRegistrationView.getCategoryList(list);
    }

    @Override
    public void onCategoryListFailure(Throwable t) {
        iRegistrationView.setProgressVisibility(View.GONE);
        iRegistrationView.getCategoryListFailure(t);
    }
}

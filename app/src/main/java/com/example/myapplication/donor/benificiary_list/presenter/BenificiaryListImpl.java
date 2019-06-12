package com.example.myapplication.donor.benificiary_list.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.example.myapplication.benificiary.login.model.IUser;
import com.example.myapplication.benificiary.login.model.UserModel;
import com.example.myapplication.benificiary.registration.model.ILocationModel;
import com.example.myapplication.benificiary.registration.model.IUserRegis;
import com.example.myapplication.benificiary.registration.model.LocationModel;
import com.example.myapplication.donor.benificiary_list.model.BenificiaryListModel;
import com.example.myapplication.donor.benificiary_list.model.IBenificiaryListModel;
import com.example.myapplication.donor.benificiary_list.view.IBenificiaryListView;
import com.example.myapplication.helperClass.PrefManager;
import org.json.JSONObject;

import java.util.List;


public class BenificiaryListImpl implements IBenificiaryListPresenter,IBenificiaryListModel.onBenificiaryListListener,IBenificiaryListModel.onDonorRegistration,ILocationModel.onLocationFinishListener, IUser.onLoginRquestListener {
    Context context;
    IBenificiaryListView iBenificiaryListView;
    IBenificiaryListModel iBenificiaryListModel;
String cat_id;
ILocationModel iLocationModel;
IUser iUser;
    public BenificiaryListImpl(Context context, IBenificiaryListView iBenificiaryListView,String cat_id) {
        this.context = context;
        this.iBenificiaryListView = iBenificiaryListView;
        iBenificiaryListModel=new BenificiaryListModel();
        this.cat_id=cat_id;
        iLocationModel=new LocationModel();
        iUser=new UserModel();
    }

    @Override
    public void requestBenificiaryList() {
        iBenificiaryListView.setProgressVisibility(View.VISIBLE);
        iBenificiaryListModel.getBenificairyList(PrefManager.getInstance(context).getLocationId(),cat_id,this);
    }

    @Override
    public void doDonorRegistration(String name, String age, String mobile, String password,String loc_id) {
        iBenificiaryListView.setProgressVisibility(View.VISIBLE);
iBenificiaryListModel.doRegistration( name,  age,  mobile,  password,loc_id,this);
    }

    @Override
    public void getLocationDetails() {
        iBenificiaryListView.setProgressVisibility(View.VISIBLE);
        iLocationModel.getLocationResult(this);
    }

    @Override
    public void validateData(String name, String age, String mobile, String password, String loc_id) {
        if (name.isEmpty())
        {
            iBenificiaryListView.getValidationResult(1);
        }
        else if (age.isEmpty())
        {
            iBenificiaryListView.getValidationResult(2);
        }


        else if (loc_id.isEmpty())
        {
            iBenificiaryListView.getValidationResult(3);
        }
        else if (!isValidPhone(mobile))
        {
            iBenificiaryListView.getValidationResult(4);
        }
        else if (password.isEmpty())
        {
            iBenificiaryListView.getValidationResult(5);
        }
        else
        {
            iBenificiaryListView.getValidationResult(6);
        }
    }

    @Override
    public void doLogin(String user_name, String password, String role) {
        iUser.doLogin(user_name,password,role,this);
    }

    @Override
    public void onBenificairyListFinished(List<BenificiaryListModel> benificiaryListModels) {
        iBenificiaryListView.setProgressVisibility(View.GONE);
        iBenificiaryListView.getBenificiaryList(benificiaryListModels);
    }

    @Override
    public void onBenificiaryListFailure(Throwable t) {
        iBenificiaryListView.setProgressVisibility(View.GONE);
        iBenificiaryListView.getBenificiaryListFailure(t);

    }

    @Override
    public void onDonorRegistrationFinished(String status, String message,String user_id) {
        iBenificiaryListView.setProgressVisibility(View.GONE);
        iBenificiaryListView.getDonorRegistrationResult(status,message,user_id);
    }

    @Override
    public void onDonorRegistrationFailure(Throwable t) {
       // iBenificiaryListView.get(status,message);
        iBenificiaryListView.setProgressVisibility(View.GONE);
    }

    @Override
    public void onLocationRequestFinish(List<LocationModel> locationModelList) {
        iBenificiaryListView.setProgressVisibility(View.GONE);
        iBenificiaryListView.getLocation(locationModelList);
    }

    @Override
    public void onLocationRequestFailure(Throwable t) {
        iBenificiaryListView.setProgressVisibility(View.GONE);
    }

    public boolean isValidPhone(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    @Override
    public void onLoginRequestFinished(JSONObject jsonObject) {
        iBenificiaryListView.getLoginResult(jsonObject);
        iBenificiaryListView.setProgressVisibility(View.GONE);
    }

    @Override
    public void onLoginRequestFailure(Throwable t) {
        iBenificiaryListView.setProgressVisibility(View.GONE);
    }
}

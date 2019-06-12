package com.example.myapplication.donor.benificiary_list.presenter;

import com.example.myapplication.benificiary.registration.model.LocationModel;

import java.util.List;

public interface IBenificiaryListPresenter {
    public void requestBenificiaryList();
    public void doDonorRegistration(String name,String age,String mobile,String password,String loc_id);
    public void getLocationDetails();
    public void validateData(String name,String age,String mobile,String password,String loc_id);
    public void doLogin(String user_name,String password,String role);

}

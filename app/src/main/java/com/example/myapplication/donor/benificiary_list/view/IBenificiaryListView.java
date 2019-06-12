package com.example.myapplication.donor.benificiary_list.view;

import com.example.myapplication.benificiary.registration.model.LocationModel;
import com.example.myapplication.donor.benificiary_list.model.BenificiaryListModel;
import org.json.JSONObject;

import java.util.List;

public interface IBenificiaryListView {

    public void setProgressVisibility(int visibility);
    public void getBenificiaryList(List<BenificiaryListModel> benificiaryListModelList);
    public void getBenificiaryListFailure(Throwable t);
    public void getValidationResult(int position);
    public void getDonorRegistrationResult(String status,String message,String user_id);
    public void getLocation(List<LocationModel> locationModelList);
    public void getLoginResult(JSONObject jsonObject);





}

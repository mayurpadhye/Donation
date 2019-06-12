package com.example.myapplication.donor.donation.view;

import com.example.myapplication.benificiary.login.model.UserModel;
import com.example.myapplication.benificiary.registration.model.CategoryListModel;
import com.example.myapplication.benificiary.registration.model.LocationModel;
import com.example.myapplication.donor.donation.model.UserListModel;

import java.util.List;

public interface IDonationView  {

    public void setProgressVisibilty(int visibility);
    public void getCategoryList(List<CategoryListModel> categoryListModelsList);
    public void getLocationList(List<LocationModel> locationModelList);
    public void getUserList(List<UserListModel> userListModelList);
    public void setImageAdapter();
    public void notifyDataSetChanged();
    public void getValidationResult(int position);
    public void getDonationFormResult(String status,String message);
    public void getDoantionFormFailure(Throwable t);

}

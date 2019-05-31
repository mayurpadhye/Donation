package com.example.myapplication.benificiary.registration.view;

import com.example.myapplication.benificiary.registration.model.CategoryListModel;
import com.example.myapplication.benificiary.registration.model.LocationModel;

import java.util.List;

public interface IRegistrationView {

    public void RegistrationResult(String result,String  message);
    public void RegistrationFailure(Throwable t);
    public void setProgressVisibility(int visibility);
    public void getLocationList(List<LocationModel> locationModelList);
    public void getLocationFailure(Throwable t);
    public void validationResult(int status,int position);
    public void getCategoryList(List<CategoryListModel> list);
    public void getCategoryListFailure(Throwable t);



}

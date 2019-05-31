package com.example.myapplication.donor.select_location.view;

import com.example.myapplication.benificiary.registration.model.LocationModel;

import java.util.List;

public interface ISelectLocationView {
    public  void setVisibility(int visibility);
    public void getAllLocation(List<LocationModel> locationModelList);
    public void onFailure(Throwable t);
}

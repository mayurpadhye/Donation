package com.example.myapplication.donor.home.view;

import com.example.myapplication.donor.home.model.HomeModel;

import java.util.List;

public interface IHomeView {
    public void getAllCategories(List<HomeModel> homeModelList);
    public void setVisibility(int Visibility);
    public void onCategoryRequestFailure(Throwable t);
}

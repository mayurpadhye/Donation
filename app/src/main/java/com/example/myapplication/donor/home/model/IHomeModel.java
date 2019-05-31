package com.example.myapplication.donor.home.model;

import java.util.List;

public interface IHomeModel {
    interface onCategoryRequestFinishedListener
    {
        void onCategoryRequestFinished(List<HomeModel> homeModelList);
        void onCategoryRequestFailure(Throwable t);
    }

    public void getAllCategory(onCategoryRequestFinishedListener onCategoryRequestFinishedListener);
}

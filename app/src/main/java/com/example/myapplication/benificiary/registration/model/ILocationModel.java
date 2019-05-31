package com.example.myapplication.benificiary.registration.model;

import java.util.List;

public interface ILocationModel {

    interface onLocationFinishListener
    {
        void onLocationRequestFinish(List<LocationModel> locationModelList);
        void onLocationRequestFailure(Throwable t);


    }

    void getLocationResult(onLocationFinishListener onLocationFinishListener);

}

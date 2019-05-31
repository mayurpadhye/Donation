package com.example.myapplication.donor.select_location.presenter;

import android.content.Context;
import android.view.View;
import com.example.myapplication.benificiary.registration.model.ILocationModel;
import com.example.myapplication.benificiary.registration.model.LocationModel;
import com.example.myapplication.donor.select_location.view.ISelectLocationView;

import java.util.List;

public class SelectLocationPresenterCompl implements ISelectLocationPresenter, ILocationModel.onLocationFinishListener {
    Context context;
    ISelectLocationView iSelectLocationView;
    ILocationModel iLocationModel;

    public SelectLocationPresenterCompl(Context context, ISelectLocationView iSelectLocationView) {
        this.context = context;
        this.iSelectLocationView = iSelectLocationView;
         iLocationModel=new LocationModel();
    }

    @Override
    public void requestLocarionData() {
        iSelectLocationView.setVisibility(View.VISIBLE);
        iLocationModel.getLocationResult(this);
    }

    @Override
    public void onLocationRequestFinish(List<LocationModel> locationModelList) {
        iSelectLocationView.setVisibility(View.GONE);
        iSelectLocationView.getAllLocation(locationModelList);
    }

    @Override
    public void onLocationRequestFailure(Throwable t) {
        iSelectLocationView.setVisibility(View.GONE);
        iSelectLocationView.onFailure(t);

    }
}

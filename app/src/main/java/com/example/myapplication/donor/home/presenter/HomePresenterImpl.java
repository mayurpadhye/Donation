package com.example.myapplication.donor.home.presenter;

import android.content.Context;
import android.view.View;
import com.example.myapplication.donor.home.model.HomeModel;
import com.example.myapplication.donor.home.model.IHomeModel;
import com.example.myapplication.donor.home.view.IHomeView;

import java.util.List;

public class HomePresenterImpl implements IHomePresenter, IHomeModel.onCategoryRequestFinishedListener {
    Context context;
    IHomeView iHomeView;
IHomeModel iHomeModel;
    public HomePresenterImpl(Context context, IHomeView iHomeView) {
        this.context = context;
        this.iHomeView = iHomeView;
        iHomeModel=new HomeModel();
    }

    @Override
    public void requestCategoryData() {
        iHomeView.setVisibility(View.VISIBLE);
        iHomeModel.getAllCategory(this);
    }

    @Override
    public void onCategoryRequestFinished(List<HomeModel> homeModelList) {
        iHomeView.getAllCategories(homeModelList);
        iHomeView.setVisibility(View.GONE);
    }

    @Override
    public void onCategoryRequestFailure(Throwable t) {
        iHomeView.onCategoryRequestFailure(t);
        iHomeView.setVisibility(View.GONE);
    }
}

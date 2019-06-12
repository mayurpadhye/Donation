package com.example.myapplication.benificiary.home.view;

import com.example.myapplication.benificiary.home.model.HomeFragmentModel;

import java.util.List;

public interface IHomeFragmentView {
    public void setProgressIndiactior(int visibility);
    public void getDonationList(List<HomeFragmentModel> homeFragmentModelList);
    public void changeStatusResult(String status,String message);
    public void changeStatusListenerFailure(Throwable t);
}

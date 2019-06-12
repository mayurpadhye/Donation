package com.example.myapplication.benificiary.home.presenter;

import android.content.Context;
import android.view.View;
import com.example.myapplication.benificiary.home.model.HomeFragmentModel;
import com.example.myapplication.benificiary.home.model.IHomeFragmentModel;
import com.example.myapplication.benificiary.home.view.IHomeFragmentView;
import com.example.myapplication.helperClass.PrefManager;

import java.util.List;

public class DonationDetailsPresenterImpl implements  IDonationDetails,IHomeFragmentModel.onDonationDetailListener,IHomeFragmentModel.onChangeStatusListener {
    Context  context;
    IHomeFragmentView iHomeFragmentView;
IHomeFragmentModel iHomeFragmentModel;
    public DonationDetailsPresenterImpl(Context context, IHomeFragmentView iHomeFragmentView) {
        this.context = context;
        this.iHomeFragmentView = iHomeFragmentView;
        iHomeFragmentModel=new HomeFragmentModel();
    }

    @Override
    public void getDonationDetails() {
        iHomeFragmentView.setProgressIndiactior(View.VISIBLE);
        iHomeFragmentModel.getDonationDetails(PrefManager.getInstance(context).getUserId(),this);
    }

    @Override
    public void changeDonationStatus(String user_id, String status) {
        iHomeFragmentView.setProgressIndiactior(View.VISIBLE);
iHomeFragmentModel.changeStatus(user_id,this);
    }

    @Override
    public void onDonationDetailsFinished(String status, List<HomeFragmentModel> homeFragmentModelList) {
        iHomeFragmentView.setProgressIndiactior(View.GONE);
        iHomeFragmentView.getDonationList(homeFragmentModelList);
    }

    @Override
    public void onDonationFailuer(Throwable t) {
        iHomeFragmentView.setProgressIndiactior(View.GONE);
    }

    @Override
    public void onChangeStatusFinished(String status, String message) {
        iHomeFragmentView.setProgressIndiactior(View.GONE);
        iHomeFragmentView.changeStatusResult(status,message);
    }

    @Override
    public void onChangeStatusFailure(Throwable t) {
        iHomeFragmentView.setProgressIndiactior(View.GONE);
        iHomeFragmentView.changeStatusListenerFailure(t);

    }
}

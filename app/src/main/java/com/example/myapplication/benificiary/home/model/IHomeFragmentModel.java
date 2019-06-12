package com.example.myapplication.benificiary.home.model;

import java.util.List;

public interface IHomeFragmentModel {
    interface onDonationDetailListener
    {
        void onDonationDetailsFinished(String status, List<HomeFragmentModel> homeFragmentModelList);
        void onDonationFailuer(Throwable t);
    }
    interface  onChangeStatusListener{
        void onChangeStatusFinished(String status,String message);
        void onChangeStatusFailure(Throwable t);


    }

    public void getDonationDetails(String user_id,onDonationDetailListener onDonationDetailListener);
    public void changeStatus(String user_id,onChangeStatusListener onChangeStatusListener);

}

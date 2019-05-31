package com.example.myapplication.benificiary.request_donation.presenter;

import android.content.Context;
import android.view.View;
import com.example.myapplication.benificiary.request_donation.model.DonationRequestModel;
import com.example.myapplication.benificiary.request_donation.model.IDonationRequestModel;
import com.example.myapplication.benificiary.request_donation.view.IDonationRequestView;
import com.example.myapplication.helperClass.PrefManager;

import java.io.File;
import java.util.List;

public class DonationRequestPresenter implements IDonationRequestPresenter,IDonationRequestModel.donationRequestListener {
    Context context;
    IDonationRequestView iDonationRequestView;
IDonationRequestModel iDonationRequestModel;
    public DonationRequestPresenter(Context context, IDonationRequestView iDonationRequestView) {
        this.context = context;
        this.iDonationRequestView = iDonationRequestView;
         iDonationRequestModel=new DonationRequestModel();

    }

    @Override
    public void requestDonation(String amount, String desc, List<File> imagesFile) {
        iDonationRequestView.setProgressVisibility(View.VISIBLE);
        iDonationRequestModel.requestForDonation(amount,desc,imagesFile, PrefManager.getInstance(context).getUserId(),this);

    }

    @Override
    public void onDonationRequestFinished(String status) {
        iDonationRequestView.setProgressVisibility(View.GONE);
        iDonationRequestView.getDonationRequestResult(status);
    }

    @Override
    public void onDonationRequestFailure(Throwable t) {
        iDonationRequestView.setProgressVisibility(View.GONE);
        iDonationRequestView.getDonationRequestFailureResult(t);
    }
}

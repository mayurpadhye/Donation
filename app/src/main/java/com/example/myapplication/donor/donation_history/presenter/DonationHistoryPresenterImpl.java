package com.example.myapplication.donor.donation_history.presenter;

import android.content.Intent;
import android.view.View;
import com.example.myapplication.donor.donation.presenter.IDonationPresenter;
import com.example.myapplication.donor.donation_history.model.DonationHistoryModel;
import com.example.myapplication.donor.donation_history.model.IDonationHistoryModel;
import com.example.myapplication.donor.donation_history.view.IDonationHistoryView;

import java.io.File;
import java.util.List;

public class DonationHistoryPresenterImpl implements IDonationHistoryPresenter,IDonationHistoryModel.onDonationHistoryListener {
    IDonationHistoryView iDonationHistoryView;
IDonationHistoryModel iDonationHistoryModel;
    public DonationHistoryPresenterImpl(IDonationHistoryView iDonationHistoryView) {
        this.iDonationHistoryView = iDonationHistoryView;
        iDonationHistoryModel=new DonationHistoryModel();

    }


    @Override
    public void requestDonationHistory() {
        iDonationHistoryView.setProgressVisibility(View.VISIBLE);
        iDonationHistoryModel.getDonationHistory(this);
    }

    @Override
    public void onDonationHistoryFinished(String total_amount,List<DonationHistoryModel> donationHistoryModelList) {
        iDonationHistoryView.setProgressVisibility(View.GONE);
        iDonationHistoryView.getDonationHistory(total_amount,donationHistoryModelList);
    }

    @Override
    public void onDonationFailure(Throwable t) {
        iDonationHistoryView.setProgressVisibility(View.GONE);
        iDonationHistoryView.donationHistoryFailure(t);

    }
}

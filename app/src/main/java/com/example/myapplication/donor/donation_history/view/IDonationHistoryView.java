package com.example.myapplication.donor.donation_history.view;

import com.example.myapplication.donor.donation_history.model.DonationHistoryModel;

import java.util.List;

public interface IDonationHistoryView {
    public void getDonationHistory(List<DonationHistoryModel> donationHistoryModelList);
    public void donationHistoryFailure(Throwable t);
    public void setProgressVisibility(int visibility);

}

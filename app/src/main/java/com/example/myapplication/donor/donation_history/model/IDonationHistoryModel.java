package com.example.myapplication.donor.donation_history.model;

import java.util.List;

public interface IDonationHistoryModel {

    interface onDonationHistoryListener
    {
        void onDonationHistoryFinished(String total_amount,List<DonationHistoryModel> donationHistoryModelList);
        void onDonationFailure(Throwable t);
    }

    public void getDonationHistory(onDonationHistoryListener onDonationHistoryListener);
}

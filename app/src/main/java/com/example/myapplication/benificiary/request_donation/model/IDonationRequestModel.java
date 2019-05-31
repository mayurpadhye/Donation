package com.example.myapplication.benificiary.request_donation.model;

import java.io.File;
import java.util.List;

public interface IDonationRequestModel {

    interface donationRequestListener
    {
        void onDonationRequestFinished(String status);
        void onDonationRequestFailure(Throwable t);
    }

    public void requestForDonation(String donation_amount, String desc, List<File> imageFiles,String user_id,donationRequestListener donationRequestListener);
}

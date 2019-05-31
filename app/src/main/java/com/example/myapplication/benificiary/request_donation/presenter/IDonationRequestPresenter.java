package com.example.myapplication.benificiary.request_donation.presenter;

import java.io.File;
import java.util.List;

public interface IDonationRequestPresenter {

    public void requestDonation(String amount, String desc, List<File>imagesFile);
}

package com.example.myapplication.benificiary.request_donation.presenter;

import android.content.Intent;

import java.io.File;
import java.util.List;

public interface IDonationRequestPresenter {

    public void requestDonation(String amount, String desc, List<File>imagesFile);
    public void validateData(String amount, String desc, List<File>imagesFile);
    public void selectImage();
    public void cameraImageIntent();
    public void galleryImageIntent();
    public void getOnActivityResult(int requestCode, int resultCode, Intent data);


}

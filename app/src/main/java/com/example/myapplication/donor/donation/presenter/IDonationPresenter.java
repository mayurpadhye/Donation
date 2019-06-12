package com.example.myapplication.donor.donation.presenter;

import android.content.Intent;

import java.io.File;
import java.util.List;

public interface IDonationPresenter {
    public void getCategory();
    public void getLocation();
    public void getUserList(String loc_id,String cat_id);
    public void selectImage();
    public void cameraImageIntent();
    public void galleryImageIntent();
    public void getOnActivityResult(int requestCode, int resultCode, Intent data);
    public void validateData(String cat_id, String loc_id, String beneficiary_id, String donated_amount, String donor_id, String mode_of_payment, List<File> img_file,String ref_no);
    public void submitDonationForm(String cat_id, String loc_id, String beneficiary_id, String donated_amount, String donor_id, String mode_of_payment, List<File> img_file,String ref_no);

}

package com.example.myapplication.donor.donation.model;

import java.io.File;
import java.util.List;

public interface IDonationModel {
    interface donationFormListener
    {
        void donationFormFinished(String status,String mesage);
        void donationFailure(Throwable t);

    }
    public  void submitDonationForm(String cat_id, String loc_id, String beneficiary_id, String donated_amount, String donor_id, String mode_of_payment, List<File> img_file, String ref_no,donationFormListener donationFormListener);
}

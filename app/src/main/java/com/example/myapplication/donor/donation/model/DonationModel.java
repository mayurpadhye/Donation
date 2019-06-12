package com.example.myapplication.donor.donation.model;

import android.util.Log;
import com.example.myapplication.donor.benificiary_list.model.IBenificiaryListModel;
import com.example.myapplication.network.RestInterface;
import com.example.myapplication.network.RetrofitClient;
import com.example.myapplication.network.WebServiceURLs;
import com.google.gson.JsonElement;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

import java.io.File;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class DonationModel implements IDonationModel {
    @Override
    public void submitDonationForm(String cat_id, String loc_id, String beneficiary_id, String donated_amount, String donor_id, String mode_of_payment, List<File> img_file, String ref_no, final donationFormListener donationFormListener) {


        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        multipartTypedOutput.addPart("loct_id", new TypedString(loc_id));
        multipartTypedOutput.addPart("cate_id", new TypedString(cat_id));
        multipartTypedOutput.addPart("donated_amount", new TypedString(donated_amount));
        multipartTypedOutput.addPart("donted_id", new TypedString(donor_id));
        multipartTypedOutput.addPart("beneficary_id", new TypedString(beneficiary_id));
        multipartTypedOutput.addPart("mode_payemnt", new TypedString(mode_of_payment));
        multipartTypedOutput.addPart("refnumber", new TypedString(ref_no));


        if (img_file.size() > 0) {
            for (int i = 0; i < img_file.size(); i++) {
                multipartTypedOutput.addPart("recp_img", new TypedFile("application/octet-stream", new File(img_file.get(i).getAbsolutePath())));
            }
        } else {
            multipartTypedOutput.addPart("recp_img", new TypedString(""));
        }



        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.add_donation_details(multipartTypedOutput, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    donationFormListener.donationFormFinished(status,message);
                }
                catch (JSONException | NullPointerException e) {
                    donationFormListener.donationFailure(e);
                    e.printStackTrace();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: " + error);
                donationFormListener.donationFailure(error);
            }
        });
    }
    }


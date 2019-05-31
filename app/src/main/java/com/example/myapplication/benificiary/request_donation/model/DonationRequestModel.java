package com.example.myapplication.benificiary.request_donation.model;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.myapplication.helperClass.PrefManager;
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

public class DonationRequestModel implements  IDonationRequestModel
{

    @Override
    public void requestForDonation(String donation_amount, String desc, List<File> imageFiles, String user_id, final donationRequestListener donationRequestListener) {
        try {

            MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
            multipartTypedOutput.addPart("user_id", new TypedString(user_id));
            multipartTypedOutput.addPart("desc", new TypedString(desc));
            multipartTypedOutput.addPart("total_amount", new TypedString(donation_amount));


            if (imageFiles.size() > 0) {
                for (int i = 0; i < imageFiles.size(); i++) {
                    multipartTypedOutput.addPart("img[]", new TypedFile("application/octet-stream", new File(imageFiles.get(i).getAbsolutePath())));
                }
            } else {
                multipartTypedOutput.addPart("img", new TypedString(""));
            }


            RetrofitClient retrofitClient = new RetrofitClient();
            RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
            service.request_donation(
                    multipartTypedOutput, new Callback<JsonElement>() {
                        @Override
                        public void success(JsonElement jsonElement, Response response) {
                            //this method call if webservice success
                            try {
                                JSONObject jsonObject = new JSONObject(jsonElement.toString());
                                String status = jsonObject.getString("status");

                                donationRequestListener.onDonationRequestFinished(status);


                            } catch (JSONException | NullPointerException e) {
                                e.printStackTrace();
                                donationRequestListener.onDonationRequestFailure(e);

                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            donationRequestListener.onDonationRequestFailure(error);
                            Log.i("fdfdfdfdfdf", "" + error.getMessage());

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

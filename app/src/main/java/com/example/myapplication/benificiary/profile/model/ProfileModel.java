package com.example.myapplication.benificiary.profile.model;

import com.example.myapplication.network.RestInterface;
import com.example.myapplication.network.RetrofitClient;
import com.example.myapplication.network.WebServiceURLs;
import com.google.gson.JsonElement;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfileModel implements IProfileModel {
    @Override
    public void getProfileData(String user_id, final onProfileDataListener onProfileDataListener) {
        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.get_user_profile(user_id,new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());

                    String status=jsonObject.getString("status");
                    if (status.equals("1"))
                    {
                        JSONObject profile_details=jsonObject.getJSONObject("profile_deatils");
                        String full_name=profile_details.getString("full_name");
                        String age=profile_details.getString("age");
                        String mob_no=profile_details.getString("mob_no");
                        String role=profile_details.getString("role");
                        String location_id=profile_details.getString("location_id");
                        String category_id=profile_details.getString("category_id");
                        String profile_pic=profile_details.getString("profile_pic");
                        String loc_name=profile_details.getString("loc_name");
                        String cat_name=profile_details.getString("cat_name");
                        String total_amount=profile_details.getString("total_amount");
                        onProfileDataListener.onProfileDataFinished(full_name,age,mob_no,profile_pic,total_amount,loc_name,cat_name);
                    }



                } catch (JSONException | NullPointerException e) {
                    onProfileDataListener.onProfileDataFailue(e);
                    e.printStackTrace();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                onProfileDataListener.onProfileDataFailue(error);

            }
        });

    }
}

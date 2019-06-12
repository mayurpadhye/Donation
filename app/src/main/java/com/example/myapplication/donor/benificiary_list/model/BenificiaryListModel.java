package com.example.myapplication.donor.benificiary_list.model;

import android.util.Log;
import com.example.myapplication.donor.home.model.HomeModel;
import com.example.myapplication.helperClass.PrefManager;
import com.example.myapplication.network.RestInterface;
import com.example.myapplication.network.RetrofitClient;
import com.example.myapplication.network.WebServiceURLs;
import com.google.gson.JsonElement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class BenificiaryListModel implements IBenificiaryListModel {
    private static final String TAG = "BenificiaryListModel";
    String user_id, full_name, age, mob_no, role, location_id, category_id, profile_pic, total_amount, loc_name, cat_name;
    List<BenificiaryListModel> benificiaryListModels = new ArrayList<>();

    public BenificiaryListModel(String user_id, String full_name, String age, String mob_no, String role, String location_id, String category_id, String profile_pic, String total_amount, String loc_name, String cat_name) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.age = age;
        this.mob_no = mob_no;
        this.role = role;
        this.location_id = location_id;
        this.category_id = category_id;
        this.profile_pic = profile_pic;
        this.total_amount = total_amount;
        this.loc_name = loc_name;
        this.cat_name = cat_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMob_no() {
        return mob_no;
    }

    public void setMob_no(String mob_no) {
        this.mob_no = mob_no;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public BenificiaryListModel() {
    }

    @Override
    public void getBenificairyList(String location_id,String category_id,final onBenificiaryListListener onBenificiaryListListener) {
        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.getBenificiaryList(location_id,category_id,new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {

                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        JSONArray beneficary_deatils = jsonObject.getJSONArray("beneficary_deatils");
                        for (int k = 0; k < beneficary_deatils.length(); k++) {
                            JSONObject j1 = beneficary_deatils.getJSONObject(k);
                            String user_id = j1.getString("user_id");
                            String full_name = j1.getString("full_name");
                            String age = j1.getString("age");
                            String mob_no = j1.getString("mob_no");
                            String role = j1.getString("role");
                            String location_id = j1.getString("location_id");
                            String category_id = j1.getString("category_id");
                            String profile_pic = j1.getString("profile_pic");
                            String total_amount = j1.getString("total_amount");
                            String loc_name = j1.getString("loc_name");
                            String cat_name = j1.getString("cat_name");

                            benificiaryListModels.add(new BenificiaryListModel(user_id, full_name, age, mob_no, role, location_id, category_id, profile_pic, total_amount, loc_name, cat_name));
                        }
                        onBenificiaryListListener.onBenificairyListFinished(benificiaryListModels);

                    }


                } catch (JSONException | NullPointerException e) {
                        onBenificiaryListListener.onBenificiaryListFailure(e);
                    e.printStackTrace();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: " + error);
                onBenificiaryListListener.onBenificiaryListFailure(error);

            }
        });
    }

    @Override
    public void doRegistration(String name, String age, String mobile, String password, String loc_id,final onDonorRegistration onDonorRegistration) {
        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.register_donor_user(name, age,  mobile,"2", password,loc_id,"0", new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    String user_id = jsonObject.getString("user_id");
                    if (status.equals("1"))
                    onDonorRegistration.onDonorRegistrationFinished(status, message,user_id);
                    else
                        onDonorRegistration.onDonorRegistrationFinished(status, message,"0");
                }
                catch (JSONException | NullPointerException e) {
                    onDonorRegistration.onDonorRegistrationFailure(e);
                    e.printStackTrace();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: " + error);
                onDonorRegistration.onDonorRegistrationFailure(error);
            }
        });
    }
}

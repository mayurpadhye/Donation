package com.example.myapplication.donor.donation.model;

import android.util.Log;
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

public class UserListModel implements  IUserListModel {

    String user_name,user_id,total_amount;
List<UserListModel> userListModelList=new ArrayList<>();
    public UserListModel() {
    }


    public UserListModel(String user_name, String user_id,String total_amount) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.total_amount = total_amount;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    @Override
    public void getUserList(String loc_id, String cat_id, final onUserListListener onUserListListener) {

        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.get_category_wise_user(loc_id, cat_id, new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    String status=jsonObject.getString("status");
                    if (status.equals("1"))
                    {
                        userListModelList.clear();
                        JSONArray user_deatils=jsonObject.getJSONArray("user_deatils");
                        for (int i=0;i<user_deatils.length();i++)
                        {
                            JSONObject j1=user_deatils.getJSONObject(i);
                            String user_id=j1.getString("user_id");
                            String full_name=j1.getString("full_name");
                            String total_amount=j1.getString("total_amount");
                            userListModelList.add(new UserListModel(full_name,user_id,total_amount));
                        }
                        userListModelList.add(0,new UserListModel("Select Beneficiary","0","0"));
                        onUserListListener.onUserListFinished(status,userListModelList);
                    }
                    else
                    {
                        onUserListListener.onUserListFinished(status,userListModelList);
                    }
                }
                catch (JSONException | NullPointerException e) {
                    onUserListListener.onUserListFailure(e);
                    e.printStackTrace();
                }
            }
            @Override
            public void failure(RetrofitError error) {

                onUserListListener.onUserListFailure(error);
            }
        });
    }

    }


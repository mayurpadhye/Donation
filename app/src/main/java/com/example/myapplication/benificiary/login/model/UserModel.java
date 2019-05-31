package com.example.myapplication.benificiary.login.model;

import android.content.Context;
import android.util.Log;
import com.example.myapplication.donor.home.model.HomeModel;
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

public class UserModel implements IUser {


    @Override
    public void doLogin(String user_name, String password, final onLoginRquestListener onLoginRquestListener) {

        RetrofitClient retrofitClient = new RetrofitClient();
        RestInterface service = retrofitClient.getAPIClient(WebServiceURLs.DOMAIN_NAME);
        service.user_login(user_name,password,new Callback<JsonElement>() {
            @Override
            public void success(JsonElement jsonElement, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonElement.toString());
                    onLoginRquestListener.onLoginRequestFinished(jsonObject);
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                    onLoginRquestListener.onLoginRequestFailure(e);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                onLoginRquestListener.onLoginRequestFailure(error);
            }
        });
    }
}

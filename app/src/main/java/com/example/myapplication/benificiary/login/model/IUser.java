package com.example.myapplication.benificiary.login.model;

import org.json.JSONObject;

public interface IUser {

    interface onLoginRquestListener
    {
        void onLoginRequestFinished(JSONObject jsonObject);
        void onLoginRequestFailure(Throwable t);
    }

    public void doLogin(String user_name,String password,String role,onLoginRquestListener onLoginRquestListener);

}

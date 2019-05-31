package com.example.myapplication.benificiary.login.view;

import com.google.gson.JsonObject;
import org.json.JSONObject;

public interface ILoginView {

    public void onClearText();
    public void onLoginResult(JSONObject jsonObject);
    public void onLoginFailure(Throwable t);
    public void setProgressBarVisibility(int visibility);


}

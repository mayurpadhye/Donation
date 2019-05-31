package com.example.myapplication.benificiary.login.Presenter;

public interface ILoginPresenter {
    void clear();
    void doLogin(String name,String pass);
    void setProgressBarVisibility(int visibility);
}

package com.example.myapplication.benificiary.registration.presenter;

public interface IRegistrationPresenter {
    void clearText();
    void checkValidation(String name,String age,String cat_id,String location_id,String mobile_no,String pass,String role);
    void doRegistration(String name,String age,String cat_id,String location_id,String mobile_no,String pass,String role);
    void setProgressVisibility(int visibility);
    void requesrLOcationDetails();
    public void requestCategoryData();
    //void validateRegistrationForm(String name,String age,String cat_id,String location_id,String mobile_no,String pass,String role);

}

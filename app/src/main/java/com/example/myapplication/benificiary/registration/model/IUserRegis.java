package com.example.myapplication.benificiary.registration.model;

import java.util.List;

public interface IUserRegis {

    interface  onRegistrationRequestListener
    {
        void onRegistrationFinshedListener(String status,String message);
        void RegistrationFailureListener(Throwable t);
    }
    void signUpUser(String name,String age,String cat_id,String location_id,String mobile_no,String ifsc,String account_no,String bank_name,String acc_holder_name,String permanent_address,String pass,String role,onRegistrationRequestListener onRegistrationRequestListener);

    interface  onCategoryListListener
    {
        void onCategoryListFinished(List<CategoryListModel> list);
        void onCategoryListFailure(Throwable t);
    }

    void getCategoryList(onCategoryListListener onCategoryListListener);

}

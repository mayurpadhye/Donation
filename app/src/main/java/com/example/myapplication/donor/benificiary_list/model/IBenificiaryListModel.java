package com.example.myapplication.donor.benificiary_list.model;

import java.util.List;

public interface IBenificiaryListModel {

    interface onBenificiaryListListener
    {
        void onBenificairyListFinished(List<BenificiaryListModel> benificiaryListModels);
        void onBenificiaryListFailure(Throwable t);
    }

    public void getBenificairyList(String location_id,String category_id,onBenificiaryListListener onBenificiaryListListener);
    interface onDonorRegistration
    {
        void onDonorRegistrationFinished(String status,String message,String user_id);
        void onDonorRegistrationFailure(Throwable t);
    }

    public void doRegistration(String name,String age,String mobile,String password,String loc_id,onDonorRegistration onDonorRegistration);


}

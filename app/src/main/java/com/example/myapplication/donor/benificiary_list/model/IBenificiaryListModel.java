package com.example.myapplication.donor.benificiary_list.model;

import java.util.List;

public interface IBenificiaryListModel {

    interface onBenificiaryListListener
    {
        void onBenificairyListFinished(List<BenificiaryListModel> benificiaryListModels);
        void onBenificiaryListFailure(Throwable t);
    }

    public void getBenificairyList(String location_id,String category_id,onBenificiaryListListener onBenificiaryListListener);
}

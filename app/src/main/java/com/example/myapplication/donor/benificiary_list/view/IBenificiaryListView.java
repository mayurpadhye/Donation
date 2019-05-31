package com.example.myapplication.donor.benificiary_list.view;

import com.example.myapplication.donor.benificiary_list.model.BenificiaryListModel;

import java.util.List;

public interface IBenificiaryListView {

    public void setProgressVisibility(int visibility);
    public void getBenificiaryList(List<BenificiaryListModel> benificiaryListModelList);
    public void getBenificiaryListFailure(Throwable t);


}

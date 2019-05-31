package com.example.myapplication.donor.benificiary_list.presenter;

import android.content.Context;
import android.view.View;
import com.example.myapplication.donor.benificiary_list.model.BenificiaryListModel;
import com.example.myapplication.donor.benificiary_list.model.IBenificiaryListModel;
import com.example.myapplication.donor.benificiary_list.view.IBenificiaryListView;
import com.example.myapplication.helperClass.PrefManager;

import java.util.List;

public class BenificiaryListImpl implements IBenificiaryListPresenter,IBenificiaryListModel.onBenificiaryListListener {
    Context context;
    IBenificiaryListView iBenificiaryListView;
    IBenificiaryListModel iBenificiaryListModel;
String cat_id;
    public BenificiaryListImpl(Context context, IBenificiaryListView iBenificiaryListView,String cat_id) {
        this.context = context;
        this.iBenificiaryListView = iBenificiaryListView;
        iBenificiaryListModel=new BenificiaryListModel();
        this.cat_id=cat_id;
    }

    @Override
    public void requestBenificiaryList() {
        iBenificiaryListView.setProgressVisibility(View.VISIBLE);
        iBenificiaryListModel.getBenificairyList(PrefManager.getInstance(context).getLocationId(),cat_id,this);
    }

    @Override
    public void onBenificairyListFinished(List<BenificiaryListModel> benificiaryListModels) {
        iBenificiaryListView.setProgressVisibility(View.GONE);
        iBenificiaryListView.getBenificiaryList(benificiaryListModels);
    }

    @Override
    public void onBenificiaryListFailure(Throwable t) {
        iBenificiaryListView.setProgressVisibility(View.GONE);
        iBenificiaryListView.getBenificiaryListFailure(t);

    }
}

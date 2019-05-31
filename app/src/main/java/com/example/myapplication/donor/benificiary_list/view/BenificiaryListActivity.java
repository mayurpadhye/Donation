package com.example.myapplication.donor.benificiary_list.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.myapplication.R;
import com.example.myapplication.donor.benificiary_list.model.BenificiaryListModel;
import com.example.myapplication.donor.benificiary_list.presenter.BenificiaryListAdapter;
import com.example.myapplication.donor.benificiary_list.presenter.BenificiaryListImpl;
import com.example.myapplication.donor.benificiary_list.presenter.IBenificiaryListPresenter;

import java.util.List;

public class BenificiaryListActivity extends AppCompatActivity implements IBenificiaryListView {

    @BindView(R.id.rv_benificiary_list)
    RecyclerView rv_benificiary_list;
String cat_id="";
String cat_name="";
IBenificiaryListPresenter iBenificiaryListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benificiary_list);
        ButterKnife.bind(this);
        setTitle("Beneficiary List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cat_id=getIntent().getStringExtra("cat_id");
        cat_name=getIntent().getStringExtra("cat_name");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_benificiary_list.setLayoutManager(mLayoutManager);
        rv_benificiary_list.setItemAnimator(new DefaultItemAnimator());
        iBenificiaryListPresenter=new BenificiaryListImpl(BenificiaryListActivity.this,this,cat_id);
        iBenificiaryListPresenter.requestBenificiaryList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void setProgressVisibility(int visibility) {

    }

    @Override
    public void getBenificiaryList(List<BenificiaryListModel> benificiaryListModelList) {
        BenificiaryListAdapter adapter=new BenificiaryListAdapter(benificiaryListModelList,BenificiaryListActivity.this);
        rv_benificiary_list.setAdapter(adapter);

    }

    @Override
    public void getBenificiaryListFailure(Throwable t) {

    }
}

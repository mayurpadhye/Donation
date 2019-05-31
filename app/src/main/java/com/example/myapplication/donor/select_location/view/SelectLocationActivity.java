package com.example.myapplication.donor.select_location.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.registration.model.LocationModel;
import com.example.myapplication.benificiary.registration.presenter.IRegistrationPresenter;
import com.example.myapplication.benificiary.registration.presenter.LocationSpinnerAdapter;
import com.example.myapplication.benificiary.registration.presenter.RegistrationCompl;
import com.example.myapplication.benificiary.registration.view.IRegistrationView;
import com.example.myapplication.benificiary.registration.view.RegistrationActivity;
import com.example.myapplication.donor.home.view.HomeActivity;
import com.example.myapplication.donor.select_location.presenter.ISelectLocationPresenter;
import com.example.myapplication.donor.select_location.presenter.SelectLocationPresenterCompl;
import com.example.myapplication.helperClass.PrefManager;

import java.util.List;

public class SelectLocationActivity extends AppCompatActivity implements  ISelectLocationView{

    @BindView(R.id.btn_ok)
    Button btn_ok;
    @BindView(R.id.sp_location)
    Spinner sp_location;
    ISelectLocationPresenter iSelectLocationPresenter;

    @BindView(R.id.p_bar)
    ProgressBar p_bar;
String loc_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        ButterKnife.bind(this);
        setTitle("Select Location");
        iSelectLocationPresenter=new SelectLocationPresenterCompl(SelectLocationActivity.this,this);
        iSelectLocationPresenter.requestLocarionData();


    }

    @Override
    public void setVisibility(int visibility) {
        p_bar.setVisibility(visibility);
    }

    @Override
    public void getAllLocation(final List<LocationModel> locationModelList) {
        Log.i("location",""+locationModelList.size());
        LocationSpinnerAdapter adapter=new LocationSpinnerAdapter(SelectLocationActivity.this,R.layout.row_location,R.id.tv_loc,locationModelList);
        sp_location.setPrompt("Select Location");
        sp_location.setAdapter(adapter);
        sp_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                   PrefManager.getInstance(SelectLocationActivity.this).setLocationId(locationModelList.get(position).getLoc_id());
                    loc_id=locationModelList.get(position).getLoc_id();

                } else {
                    loc_id="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(this, ""+t, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_ok)
    void onButtonOkClick()
    {
        if (loc_id.equals(""))
        {
            Toast.makeText(this, "Please Select Location", Toast.LENGTH_SHORT).show();
        }
        else
        {
            startActivity(new Intent(SelectLocationActivity.this, HomeActivity.class));
            finish();
        }

    }
}

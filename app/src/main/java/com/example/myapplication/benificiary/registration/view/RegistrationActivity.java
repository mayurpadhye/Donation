package com.example.myapplication.benificiary.registration.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.login.view.LoginActivity;
import com.example.myapplication.benificiary.registration.model.CategoryListModel;
import com.example.myapplication.benificiary.registration.model.LocationModel;
import com.example.myapplication.benificiary.registration.presenter.CategorySpinnerAdapter;
import com.example.myapplication.benificiary.registration.presenter.IRegistrationPresenter;
import com.example.myapplication.benificiary.registration.presenter.LocationSpinnerAdapter;
import com.example.myapplication.benificiary.registration.presenter.RegistrationCompl;
import com.example.myapplication.donor.home.presenter.CategoryListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements IRegistrationView {
    @BindView(R.id.rl_sign_in)
    RelativeLayout rl_sign_in;
    @BindView(R.id.btn_sign_up)
    Button btn_sign_up;
    IRegistrationPresenter iRegistrationPresenter;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_age)
    EditText et_age;
    @BindView(R.id.et_cat)
    EditText et_cat;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_location)
    EditText et_location;
    @BindView(R.id.et_pass)
    EditText et_pass;
    @BindView(R.id.sp_location)
    Spinner sp_location;
    @BindView(R.id.p_bar)
    ProgressBar p_bar;
    @BindView(R.id.sp_cat)
    Spinner sp_cat;
    List<CategoryListModel> listModels = new ArrayList<>();
    List<LocationModel> locationModelList1 = new ArrayList<>();
    String cat_id = "";
    String loc_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iRegistrationPresenter = new RegistrationCompl(RegistrationActivity.this, this);
        iRegistrationPresenter.requesrLOcationDetails();
        iRegistrationPresenter.requestCategoryData();

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iRegistrationPresenter.checkValidation(et_name.getText().toString().trim(), et_age.getText().toString().trim(), cat_id, loc_id, et_mobile.getText().toString().trim(), et_pass.getText().toString(), "1");


            }
        });

        sp_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    cat_id = listModels.get(position).getCat_id();
                } else {
                    cat_id = "";
                }
            }
           @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (position > 0) {
                    loc_id = locationModelList1.get(position).getLoc_id();
                } else {
                    loc_id = "";
                }
            }
          @Override
            public void onNothingSelected(AdapterView<?> parent) {
           }
        });
    }

    @OnClick(R.id.rl_sign_in)
    public void onSignInClick() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void RegistrationResult(String result, String message) {
        if (result.equals("0")) {
            Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void RegistrationFailure(Throwable t) {
        Toast.makeText(this, "Unable to register", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setProgressVisibility(int visibility) {
        p_bar.setVisibility(visibility);

    }

    @Override
    public void getLocationList(List<LocationModel> locationModelList) {
        Log.i("location", "" + locationModelList.size());
        locationModelList1.addAll(locationModelList);
        LocationSpinnerAdapter adapter = new LocationSpinnerAdapter(RegistrationActivity.this, R.layout.row_location, R.id.tv_loc, locationModelList);
        sp_location.setPrompt("Select Location");
        sp_location.setAdapter(adapter);
    }

    @Override
    public void getLocationFailure(Throwable t) {
        Toast.makeText(this, "Unable to get Location Result", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void validationResult(int status, int position) {

        switch (position) {
            case 1:
                et_name.setError("Please Enter Full name");
                break;
            case 2:
                et_age.setError("Please Enter Age");
                break;
            case 3:
                Toast.makeText(this, "Please Select Category", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Please Select Location", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                et_pass.setError("Enter Password");
                break;
            case 7:
                iRegistrationPresenter.doRegistration(et_name.getText().toString().trim(), et_age.getText().toString().trim(), cat_id, loc_id, et_mobile.getText().toString().trim(), et_pass.getText().toString(), "1");
                break;
        }

    }

    @Override
    public void getCategoryList(List<CategoryListModel> list) {
        Log.i("location", "" + list.size());
        listModels.addAll(list);
        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(RegistrationActivity.this, R.layout.row_location, R.id.tv_loc, list);
        sp_cat.setPrompt("Select Category");
        sp_cat.setAdapter(adapter);
    }

    @Override
    public void getCategoryListFailure(Throwable t) {

    }
}

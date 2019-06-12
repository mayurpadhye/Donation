package com.example.myapplication.donor.benificiary_list.view;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.myapplication.R;
import com.example.myapplication.RecyclerViewClickListener;
import com.example.myapplication.benificiary.home.BenificiaryHomeActivity;
import com.example.myapplication.benificiary.login.view.LoginActivity;
import com.example.myapplication.benificiary.registration.model.LocationModel;
import com.example.myapplication.benificiary.registration.presenter.LocationSpinnerAdapter;
import com.example.myapplication.benificiary.registration.view.RegistrationActivity;
import com.example.myapplication.donor.benificiary_list.model.BenificiaryListModel;
import com.example.myapplication.donor.benificiary_list.presenter.BenificiaryListAdapter;
import com.example.myapplication.donor.benificiary_list.presenter.BenificiaryListImpl;
import com.example.myapplication.donor.benificiary_list.presenter.IBenificiaryListPresenter;
import com.example.myapplication.helperClass.PrefManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BenificiaryListActivity extends AppCompatActivity implements IBenificiaryListView, View.OnClickListener {

    @BindView(R.id.rv_benificiary_list)
    RecyclerView rv_benificiary_list;
    String cat_id = "";
    String cat_name = "";
    IBenificiaryListPresenter iBenificiaryListPresenter;
    RecyclerViewClickListener listener;
    BottomSheetDialog bottomSheetDialog;
    EditText et_name, et_age, et_mobile, et_pass;
    Spinner sp_location;
    Button btn_sign_up;
    @BindView(R.id.p_bar)
    ProgressBar p_bar;
    String loc_id = "";
    BottomSheetDialog bottomSheetDialog_login;
    EditText et_mobile_number, et_password;
    Button btn_login;
    RelativeLayout rl_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benificiary_list);
        ButterKnife.bind(this);
        setTitle("Beneficiary List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cat_id = getIntent().getStringExtra("cat_id");
        cat_name = getIntent().getStringExtra("cat_name");
        initSignupDailog();
        initSignInDailog();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_benificiary_list.setLayoutManager(mLayoutManager);
        rv_benificiary_list.setItemAnimator(new DefaultItemAnimator());
        iBenificiaryListPresenter = new BenificiaryListImpl(BenificiaryListActivity.this, this, cat_id);
        iBenificiaryListPresenter.requestBenificiaryList();
        iBenificiaryListPresenter.getLocationDetails();

    }//onCreateClose

    private void initSignInDailog() {
        bottomSheetDialog_login = new BottomSheetDialog(this);
        bottomSheetDialog_login.setContentView(R.layout.bottom_sheet_donor_login);
        btn_login = bottomSheetDialog_login.findViewById(R.id.btn_login);
        et_mobile_number = bottomSheetDialog_login.findViewById(R.id.et_mobile_number);
        et_password = bottomSheetDialog_login.findViewById(R.id.et_password);
        rl_sign_up = bottomSheetDialog_login.findViewById(R.id.rl_sign_up);
        rl_sign_up.setOnClickListener(this);
        btn_login.setOnClickListener(this);


    }

    private void initSignupDailog() {

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_donor_registration);
        et_name = bottomSheetDialog.findViewById(R.id.et_name);
        et_age = bottomSheetDialog.findViewById(R.id.et_name);
        et_mobile = bottomSheetDialog.findViewById(R.id.et_mobile);
        sp_location = bottomSheetDialog.findViewById(R.id.sp_location);
        et_pass = bottomSheetDialog.findViewById(R.id.et_pass);
        btn_sign_up = bottomSheetDialog.findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void setProgressVisibility(int visibility) {
        p_bar.setVisibility(visibility);
    }

    @Override
    public void getBenificiaryList(List<BenificiaryListModel> benificiaryListModelList) {
        BenificiaryListAdapter adapter = new BenificiaryListAdapter(benificiaryListModelList, BenificiaryListActivity.this, listener = new RecyclerViewClickListener() {
        @Override
        public void onClick(View view, int position) {
            switch (view.getId()) {
                case R.id.btn_donate:

                    if (PrefManager.getInstance(BenificiaryListActivity.this).IS_LOGIN()) {

                    } else {
                        bottomSheetDialog_login.show();
                    }
                    // Toast.makeText(BenificiaryListActivity.this, "Donate", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_view_details:
                    if (PrefManager.getInstance(BenificiaryListActivity.this).IS_LOGIN()) {

                    } else {
                        bottomSheetDialog_login.show();
                    }
                    break;

            }

        }
    });
        rv_benificiary_list.setAdapter(adapter);
}
    @Override
    public void getBenificiaryListFailure(Throwable t) {
     }

    @Override
    public void getValidationResult(int position) {
        switch (position) {
            case 1:
                et_name.setError("Please Enter Full name");
                break;
            case 2:
                et_age.setError("Please Enter Age");
                break;

            case 3:
                Toast.makeText(this, "Please Select Location", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                et_pass.setError("Enter Password");
                break;
            case 6:
                iBenificiaryListPresenter.doDonorRegistration(et_name.getText().toString(), et_age.getText().toString(), et_mobile.getText().toString(), et_pass.getText().toString(),loc_id);
                break;
        }
    }

    @Override
    public void getDonorRegistrationResult(String status, String message,String user_id) {
        bottomSheetDialog.dismiss();
       if (status.equals("1"))
       {
           PrefManager.getInstance(BenificiaryListActivity.this).setUserId(user_id);
           PrefManager.getInstance(BenificiaryListActivity.this).setIsLogin(true);
       }
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getLocation(final List<LocationModel> locationModelList) {
        LocationSpinnerAdapter adapter = new LocationSpinnerAdapter(BenificiaryListActivity.this, R.layout.row_location, R.id.tv_loc, locationModelList);
        sp_location.setPrompt("Select Location");
        sp_location.setAdapter(adapter);
        sp_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    loc_id = locationModelList.get(position).getLoc_id();
                    Toast.makeText(BenificiaryListActivity.this, "" + loc_id, Toast.LENGTH_SHORT).show();
                } else {
                    loc_id = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void getLoginResult(JSONObject jsonObject) {
        bottomSheetDialog_login.dismiss();
        btn_login.setEnabled(true);
        try {
            String status=jsonObject.getString("status");
            if (status.equals("1"))
            {   String message=jsonObject.getString("message");
                JSONObject login_deatils=jsonObject.getJSONObject("login_deatils");
                String dr_id=login_deatils.getString("dr_id");
                String full_name=login_deatils.getString("full_name");
                String mob_no=login_deatils.getString("mob_no");
                String location_id=login_deatils.getString("location_id");
                String password=login_deatils.getString("password");
                String role=login_deatils.getString("role");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                PrefManager.getInstance(BenificiaryListActivity.this).setUserId(dr_id);
                PrefManager.getInstance(BenificiaryListActivity.this).setIsLogin(true);
                PrefManager.getInstance(BenificiaryListActivity.this).setMobile(mob_no);
                PrefManager.getInstance(BenificiaryListActivity.this).setRole(role);
                PrefManager.getInstance(BenificiaryListActivity.this).setLocationId(location_id);
            }
            else
            {
                Toast.makeText(this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_up:
                iBenificiaryListPresenter.validateData(et_name.getText().toString(), et_age.getText().toString(), et_mobile.getText().toString(), et_pass.getText().toString(),loc_id);
                break;
            case R.id.rl_sign_up:
                bottomSheetDialog_login.dismiss();
                bottomSheetDialog.show();
                break;
            case R.id.btn_login:
                iBenificiaryListPresenter.doLogin(et_mobile_number.getText().toString(),et_password.getText().toString(),"2");
               break;

        }
    }

    public void viewDonationDetails()
    {
        Dialog dialog=new Dialog(this);

    }
}

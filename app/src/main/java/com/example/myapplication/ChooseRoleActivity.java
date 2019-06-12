package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.myapplication.benificiary.home.BenificiaryHomeActivity;
import com.example.myapplication.benificiary.login.view.LoginActivity;
import com.example.myapplication.donor.home.view.HomeActivity;
import com.example.myapplication.donor.select_location.view.SelectLocationActivity;
import com.example.myapplication.helperClass.PrefManager;

public class ChooseRoleActivity extends AppCompatActivity {

    @BindView(R.id.btn_donor)
    Button btn_donor;

    @BindView(R.id.btn_benificiary)
    Button btn_benificiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        ButterKnife.bind(this);
        if (PrefManager.getInstance(ChooseRoleActivity.this).IS_LOGIN())
        {
            if (PrefManager.getInstance(ChooseRoleActivity.this).getRole().equals("1"))
            {
                startActivity(new Intent(ChooseRoleActivity.this, BenificiaryHomeActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(ChooseRoleActivity.this, HomeActivity.class));
                finish();
            }
        }
    }

    @OnClick(R.id.btn_donor)
    public void onDonorClick()
    {
        startActivity(new Intent(ChooseRoleActivity.this, SelectLocationActivity.class));

    }
    @OnClick(R.id.btn_benificiary)
    public void onBenificiaryClick()
    {
        startActivity(new Intent(ChooseRoleActivity.this, LoginActivity.class));
    }
}

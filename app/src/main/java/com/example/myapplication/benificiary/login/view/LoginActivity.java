package com.example.myapplication.benificiary.login.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.home.BenificiaryHomeActivity;
import com.example.myapplication.benificiary.registration.view.RegistrationActivity;
import com.example.myapplication.benificiary.login.Presenter.ILoginPresenter;
import com.example.myapplication.benificiary.login.Presenter.LoginPresenterCompl;
import com.example.myapplication.helperClass.PrefManager;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    @BindView(R.id.rl_sign_up)
    RelativeLayout rl_sign_up;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.et_mobile_number)
    EditText et_mobile_number;
    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.p_bar)
    ProgressBar p_bar;

    private ILoginPresenter iLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTitle("Login");
        iLoginPresenter = new LoginPresenterCompl(this, LoginActivity.this);


    }

    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        //startActivity(new Intent(LoginActivity.this, BenificiaryHomeActivity.class));
        btn_login.setEnabled(false);
        iLoginPresenter.setProgressBarVisibility(View.VISIBLE);
        iLoginPresenter.doLogin(et_mobile_number.getText().toString().trim(), et_password.getText().toString().trim());

    }

    @OnClick(R.id.rl_sign_up)
    public void onSignUpClick() {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

    @Override
    public void onClearText() {

    }

    @Override
    public void onLoginResult(JSONObject jsonObject) {

        btn_login.setEnabled(true);
        try {
            String status=jsonObject.getString("status");
            if (status.equals("1"))
            {
                String message=jsonObject.getString("message");
                JSONObject login_deatils=jsonObject.getJSONObject("login_deatils");
                String user_id=login_deatils.getString("user_id");
                String full_name=login_deatils.getString("full_name");
                String age=login_deatils.getString("age");
                String location_id=login_deatils.getString("location_id");
                String mob_no=login_deatils.getString("mob_no");
                String category_id=login_deatils.getString("category_id");
                String password=login_deatils.getString("password");
                String role=login_deatils.getString("role");
                String is_block=login_deatils.getString("is_block");
                String total_amount=login_deatils.getString("total_amount");
                String profile_pic=login_deatils.getString("profile_pic");
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                PrefManager.getInstance(LoginActivity.this).setUserId(user_id);
                PrefManager.getInstance(LoginActivity.this).setIsLogin(true);
                PrefManager.getInstance(LoginActivity.this).setMobile(mob_no);
                PrefManager.getInstance(LoginActivity.this).setRole(role);
                PrefManager.getInstance(LoginActivity.this).setLocationId(location_id);
                PrefManager.getInstance(LoginActivity.this).setCategoryId(category_id);
                startActivity(new Intent(LoginActivity.this,BenificiaryHomeActivity.class));
                finish();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoginFailure(Throwable t) {

    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        p_bar.setVisibility(visibility);
    }
}

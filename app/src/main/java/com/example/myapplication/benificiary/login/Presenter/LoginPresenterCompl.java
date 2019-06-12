package com.example.myapplication.benificiary.login.Presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.example.myapplication.benificiary.login.model.IUser;
import com.example.myapplication.benificiary.login.model.UserModel;
import com.example.myapplication.benificiary.login.view.ILoginView;
import org.json.JSONObject;


public class LoginPresenterCompl  implements ILoginPresenter,IUser.onLoginRquestListener{
    ILoginView iLoginView;
    Context context;
    IUser user;
    Handler handler;
    public LoginPresenterCompl(ILoginView iLoginView, Context context) {
        this.iLoginView = iLoginView;
        this.context = context;
        handler=new Handler(Looper.getMainLooper());
        initUser();
    }

    private void initUser() {
       user=new UserModel();

    }

    @Override
    public void clear() {
        iLoginView.onClearText();

    }

    @Override
    public void doLogin(String name, String pass) {
        iLoginView.setProgressBarVisibility(View.VISIBLE);
        user.doLogin(name,pass,"1",this);

    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        iLoginView.setProgressBarVisibility(visibility);
    }

    @Override
    public void onLoginRequestFinished(JSONObject jsonObject) {
        iLoginView.setProgressBarVisibility(View.GONE);
        iLoginView.onLoginResult(jsonObject);
    }

    @Override
    public void onLoginRequestFailure(Throwable t) {
        iLoginView.setProgressBarVisibility(View.GONE);
        iLoginView.onLoginFailure(t);

    }
}

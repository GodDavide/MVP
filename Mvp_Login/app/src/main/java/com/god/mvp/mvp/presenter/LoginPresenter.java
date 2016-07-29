package com.god.mvp.mvp.presenter;

import android.os.Handler;

import com.god.mvp.mvp.bean.User;
import com.god.mvp.mvp.model.LoginModel;
import com.god.mvp.mvp.model.LoginModelImpl;
import com.god.mvp.mvp.view.LoginView;

/**
 * @author David  create on 2016/7/29  15:56.
 * @email david.forever.god@gmail.com
 * Learn from yesterday, live for today, hope for tomorrow.
 */
public class LoginPresenter {
    private LoginView loginView;
    private LoginModel loginModel;
    private Handler mHandler;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImpl();
        mHandler = new Handler();
    }

    public void login() {
        loginView.showLoading();
        loginModel.login(loginView.getUsername(), loginView.getPassword(), new LoginModel.OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                //模拟登录成功后，返回信息到Activity,吐出成功信息
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.showSuccessMsg(user);
                        loginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed(final String s) {
                //模拟登录失败后，返回信息，吐出错误信息
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.showFailedMsg(s);
                        loginView.hideLoading();
                    }
                });
            }
        });
    }

    public void clear(){
        loginView.clearEditText();
    }
}

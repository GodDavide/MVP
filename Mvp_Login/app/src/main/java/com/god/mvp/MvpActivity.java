package com.god.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.god.mvp.mvp.bean.User;
import com.god.mvp.mvp.presenter.LoginPresenter;
import com.god.mvp.mvp.view.LoginView;

public class MvpActivity extends AppCompatActivity implements LoginView {

    private EditText et_userName;
    private EditText et_password;
    private ProgressBar progressBar;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        initview();
        loginPresenter = new LoginPresenter(this);
    }

    private void initview() {
        et_userName = (EditText) findViewById(R.id.main_et_username);
        et_password = (EditText) findViewById(R.id.main_et_password);
        progressBar = (ProgressBar) findViewById(R.id.main_progressBar);
    }

    //点击登录
    public void LoginClick(View view) {
        loginPresenter.login();
    }

    //点击清除
    public void ClearClick(View view) {
        loginPresenter.clear();
    }

    @Override
    public String getUsername() {
        return et_userName.getText().toString();
    }

    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSuccessMsg(User user) {
        Toast.makeText(MvpActivity.this, "User " + user.getUsername() + " Login Sccess!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedMsg(String s) {
        Toast.makeText(MvpActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearEditText() {
        et_userName.setText("");
        et_password.setText("");
    }
}

package com.god.mvp.mvp.view;

import com.god.mvp.mvp.bean.User;

/**
 * author: {Davide} on 2016/7/29.
 * email : {suwei@acooo.cn}
 * Difficulties vanish when faced boldly.
 */
public interface LoginView {
    //得到用户填写的信息
    String getUsername();
    String getPassword();

    //显示和隐藏登录ProgressBar
    void showLoading();
    void hideLoading();

    //登录成功或失败后，返回信息的方法
    void showSuccessMsg(User user);
    void showFailedMsg(String s);

    //清楚数据
    void clearEditText();
}

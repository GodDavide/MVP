package com.god.mvp.mvp.model;

import com.god.mvp.mvp.bean.User;

/**
 * @author David  create on 2016/7/29  15:47.
 * @email david.forever.god@gmail.com
 * Learn from yesterday, live for today, hope for tomorrow.
 */
public class LoginModelImpl implements LoginModel {
    @Override
    public void login(final String username, final String password, final OnLoginListener onLoginListener) {
        /**
         * @author David  created at 2016/7/29 15:47
         * 模拟子线程，执行耗时操作
         */
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                    if (username.equals("David") && password.equals("12345")) {
                        onLoginListener.loginSuccess(new User(username, password));
                    } else {
                        onLoginListener.loginFailed("Incorrect username or password.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

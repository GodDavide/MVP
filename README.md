# MVP
###这是一个使用mvp模式实现模拟用户登录的简单Demo。<br>(参考的网络资源：Thanks for hongyangAndroid)
<br>
<br>
__期待共同进步__
#####@Auther: David
#####@email ：david.forever.god@gmail.com
#####Learn from yesterday, live for today, hope for tomorrow.<br>

                     start

###__正文__: <br>

MVP 是从经典的模式MVC演变而来，它们的基本思想有相通的地方：Controller/Presenter负责逻辑的处理，Model提供数据，View负责显示... ...<br>

理论知识不多讲，我也讲不清楚，请直接查看代码或下载后查看；<br>

###效果图<br>
![image](https://github.com/GodDavide/MVP/blob/master/images/LogiSuccess.jpg)<br>

###首先看一下文件结构：<br>
![image](https://github.com/GodDavide/MVP/blob/master/images/javaPic.jpg)<br>

###代码书写顺序：熟悉了mvp之后，我掌握了属于自己风格的书写顺序，感觉思路较为清晰，不会遗漏某些信息。(仅个人观点，欢迎吐槽~~~)<br>

#####step1:bean类：<br>
```java

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

```

#####step2：Model:Model里写真正处理信息的操作，比如请求网络，再次以子线程进行模拟。首先写接口类，然后写实现类<br>

```java
//接口类
public interface LoginModel {
    void login(String username, String password, OnLoginListener onLoginListener);

    interface OnLoginListener {
        void loginSuccess(User user);

        void loginFailed(String s);
    }
}

```
```java
//实现类
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

```
#####step3：View:view是Presenter与View交互接口，写一些方法以便在Activity中处理信息<br>

```java

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

```
#####step4：Presenter:Presenter是用作Model和View之间交互的桥梁，在Activity中拿到Presenter对象后，实现View接口，调用Presenter方法即可<br>

```java

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

```

#####step5：Activity: 其实就是View...在new Presenter(this)时，会提示集成View接口，接口中的方法都会在Activity调用Presenter方法时候被调用。<br>

```java

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

```
#####MainActivity布局文件：
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="10dp"
    tools:context="com.god.mvp.MvpActivity">

    <EditText
        android:id="@+id/main_et_username"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="请输入姓名"
        android:textColorHint="#505050"
        android:textSize="20dp" />

    <EditText
        android:id="@+id/main_et_password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/main_et_username"
        android:layout_marginTop="10dp"
        android:hint="请输入姓名"
        android:textColorHint="#505050"
        android:textSize="20dp" />

    <ProgressBar
        android:id="@+id/main_progressBar"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_centerInParent="true"
        android:visibility="gone" />

    <ImageView
        android:id="@+id/align"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/main_et_password"
        android:layout_marginRight="30dp"
        android:layout_marginTop="10dp"
        android:layout_toLeftOf="@id/align"
        android:onClick="LoginClick"
        android:text="Login"

        />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/main_et_password"
        android:layout_marginLeft="30dp"
        android:layout_marginTop="10dp"
        android:layout_toRightOf="@id/align"
        android:onClick="ClearClick"
        android:text="Clear" />

</RelativeLayout>
```

#####step6：下面就可以运行了，正确的用户信息为：<br>username = "David"<br>password = "12345"<br>
                     end



========================
__Remember Me__
#####@ Name  : David
#####@ email ：david.forever.god@gmail.com
#####Learn from yesterday, live for today, hope for tomorrow.<br>

###Thanks for you!!!<br>
###Have a nice day !!!

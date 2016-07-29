# MVP
###这是一个简单的模拟用户登录的小Demo。(参考的网络资源)

##Auther David<br>
#####邮箱：david.forever.god@gmail.com<br>

####正文: <br>

MVP 是从经典的模式MVC演变而来，它们的基本思想有相通的地方：Controller/Presenter负责逻辑的处理，Model提供数据，View负责显示... ...<br>

理论知识不多讲，我也讲不清楚，请直接查看代码或下载后查看；<br>

###效果图<br>
![image](https://github.com/GodDavide/MVP/blob/master/images/LogiSuccess.jpg)<br>

###首先看一下文件结构：<br>
![image](https://github.com/GodDavide/MVP/blob/master/images/javaPic.jpg)<br>

###代码书写顺序：<br>

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
```
#####step4：Presenter:Presenter是用作Model和View之间交互的桥梁，在Activity中拿到Presenter对象后，实现View接口，调用Presenter方法即可<br>

```java
```

#####step5：Activity: 其实就是View...在new Presenter(this)时，会提示集成View接口，接口中的方法都会在Activity调用Presenter方法时候被调用。<br>

```java
```

#####step6：下面就可以运行了，正确的用户信息为：<br>username = "David"<br>password = "12345"<br>

##Thanks for you!!!<br>
##Have a nice day !!!

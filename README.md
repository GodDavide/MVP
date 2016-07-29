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
![image}(https://github.com/GodDavide/MVP/blob/master/images/javaPic.jpg)<br>

###代码书写顺序：<br>

#####step1:bean类：<br>
...java
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
...

#####step2：Model:首先写接口类，然后写实现类<br>
######接口类
...java
public interface LoginModel {
    void login(String username, String password, OnLoginListener onLoginListener);

    interface OnLoginListener {
        void loginSuccess(User user);

        void loginFailed(String s);
    }
}
...


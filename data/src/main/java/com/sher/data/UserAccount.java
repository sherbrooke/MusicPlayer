package com.sher.data;

/**
 * Created by Sher on 2017/8/20.
 */

public class UserAccount {

    private String userName;
    private String password;
    private String phone;

    public UserAccount(String userName, String password, String phone) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }
    public UserAccount(){

    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

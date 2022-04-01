package com.example.rentlab;

public class userHelperClass {

    public String Userid, Firstname, Lastname, Email, Password, Aadhar, UserType;

    public userHelperClass() {
    }

    public userHelperClass(String userid, String firstname, String lastname, String email, String password, String aadhar, String userType) {
        Userid = userid;
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
        Password = password;
        Aadhar = aadhar;
        UserType = userType;
    }
}

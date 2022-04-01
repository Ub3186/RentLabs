package com.example.rentlab;

public class providerHelperClass {

    public String Userid, Firstname, Lastname, Email, Password, Aadhar, UserType, shopName, shopiID, shopAddress, shopEmail, shopContact;

    public providerHelperClass() {
    }

    public providerHelperClass(String userid, String firstname, String lastname, String email, String password, String aadhar, String userType, String shopName, String shopiID, String shopAddress, String shopEmail, String shopContact) {
        Userid = userid;
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
        Password = password;
        Aadhar = aadhar;
        UserType = userType;
        this.shopName = shopName;
        this.shopiID = shopiID;
        this.shopAddress = shopAddress;
        this.shopEmail = shopEmail;
        this.shopContact = shopContact;
    }
}

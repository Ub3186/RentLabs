package com.example.rentlab;

public class placeOrderHelperClass {

    public String poinvoice, productid, userid, podate, rentaldays, deposit, balanceAmount, customername, returndate, contactnumber, shipAddress;

    public placeOrderHelperClass() {
    }

    public placeOrderHelperClass(String poinvoice, String productid, String userid, String podate, String rentaldays, String deposit, String balanceAmount, String returndate, String contactnumber, String shipAddress, String customername) {
        this.poinvoice = poinvoice;
        this.productid = productid;
        this.userid = userid;
        this.podate = podate;
        this.rentaldays = rentaldays;
        this.deposit = deposit;
        this.balanceAmount = balanceAmount;
        this.returndate = returndate;
        this.customername = customername;
        this.contactnumber = contactnumber;
        this.shipAddress = shipAddress;
    }

    public placeOrderHelperClass(String poinvoice) {
        this.poinvoice = poinvoice;
    }

    public String getPoinvoice() {
        return poinvoice;
    }

    public void setPoinvoice(String poinvoice) {
        this.poinvoice = poinvoice;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPodate() {
        return podate;
    }

    public void setPodate(String podate) {
        this.podate = podate;
    }

    public String getRentaldays() {
        return rentaldays;
    }

    public void setRentaldays(String rentaldays) {
        this.rentaldays = rentaldays;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposite(String deposit) {
        this.deposit = deposit;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }
}




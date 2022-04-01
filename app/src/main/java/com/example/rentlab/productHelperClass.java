package com.example.rentlab;

public class productHelperClass {

    public String uniqueid, userid, brand, processor, ram, graphics, os, screensize, model;

    public productHelperClass() {
    }

    public productHelperClass(String uniqueid, String userid, String brand, String processor, String ram, String graphics, String os, String screensize, String model) {
        this.uniqueid = uniqueid;
        this.userid = userid;
        this.brand = brand;
        this.processor = processor;
        this.ram = ram;
        this.graphics = graphics;
        this.os = os;
        this.screensize = screensize;
        this.model = model;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getScreensize() {
        return screensize;
    }

    public void setScreensize(String screensize) {
        this.screensize = screensize;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}

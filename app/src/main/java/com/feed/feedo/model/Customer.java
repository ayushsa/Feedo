package com.feed.feedo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("DOA")
    @Expose
    private String dOA;
    @SerializedName("Gender")
    @Expose
    private String gender;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getDOA() {
        return dOA;
    }

    public void setDOA(String dOA) {
        this.dOA = dOA;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
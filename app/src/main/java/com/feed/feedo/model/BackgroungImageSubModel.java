package com.feed.feedo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BackgroungImageSubModel {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Key")
    @Expose
    private String key;
    @SerializedName("Value")
    @Expose
    private String value;
    @SerializedName("ClientId")
    @Expose
    private Integer clientId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

}


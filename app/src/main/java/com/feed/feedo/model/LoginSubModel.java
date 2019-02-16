package com.feed.feedo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginSubModel
{
    @SerializedName("ClientId")
    @Expose
    private Integer clientId;

    @SerializedName("DisplayName")
    @Expose
    private String displayName;

    @SerializedName("Logo")
    @Expose
    private String logo;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("status")
    @Expose
    private int status;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getClientId()
    {
        return clientId;
    }

    public void setClientId(Integer clientId)
    {
        this.clientId = clientId;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

}

package com.feed.feedo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BackgroundImageModel
{

    @SerializedName("resurces")
    @Expose
    private List<BackgroungImageSubModel> resurces = null;

    public List<BackgroungImageSubModel> getResurces()
    {
        return resurces;
    }

    public void setResurces(List<BackgroungImageSubModel> resurces)
    {
        this.resurces = resurces;
    }

}

package com.feed.feedo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question
{
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Opt1")
    @Expose
    private String opt1;
    @SerializedName("Opt2")
    @Expose
    private String opt2;
    @SerializedName("Opt3")
    @Expose
    private String opt3;
    @SerializedName("Opt4")
    @Expose
    private String opt4;
    @SerializedName("TitleH")
    @Expose
    private String titleH;
    @SerializedName("Opt1H")
    @Expose
    private String opt1H;
    @SerializedName("Opt2H")
    @Expose
    private String opt2H;
    @SerializedName("Opt3H")
    @Expose
    private String opt3H;
    @SerializedName("Opt4H")
    @Expose
    private String opt4H;
    @SerializedName("ClientId")
    @Expose
    private Integer clientId;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("icon1")
    @Expose
    private Object icon1;
    @SerializedName("icon2")
    @Expose
    private Object icon2;
    @SerializedName("icon3")
    @Expose
    private Object icon3;
    @SerializedName("icon4")
    @Expose
    private Object icon4;
    @SerializedName("iconselected1")
    @Expose
    private Object iconselected1;
    @SerializedName("iconselected2")
    @Expose
    private Object iconselected2;
    @SerializedName("iconselected3")
    @Expose
    private Object iconselected3;

    @SerializedName("iconselected4")
    @Expose
    private Object iconselected4;

    @SerializedName("display")
    @Expose
    private Integer display;

    @SerializedName("givenAnswers")
    @Expose
    private int GivenAnswers = -1;


    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public int getGivenAnswers()
    {
        return GivenAnswers;
    }

    public void setGivenAnswers(int givenAnswers)
    {
        GivenAnswers = givenAnswers;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public String getTitleH() {
        return titleH;
    }

    public void setTitleH(String titleH) {
        this.titleH = titleH;
    }

    public String getOpt1H() {
        return opt1H;
    }

    public void setOpt1H(String opt1H) {
        this.opt1H = opt1H;
    }

    public String getOpt2H() {
        return opt2H;
    }

    public void setOpt2H(String opt2H) {
        this.opt2H = opt2H;
    }

    public String getOpt3H() {
        return opt3H;
    }

    public void setOpt3H(String opt3H) {
        this.opt3H = opt3H;
    }

    public String getOpt4H() {
        return opt4H;
    }

    public void setOpt4H(String opt4H) {
        this.opt4H = opt4H;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getIcon1() {
        return icon1;
    }

    public void setIcon1(Object icon1) {
        this.icon1 = icon1;
    }

    public Object getIcon2() {
        return icon2;
    }

    public void setIcon2(Object icon2) {
        this.icon2 = icon2;
    }

    public Object getIcon3() {
        return icon3;
    }

    public void setIcon3(Object icon3) {
        this.icon3 = icon3;
    }

    public Object getIcon4() {
        return icon4;
    }

    public void setIcon4(Object icon4) {
        this.icon4 = icon4;
    }

    public Object getIconselected1() {
        return iconselected1;
    }

    public void setIconselected1(Object iconselected1) {
        this.iconselected1 = iconselected1;
    }

    public Object getIconselected2() {
        return iconselected2;
    }

    public void setIconselected2(Object iconselected2) {
        this.iconselected2 = iconselected2;
    }

    public Object getIconselected3() {
        return iconselected3;
    }

    public void setIconselected3(Object iconselected3) {
        this.iconselected3 = iconselected3;
    }

    public Object getIconselected4() {
        return iconselected4;
    }

    public void setIconselected4(Object iconselected4) {
        this.iconselected4 = iconselected4;
    }

}


package com.feed.feedo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LogoutModel
{
        @SerializedName("logout")
        @Expose
        private List<LoginSubModel> login = null;

        public List<LoginSubModel> getLogin()
        {
            return login;
        }

        public void setLogin(List<LoginSubModel> login)
        {
            this.login = login;
        }
}

package com.feed.feedo.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel
{
        @SerializedName("login")
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

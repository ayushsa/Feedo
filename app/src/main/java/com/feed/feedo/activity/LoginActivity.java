package com.feed.feedo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.feed.feedo.R;
import com.feed.feedo.model.LoginModel;
import com.feed.feedo.network.ApiClient;
import com.feed.feedo.network.ApiClientInterface;
import com.feed.feedo.utils.Constant;
import com.feed.feedo.utils.SharedPreference;
import com.feed.feedo.utils.Support;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity
{

    ApiClientInterface apiClientInterface;
    com.trend.progress.ProgressDialog progressBar;
    private SharedPreference sp;
    private EditText etUserName;
    private EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = new SharedPreference(this);

        //Retrofit Initialization
        apiClientInterface = ApiClient.getApiClient().create(ApiClientInterface.class);

        progressBar = new com.trend.progress.ProgressDialog(LoginActivity.this);
        progressBar.setBarColor(getResources().getColor(R.color.app_color_dark));

        init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            disableAutoFill();
        }
    }

    private void init()
    {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Button btLogin = (Button) findViewById(R.id.btLogin);

        btLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if (Support.isNetworkOnline(LoginActivity.this))
                {
                    if (!userName.isEmpty() && userName != null)
                    {
                        if (!password.isEmpty() && password != null)
                        {
                            loginAPI(userName, password, Support.getDeviceId(LoginActivity.this));
                        }
                        else
                            {
                            Support.showSnackbar(LoginActivity.this, "Password Empty");
                        }
                    }
                    else
                        {
                        Support.showSnackbar(LoginActivity.this, "UserName Empty");
                    }
                }
                else
                    {
                    Support.showAlertDialog(LoginActivity.this, getResources().getString(R.string.msg_check_internet));
                }
            }
        });
    }


    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.O)
    private void disableAutoFill() {
        getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
    }


    private void loginAPI(final String userName, final String password, String deviceId)
    {
         progressBar.show();

        apiClientInterface.login(userName, password, deviceId).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressBar.dismiss();

                Log.d("AYUSH", response.isSuccessful() + "");

                if (response.isSuccessful()) {
                    if (response.body().getLogin().get(0).getDisplayName() == null) {
                        if (response.body().getLogin().get(0).getStatus() == 0) {
                            Support.showAlertDialog(LoginActivity.this, "User is already logged in on another device.");
                        } else if (response.body().getLogin().get(0).getStatus() == -1) {
                            Support.showAlertDialog(LoginActivity.this, "Wrong User and Password");
                        }
                    } else {
                        sp.setValueBool(Constant.KEY_ISLOGIN, true);
                        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        sp.setValueInt(Constant.KEY_CLIENT_ID, response.body().getLogin().get(0).getClientId());
                        sp.setValueString(Constant.KEY_DISPLAY_NAME, response.body().getLogin().get(0).getDisplayName());
                        sp.setValueString(Constant.KEY_LOGO, response.body().getLogin().get(0).getLogo());
                        sp.setValueInt(Constant.KEY_USER_ID, response.body().getLogin().get(0).getUserId());

                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();


                        sp.setValueString(Constant.KEY_USER_NAME, userName);
                        sp.setValueString(Constant.KEY_USER_PASSWORD, password);
                    }
                } else {
                    Support.showToast(LoginActivity.this, "Check your network");
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

}

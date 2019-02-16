package com.feed.feedo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feed.feedo.R;
import com.feed.feedo.model.CustumerExist;
import com.feed.feedo.model.ShivamSirQuestionModel;
import com.feed.feedo.network.ApiClient;
import com.feed.feedo.network.ApiClientInterface;
import com.feed.feedo.utils.Constant;
import com.feed.feedo.utils.SharedPreference;
import com.feed.feedo.utils.Support;
import com.trend.progress.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ActivityMobileNumber extends AppCompatActivity
{
    private EditText etMobileNumber;
    private SharedPreference sp;

    ApiClientInterface apiClientInterface;
    ProgressDialog progressBar;
    private ImageView ivLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custumer_mobile);
        sp = new SharedPreference(this);

        //Retrofit Initialization
        apiClientInterface = ApiClient.getApiClient().create(ApiClientInterface.class);
        progressBar = new com.trend.progress.ProgressDialog(ActivityMobileNumber.this);
        progressBar.setBarColor(getResources().getColor(R.color.app_color_dark));

        init();
    }

    private void init()
    {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);

        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        Glide.with(ActivityMobileNumber.this).setDefaultRequestOptions(requestOptions).load(sp.getValueString(Constant.KEY_LOGO)).into(ivLogo);

        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);

        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (Support.isValidateMobile(ActivityMobileNumber.this , etMobileNumber.getText().toString()))
                {
                    sp.setValueString(Constant.KEY_MOBILE_NUMBER ,  etMobileNumber.getText().toString());
                    isCustomerExist(etMobileNumber.getText().toString());
                }
                else
                {
                    Support.showSnackbar(ActivityMobileNumber.this, "Check Mobile No");
                }
            }
        });
    }


    private void isCustomerExist(String mobile)
    {
        progressBar.show();
        apiClientInterface.isCustomerExist(mobile).enqueue(new Callback<CustumerExist>() {
            @Override
            public void onResponse(Call<CustumerExist> call, Response<CustumerExist> response)
            {
                progressBar.dismiss();
                Log.d("AYUSH", response.isSuccessful() + "");

                if (response.isSuccessful())
                {
                    Intent i = new Intent(ActivityMobileNumber.this, ActivityCustumerInfo.class);

                    try
                    {
                        i.putExtra(Constant.KEY_CUSTOMER_NAME ,  response.body().getCustomer().get(0).getName());
                        i.putExtra(Constant.KEY_CUSTOMER_MOBILE ,  response.body().getCustomer().get(0).getMobile());
                        i.putExtra(Constant.KEY_CUSTOMER_DOB ,  response.body().getCustomer().get(0).getDOB());
                        i.putExtra(Constant.KEY_CUSTOMER_DOA ,  response.body().getCustomer().get(0).getDOA());
                        i.putExtra(Constant.KEY_CUSTOMER_GENDER ,  response.body().getCustomer().get(0).getGender());
                    }
                    catch (Exception e)
                    {
                        i.putExtra(Constant.KEY_CUSTOMER_MOBILE ,  etMobileNumber.getText().toString());
                        e.printStackTrace();
                    }

                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CustumerExist> call, Throwable t)
            {
                Log.d("Error", t.getMessage());
            }
        });
    }
}

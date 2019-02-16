package com.feed.feedo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feed.feedo.R;
import com.feed.feedo.model.BackgroundImageModel;
import com.feed.feedo.model.LogoutModel;
import com.feed.feedo.model.ShivamSirQuestionModel;
import com.feed.feedo.network.ApiClient;
import com.feed.feedo.network.ApiClientInterface;
import com.feed.feedo.utils.Constant;
import com.feed.feedo.utils.SharedPreference;
import com.feed.feedo.utils.Support;
import com.google.gson.Gson;
import com.trend.progress.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity
{
    private ImageView ivLogo;
    ApiClientInterface apiClientInterface;
    ProgressDialog progressBar;
    private ImageView ivBackground;
    private SharedPreference sp;
    private long lastPress;
    private String imageUrl;

    private TextView tvRestaurantName;
    private String myJson;

    Support support = new Support();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sp = new SharedPreference(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {

        }
        initToolBar(sp.getValueString(Constant.KEY_DISPLAY_NAME) +"");

        //Retrofit Initialization
        apiClientInterface = ApiClient.getApiClient().create(ApiClientInterface.class);
        progressBar = new ProgressDialog(DashboardActivity.this);
        progressBar.setBarColor(getResources().getColor(R.color.app_color_dark));

        init();
        getBackgroundImage(sp.getValueInt(Constant.KEY_CLIENT_ID));
        getQuiz(sp.getValueInt(Constant.KEY_CLIENT_ID));
    }



    private void getQuiz(final int clientId)
    {
        apiClientInterface.getQuiz(clientId).enqueue(new Callback<ShivamSirQuestionModel>()
        {
            @Override
            public void onResponse(Call<ShivamSirQuestionModel> call, Response<ShivamSirQuestionModel> response)
            {
                if (response.isSuccessful())
                {
                    myJson = new Gson().toJson(response);
                    sp.setValueString(Constant.KEY_QUIZ_JSON , myJson);
                }
                else
                {
                    Support.showToast(DashboardActivity.this, getString(R.string.cyn));
                }
            }

            @Override
            public void onFailure(Call<ShivamSirQuestionModel> call, Throwable t)
            {
                Log.d("Error" , t.getMessage());
            }
        });
    }

    private void initToolBar(String title)
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        setTitle(title);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        //mDrawerToggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_logout:

                Support.showMessageOKCancel(this, getString(R.string.dywtl), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        apiClientInterface.logout(sp.getValueString(Constant.KEY_USER_NAME), sp.getValueString(Constant.KEY_USER_PASSWORD)).enqueue(new Callback<LogoutModel>()
                        {
                            @Override
                            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response)
                            {
                                progressBar.dismiss();
                                Log.d("AYUSH", response.isSuccessful() + "");

                                if (response.isSuccessful())
                                {
                                    if (response.body().getLogin().get(0).getStatus() == 0)
                                    {
                                        Support.showAlertDialog(DashboardActivity.this , getString(R.string.ic));
                                    }
                                    else if (response.body().getLogin().get(0).getStatus() == -1)
                                    {
                                        Support.showAlertDialog(DashboardActivity.this , getString(R.string.ic));
                                    }
                                    else if(response.body().getLogin().get(0).getStatus() == 1)
                                    {
                                            sp.setValueBool(Constant.KEY_ISLOGIN, false);
//                                            Intent i = new Intent(DashboardActivity.this, SplashActivity.class);
//                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                                            startActivity(i);
//                                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            finish();
                                        System.exit(0);
                                     }
                                }
                                else
                                {
                                    Support.showToast(DashboardActivity.this, getString(R.string.cyn));
                                }
                            }

                            @Override
                            public void onFailure(Call<LogoutModel> call, Throwable t)
                            {
                                Log.d("Error" , t.getMessage());
                            }
                        });

                      sp.clearAll();
                      startActivity(new Intent(DashboardActivity.this, SplashActivity.class));
                      finish();
                    }
                });

                break;


            case R.id.action_change_language:

                Intent i = new Intent(DashboardActivity.this, ChooseLanguage.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPress > 5000)
        {
            Toast.makeText(getBaseContext(), R.string.pbate, Toast.LENGTH_LONG).show();
            lastPress = currentTime;
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void init()
    {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);

        tvRestaurantName = (TextView) findViewById(R.id.tvRestaurantName);
        tvRestaurantName.setText(sp.getValueString(Constant.KEY_DISPLAY_NAME));

        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        Glide.with(DashboardActivity.this).setDefaultRequestOptions(requestOptions).load(sp.getValueString(Constant.KEY_LOGO)).into(ivLogo);

        CardView cvFeedback = (CardView) findViewById(R.id.cvFeedback);
        cvFeedback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               startQuiz();
            }
        });
    }



    private void startQuiz()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(R.string.start_quiz);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int which)
            {
                if(Support.isNetworkOnline(DashboardActivity.this))
                {
                    Intent i = new Intent(DashboardActivity.this , QuizActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Support.showAlertDialog(DashboardActivity.this , getString(R.string.msg_check_internet));
                }
            }
        });

        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    private void getBackgroundImage(final int clientId)
    {
        apiClientInterface.getBackgroundImage(clientId).enqueue(new Callback<BackgroundImageModel>()
        {
            @Override
            public void onResponse(Call<BackgroundImageModel> call, Response<BackgroundImageModel> response)
            {
               try
               {
                   if (response.isSuccessful())
                   {
                       imageUrl = response.body().getResurces().get(0).getValue();
                       sp.setValueString(Constant.KEY_BACKGROUND_IMAGE_URL, imageUrl);
                       Glide.with(getApplicationContext()).load(response.body().getResurces().get(0).getValue()).into(ivBackground);
                   }
                   else
                   {
                       Support.showToast(DashboardActivity.this, getString(R.string.cyn));
                   }
               }
               catch (Exception e)
               {
                   e.printStackTrace();

                   RequestOptions requestOption = new RequestOptions().placeholder(R.drawable.bg_splash).centerCrop();
                   Glide.with(DashboardActivity.this).load(getResources().getDrawable(R.drawable.bg_splash))
                           .apply(requestOption)
                           .into(ivBackground);
               }
            }

            @Override
            public void onFailure(Call<BackgroundImageModel> call, Throwable t)
            {
                Log.d("Error", t.getMessage());
            }
        });
    }
}

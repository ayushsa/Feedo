package com.feed.feedo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feed.feedo.R;
import com.feed.feedo.utils.Constant;
import com.feed.feedo.utils.SharedPreference;

public class ActivityThankyou extends AppCompatActivity
{
    private ImageView ivLogo;
    private SharedPreference sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        sp = new SharedPreference(this);

        init();

        startTimer();
    }

    private void init()
    {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);

        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        Glide.with(ActivityThankyou.this).setDefaultRequestOptions(requestOptions).load(sp.getValueString(Constant.KEY_LOGO)).into(ivLogo);
    }

    private void startTimer()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity();
            }
        }, 10000);
    }

    //starts the Home Activity(DashboardActivity activity)
    void startActivity()
    {
        Intent i = null;
        i = new Intent(this, DashboardActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        this.finish();
    }
}

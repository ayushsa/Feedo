package com.feed.feedo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feed.feedo.R;
import com.feed.feedo.utils.Constant;
import com.feed.feedo.utils.SharedPreference;
import com.feed.feedo.utils.Support;

public class SplashActivity extends AppCompatActivity
{
    SharedPreference sp;
    private ImageView ivSplash;
    private ImageView ivLogo;
    private Support support = new Support();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        sp = new SharedPreference(this);

        init();
        startAnimation();
        startTimer();
    }

    private void init()
    {
        ivSplash = (ImageView) findViewById(R.id.ivSplash);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);

        RequestOptions requestOption = new RequestOptions().placeholder(R.drawable.bg_splash).centerCrop();

        Glide.with(SplashActivity.this).load(getResources().getDrawable(R.drawable.bg_splash))
                .apply(requestOption)
                .into(ivSplash);
    }

    private void startAnimation() 
    {

        AnimationSet animSet;

        animSet = new AnimationSet(true);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);

        final RotateAnimation animRotate = new RotateAnimation(0.0f,20.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        animRotate.setDuration(2000);
        animRotate.setFillAfter(true);
        animSet.addAnimation(animRotate);
        animSet.setRepeatCount(-1);
        ivLogo.startAnimation(animRotate);

    }

    //timer for showing splash screen
    private void startTimer()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity();
            }
        }, 2000);
    }

    //starts the Home Activity(DashboardActivity activity)
    void startActivity()
    {
        Intent intent = null;

        if (sp.getValueInt(Constant.SELECTED_LANGUAGE_ID) == 0)
        {
            // get selected lang from sp if its 0
            intent = new Intent(this, ChooseLanguage.class); // start change language activity
        }
        else
        {
            // set language then start signIn activity
            setLanguage();

            if (!sp.getValueBoolean(Constant.KEY_ISLOGIN))
            {
                intent = new Intent(this, LoginActivity.class);
            }
            else if (sp.getValueBoolean(Constant.KEY_ISLOGIN))
            {
                intent = new Intent(this, DashboardActivity.class);
            }
        }

        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        this.finish();
    }



    private void setLanguage()
    {
        int langId = sp.getValueInt(Constant.SELECTED_LANGUAGE_ID); // get selected languageId from shared preference

        String selectedLang = null;
        if (langId != 0)
        { // if langeId is not 0 i.e. language is selected by user
            selectedLang = Constant.LANG_CODE[langId - 1];
            // get selected language
        }
        else
        {
            selectedLang = Constant.LANG_CODE[0]; // get first language as selected language
        }

        support.setDefaultLang(this, selectedLang); // set app default language to the selected language
    }
}
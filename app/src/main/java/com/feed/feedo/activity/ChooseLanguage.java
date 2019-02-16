package com.feed.feedo.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.feed.feedo.R;
import com.feed.feedo.utils.Constant;
import com.feed.feedo.utils.SharedPreference;
import com.feed.feedo.utils.Support;


public class ChooseLanguage extends AppCompatActivity implements View.OnClickListener
{
    LinearLayout llLogo;
    float mWidthScale, mHeightScale;
    LinearLayout llContent;
    String selectedLang;
    SharedPreference sp;
    Support support = new Support();
    int langId;
    private int thumbnailTop, thumbnailLeft, thumbnailWidth, thumbnailHeight, mLeftDelta, mTopDelta;
    private ImageView ivDoneHindi, ivDoneEnglish;
    private RelativeLayout rlEnglish;
    private RelativeLayout rlHindi;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sp = new SharedPreference(this);

        langId = sp.getValueInt(Constant.SELECTED_LANGUAGE_ID);
        // get selected language from shared preference
        if (langId == 0)
        {
            // if langId is 0, mean no language is yet selected
            langId = 1;
            // so consider  language 1 as selected language
        }
        Configuration c = support.setDefaultLang(this, selectedLang); // set default lang of application
        onConfigurationChanged(c);   // change configuration to selected language for current view
        // push to google analytics
    }


    private void findViews()
    {  // find views by ids
        llLogo = (LinearLayout) findViewById(R.id.llLogo);
        llContent = (LinearLayout) findViewById(R.id.llContent);

        ivDoneHindi = (ImageView) findViewById(R.id.ivDoneHindi);
        ivDoneEnglish = (ImageView) findViewById(R.id.ivDoneEnglish);

        rlEnglish = (RelativeLayout) findViewById(R.id.rlEnglish);
        rlHindi = (RelativeLayout) findViewById(R.id.rlHindi);
        
        rlEnglish.setOnClickListener(this);
        rlHindi.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rlEnglish:
                // when user click on english button
                ivDoneEnglish.setVisibility(View.VISIBLE);
                // visible english image view
                selectedLang = Constant.LANG_CODE[0];
                // set selected lang at index 1
                langId = 1; // set langId to 1
                support.setDefaultLang(this, selectedLang);
                // set app default language to selected language
                sp.setValueInt(Constant.SELECTED_LANGUAGE_ID, langId);
                // set value in shared preference
                startSignIn();
                // invoke startSignIn method
                break;

            case R.id.rlHindi:
                // when user click on hindi button
                ivDoneHindi.setVisibility(View.VISIBLE);
                // visible hindi image view
                selectedLang = Constant.LANG_CODE[1];
                // set selected lang at index 0
                langId = 2;
                // set langId to 2
                support.setDefaultLang(this, selectedLang);
                sp.setValueInt(Constant.SELECTED_LANGUAGE_ID, langId);
                // set value in shared preference
                startSignIn(); // invoke startSignIn method
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_choose_lang);   // set view for the activity
        findViews();
    }

    private void startSignIn()
    {
        // get logo current position and start signin activity
        Intent intent;
        intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        this.finish();
    }
}

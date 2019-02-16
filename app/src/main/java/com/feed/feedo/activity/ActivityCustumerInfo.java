package com.feed.feedo.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feed.feedo.R;
import com.feed.feedo.model.ShivamSirQuestionModel;
import com.feed.feedo.network.ApiClient;
import com.feed.feedo.network.ApiClientInterface;
import com.feed.feedo.utils.Constant;
import com.feed.feedo.utils.SharedPreference;
import com.feed.feedo.utils.Support;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCustumerInfo extends AppCompatActivity {

    public static int day;
    public static int month;
    public static int year = 1980;

    public static int day_1;
    public static int month_1;
    public static int year_1 = 1990;

    private LinearLayout llDob;
    private LinearLayout llDoa;

    private TextView tvDob;
    private TextView tvDoa;
    private RadioGroup rgGender;

    ApiClientInterface apiClientInterface;
    com.trend.progress.ProgressDialog progressBar;
    private CardView cvCustumerInfo;

    private EditText etName;
    private EditText etMobile;
    private String gender = "M";
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private SharedPreference sp;
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custumer_info);
        sp = new SharedPreference(ActivityCustumerInfo.this);

        //Retrofit Initialization
        apiClientInterface = ApiClient.getApiClient().create(ApiClientInterface.class);

        progressBar = new com.trend.progress.ProgressDialog(ActivityCustumerInfo.this);
        progressBar.setBarColor(getResources().getColor(R.color.app_color_dark));

        init();

        getIntentExtras();
    }

    private void getIntentExtras()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            try
            {
                etName.setText(extras.getString(Constant.KEY_CUSTOMER_NAME));
                etMobile.setText(extras.getString(Constant.KEY_CUSTOMER_MOBILE));
                tvDob.setText(extras.getString(Constant.KEY_CUSTOMER_DOB));
                tvDoa.setText(extras.getString(Constant.KEY_CUSTOMER_DOA));

                if (extras.getString(Constant.KEY_CUSTOMER_GENDER).equals("M"))
                {
                    rbMale.setChecked(true);
                    rbFemale.setChecked(false);
                }
                else if (extras.getString(Constant.KEY_CUSTOMER_GENDER).equals("F"))
                {
                    rbFemale.setChecked(true);
                    rbMale.setChecked(false);
                }
            }
           catch (Exception e)
           {
               e.printStackTrace();
           }
        }
    }

    private void init()
    {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo);
        requestOptions.error(R.drawable.logo);

        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        Glide.with(ActivityCustumerInfo.this).setDefaultRequestOptions(requestOptions).load(sp.getValueString(Constant.KEY_LOGO)).into(ivLogo);


        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rbMale = (RadioButton) findViewById(R.id.rMale);
        rbFemale = (RadioButton) findViewById(R.id.rFemale);
        
        cvCustumerInfo = (CardView) findViewById(R.id.cvCustumerInfo);

        etName = (EditText) findViewById(R.id.etName);
        etMobile = (EditText) findViewById(R.id.etMobile);
        tvDob = (TextView) findViewById(R.id.tvDob);
        tvDoa = (TextView) findViewById(R.id.tvDoa);

        llDob = (LinearLayout) findViewById(R.id.llDob);
        llDoa = (LinearLayout) findViewById(R.id.llDoa);


        llDob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dpd = new DatePickerDialog(ActivityCustumerInfo.this, mDateSetListener, year, month, day);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
            }
        });

        llDoa.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dpd = new DatePickerDialog(ActivityCustumerInfo.this, mDateSetListener2, year_1, month_1, day_1);
                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.show();
            }
        });


        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                submitFeedback(etName.getText().toString(),
                        etMobile.getText().toString(),
                        gender, tvDob.getText().toString(),
                        tvDoa.getText().toString(),
                        sp.getValueString(Constant.KEY_QUESTIONS_ID),
                        sp.getValueString(Constant.KEY_OPTIONS_ID),
                        Support.getDeviceId(ActivityCustumerInfo.this),
                        sp.getValueInt(Constant.KEY_USER_ID),
                        sp.getValueInt(Constant.KEY_CLIENT_ID)
                        );
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup rg, int id)
            {
                // genderValue = ((RadioButton)
                // d.findViewById(id)).getText().toString().trim();

                switch (id)
                {
                    case R.id.rMale:

                        ((TextView) findViewById(R.id.rMale)).setTextColor(Color.BLACK);

                         gender = "M";

                        break;

                    case R.id.rFemale:

                        ((TextView) findViewById(R.id.rFemale)).setTextColor(Color.BLACK);

                        gender = "F";

                        break;

                    default:
                        break;
                }

            }
        });
    }

    private void submitFeedback(String name, String mobile, String gender, String dob, String doa, String questionIds, String answer, String deviceId, int userId, int clientId)
    {
        progressBar.show();
        apiClientInterface.submitFeedback(name , mobile, gender, dob, doa, questionIds, answer, deviceId, userId, clientId).enqueue(new Callback<ShivamSirQuestionModel>()
        {
            @Override
            public void onResponse(Call<ShivamSirQuestionModel> call, Response<ShivamSirQuestionModel> response)
            {
                progressBar.dismiss();
                Log.d("AYUSH", response.isSuccessful() + "");

                if (response.isSuccessful())
                {
                    Intent i = new Intent(ActivityCustumerInfo.this, ActivityThankyou.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ShivamSirQuestionModel> call, Throwable t)
            {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int myear, int monthOfYear, int dayOfMonth)
        {
            day = dayOfMonth;
            month = monthOfYear;
            year = myear;

            updateDisplay();
        }
    };

    // update date in text view
    private void updateDisplay()
    {
        tvDob.setText(new StringBuilder()
                // month is 0 base so add 1
                .append(year).append("-").append(month + 1).append("-").append(day));

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int myear, int monthOfYear, int dayOfMonth)
        {
            year_1 = myear;
            month_1 = monthOfYear;
            day_1 = dayOfMonth;

            updateDisplay2();
        }
    };

    // update date in text view
    private void updateDisplay2()
    {
        tvDoa.setText(new StringBuilder()
                // month is 0 base so add 1
                .append(year_1).append("-").append(month_1 + 1).append("-").append(day_1).append(""));

    }


}

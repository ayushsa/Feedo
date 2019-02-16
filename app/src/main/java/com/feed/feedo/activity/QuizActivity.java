package com.feed.feedo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feed.feedo.R;
import com.feed.feedo.model.MasterQuestionResultModel;
import com.feed.feedo.model.Question;
import com.feed.feedo.model.QuestionModel;
import com.feed.feedo.model.QuestionResultModel;
import com.feed.feedo.model.ShivamSirQuestionModel;
import com.feed.feedo.utils.Constant;
import com.feed.feedo.utils.SharedPreference;
import com.feed.feedo.utils.Support;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements ApiRequest.OnApiRequestCompletedListener
{
   // String quesJSON = "{\"StatusCode\":200,\"Quiz\":{\"Id\":1,\"Name\":\"First Quiz\",\"Questions\":[{\"Id\":1,\"Title\":\"Value of 10+20\",\"CreatedDate\":\"2016-12-08T10:22:11.443\",\"Options\":[{\"Name\":\"10\",\"IsCorrect\":false,\"Id\":1},{\"Name\":\"20\",\"IsCorrect\":false,\"Id\":2},{\"Name\":\"30\",\"IsCorrect\":true,\"Id\":3},{\"Name\":\"35\",\"IsCorrect\":false,\"Id\":4}]},{\"Id\":2,\"Title\":\"Value of 20 * 20\",\"CreatedDate\":\"2016-12-08T10:22:23.9\",\"Options\":[{\"Name\":\"350\",\"IsCorrect\":false,\"Id\":5},{\"Name\":\"150\",\"IsCorrect\":false,\"Id\":6},{\"Name\":\"100\",\"IsCorrect\":false,\"Id\":7},{\"Name\":\"400\",\"IsCorrect\":true,\"Id\":8}]},{\"Id\":3,\"Title\":\"Value of 40 / 20\",\"CreatedDate\":\"2016-12-08T10:22:31.383\",\"Options\":[{\"Name\":\"20\",\"IsCorrect\":false,\"Id\":9},{\"Name\":\"2\",\"IsCorrect\":true,\"Id\":10},{\"Name\":\"15\",\"IsCorrect\":false,\"Id\":11},{\"Name\":\"5\",\"IsCorrect\":false,\"Id\":12}]},{\"Id\":4,\"Title\":\"Value of 140 - 20\",\"CreatedDate\":\"2016-12-08T10:22:40.523\",\"Options\":[{\"Name\":\"120\",\"IsCorrect\":true,\"Id\":13},{\"Name\":\"100\",\"IsCorrect\":false,\"Id\":14},{\"Name\":\"90\",\"IsCorrect\":false,\"Id\":15},{\"Name\":\"80\",\"IsCorrect\":false,\"Id\":16}]}]}}";


    List<Question> questions;
    TextView tvStartIn, tvQuestionCounter, tvQuestion, tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    RelativeLayout rlStartIn;
    int correctAnswerCounter;
    int questionIndex = 0;
    boolean isQuizOver = false;
    Button bNext, bPrevious, bRestartQuiz;
    MenuItem vEndQuiz;
    private CountDownTimer c;
    private String name;
    private Button btSubmitQuiz;
    private SharedPreference sp;
    private String backgroundImage;
    private String quizId;
    private ImageView ivBackground;

    private String shivamJSON;

    private ImageButton ibAnswer1;
    private ImageButton ibAnswer2;
    private ImageButton ibAnswer3;
    private ImageButton ibAnswer4;

    // String shivamJSON = "{\"questions\":[{\"Id\":2,\"Title\":\"How Would You Rate Our Staff’s Ability to Meet Your Needs?\",\"Opt1\":\"Awsome\",\"Opt2\":\"Good\",\"Opt3\":\"Satisfactory\",\"Opt4\":\"Rubbish\",\"TitleH\":\"स्टाफ का व्यव्हार कैसा है ?\",\"Opt1H\":\"बहुत बढ़िया \",\"Opt2H\":\"बढ़िया \",\"Opt3H\":\"\\r\\nसंतोषजनक \",\"Opt4H\":\"घटिया\",\"ClientId\":1,\"IsActive\":true,\"icon1\":null,\"icon2\":null,\"icon3\":null,\"icon4\":null,\"iconselected1\":null,\"iconselected2\":null,\"iconselected3\":null,\"iconselected4\":null},{\"Id\":3,\"Title\":\"How Would You Rate the Cleanliness of the Restaurant?\",\"Opt1\":\"5 Star\",\"Opt2\":\"4 Star\",\"Opt3\":\"3 star\",\"Opt4\":\"2 Star\",\"TitleH\":\"स्वछता पर आप हमे कितने अंक देंगे \",\"Opt1H\":\"5 सितारे \",\"Opt2H\":\"4 सितारे \",\"Opt3H\":\"3 सितारे \",\"Opt4H\":\"2 सितारे \",\"ClientId\":1,\"IsActive\":true,\"icon1\":null,\"icon2\":null,\"icon3\":null,\"icon4\":null,\"iconselected1\":null,\"iconselected2\":null,\"iconselected3\":null,\"iconselected4\":null},{\"Id\":6,\"Title\":\"How Would You Rate the Cleanliness of the Restaurant?\",\"Opt1\":\"5 Star\",\"Opt2\":\"4 Star\",\"Opt3\":\"3 star\",\"Opt4\":\"2 Star\",\"TitleH\":\"स्वछता पर आप हमे कितने अंक देंगे \",\"Opt1H\":\"5 सितारे \",\"Opt2H\":\"4 सितारे \",\"Opt3H\":\"3 सितारे \",\"Opt4H\":\"2 सितारे \",\"ClientId\":1,\"IsActive\":true,\"icon1\":null,\"icon2\":null,\"icon3\":null,\"icon4\":null,\"iconselected1\":null,\"iconselected2\":null,\"iconselected3\":null,\"iconselected4\":null},{\"Id\":7,\"Title\":\"How Would You Rate the Cleanliness of the Restaurant?\",\"Opt1\":\"5 Star\",\"Opt2\":\"4 Star\",\"Opt3\":\"3 star\",\"Opt4\":\"2 Star\",\"TitleH\":\"स्वछता पर आप हमे कितने अंक देंगे \",\"Opt1H\":\"5 सितारे \",\"Opt2H\":\"4 सितारे \",\"Opt3H\":\"3 सितारे \",\"Opt4H\":\"2 सितारे \",\"ClientId\":1,\"IsActive\":true,\"icon1\":null,\"icon2\":null,\"icon3\":null,\"icon4\":null,\"iconselected1\":null,\"iconselected2\":null,\"iconselected3\":null,\"iconselected4\":null},{\"Id\":8,\"Title\":\"How Would You Rate the Cleanliness of the Restaurant?\",\"Opt1\":\"5 Star\",\"Opt2\":\"4 Star\",\"Opt3\":\"3 star\",\"Opt4\":\"2 Star\",\"TitleH\":\"स्वछता पर आप हमे कितने अंक देंगे \",\"Opt1H\":\"5 सितारे \",\"Opt2H\":\"4 सितारे \",\"Opt3H\":\"3 सितारे \",\"Opt4H\":\"2 सितारे \",\"ClientId\":1,\"IsActive\":true,\"icon1\":null,\"icon2\":null,\"icon3\":null,\"icon4\":null,\"iconselected1\":null,\"iconselected2\":null,\"iconselected3\":null,\"iconselected4\":null}]}";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        sp = new SharedPreference(this);

//        Bundle b = getIntent().getExtras();
//        if (b != null)
//        {
//            backgroundImage = b.getString("KEY_BACKGROUND_IMAGE");
//
//            shivamJSON = b.getString("KEY_QUIZ_JSON");
//        }

        backgroundImage = sp.getValueString(Constant.KEY_BACKGROUND_IMAGE_URL);
        shivamJSON = sp.getValueString(Constant.KEY_QUIZ_JSON);

        initToolbar();
        findViews();

       // getQuizData();

        init(shivamJSON);
    }

    private void getQuizData()
    {
       // Log.d("ContentId", contentID + "");
        int lang_id = sp.getValueInt(Constant.SELECTED_LANGUAGE_ID);

        if (lang_id == 0)
        {
            lang_id = 1;
        }

        Log.d("LangID", lang_id + "");

//        if (Support.isNetworkOnline(this))
//        {
//            String URL = Constant.SERVER_URL + "GetQuiz?TrainingContentId=" + contentID + "&LanguageId=" + lang_id;
//            new ApiRequest(URL, QuizActivity.this, Constant.GET, null, this, true, 1).execute();
//        }
//        else
//        {
//            Support.showAlertDialog(this , getString(R.string.msg_check_internet));
//        }
    }

    private void findViews()
    {
        ivBackground = (ImageView) findViewById(R.id.ivBackground);
        Glide.with(QuizActivity.this).load(backgroundImage).into(ivBackground);
        
        rlStartIn = (RelativeLayout) findViewById(R.id.rlStartIn);
        rlStartIn.setVisibility(View.VISIBLE);

        bRestartQuiz = (Button) findViewById(R.id.bRestartQuiz);

        btSubmitQuiz = (Button) findViewById(R.id.btSubmitQuiz);

        tvStartIn = (TextView) findViewById(R.id.tvStartIn);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvQuestionCounter = (TextView) findViewById(R.id.tvQuestionCounter);

        tvAnswer1 = (TextView) findViewById(R.id.tvAnswer1);
        tvAnswer2 = (TextView) findViewById(R.id.tvAnswer2);
        tvAnswer3 = (TextView) findViewById(R.id.tvAnswer3);
        tvAnswer4 = (TextView) findViewById(R.id.tvAnswer4);

        ibAnswer1 = (ImageButton) findViewById(R.id.ibAnswer1);
        ibAnswer2 = (ImageButton) findViewById(R.id.ibAnswer2);
        ibAnswer3 = (ImageButton) findViewById(R.id.ibAnswer3);
        ibAnswer4 = (ImageButton) findViewById(R.id.ibAnswer4);


        bPrevious = (Button) findViewById(R.id.bPrevious);
        bNext = (Button) findViewById(R.id.bNext);
        bPrevious.setVisibility(View.INVISIBLE);
        bNext.setVisibility(View.INVISIBLE);
        btSubmitQuiz.setVisibility(View.INVISIBLE);
    }


    private void init(String quesJSON)
    {
//        Log.d("quesJson", quesJSON);
        name = getIntent().getStringExtra("name") + "";

        try
        {
            JSONObject jsonObject = new JSONObject(quesJSON);
            JSONObject obj = jsonObject.getJSONObject("body");
            JSONArray subArray = obj.getJSONArray("questions");

            if (subArray.length() == 0)
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setMessage(R.string.qdna);

                alertDialog.setNegativeButton(R.string.yes_1, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                        finish();
                    }
                });
                alertDialog.show();
            }
            else
            {
                // quizId = new JSONObject(quesJSON).getJSONArray("questions").get(0).getString("Id");
                // quizId = new JSONObject(quesJSON).getJSONObject("Quiz").getString("Id");
               // String d = new JSONObject(quesJSON).getJSONObject("Quiz").getJSONArray("Questions").toString();

                String d = obj.getJSONArray("questions").toString();

                questions = Arrays.asList(new Gson().fromJson(d, Question[].class));
                setNextQuestion(questionIndex);

                new CountDownTimer(4000, 1000)
                {
                    @Override
                    public void onTick(long l)
                    {
                        String startIn = getString(R.string.quiz_start_in) + (l / 1000) + getString(R.string.sec);
                        tvStartIn.setText(startIn);
                    }

                    @Override
                    public void onFinish()
                    {
                        rlStartIn.setVisibility(View.GONE);
                    }
                }.start();
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    public void invokePrevious(View v)
    {
        questionIndex--;
        setNextQuestion(questionIndex);
    }

    public void invokeNext(View v)
    {
        if (questions.get(questionIndex).getGivenAnswers() != -1)
        {
            questionIndex++;
            setNextQuestion(questionIndex);
        }
        else
        {
            Support.showAlertDialog(this, getString(R.string.give));
        }
    }

    public void invokeAnswerClicked(View v)
    {
        resetAllAns();
        int index = Integer.parseInt(v.getTag().toString());

        v.setBackgroundResource(R.drawable.btn_answer_focus);
        questions.get(questionIndex).setGivenAnswers(index);
    }

    private void resetAllAns()
    {
        tvAnswer1.setBackgroundResource(R.drawable.btn_answer);
        tvAnswer2.setBackgroundResource(R.drawable.btn_answer);
        tvAnswer3.setBackgroundResource(R.drawable.btn_answer);
        tvAnswer4.setBackgroundResource(R.drawable.btn_answer);

        ibAnswer1.setBackgroundResource(R.drawable.btn_answer);
        ibAnswer2.setBackgroundResource(R.drawable.btn_answer);
        ibAnswer3.setBackgroundResource(R.drawable.btn_answer);
        ibAnswer4.setBackgroundResource(R.drawable.btn_answer);
    }


    @Override
    public void onBackPressed()
    {
        if (c != null)
        {
            c.cancel();
        }

        super.onBackPressed();
    }

    void setNextQuestion(int i)
    {
        resetAllAns();
        if (i == 0)
        {
            bPrevious.setVisibility(View.GONE);
            bNext.setVisibility(View.VISIBLE);
            btSubmitQuiz.setVisibility(View.GONE);
        }
        else if (i + 1 == questions.size())
        {
            bPrevious.setVisibility(View.VISIBLE);
            bNext.setVisibility(View.GONE);
            btSubmitQuiz.setVisibility(View.VISIBLE);
        }
        else
        {
            bNext.setVisibility(View.VISIBLE);
            bPrevious.setVisibility(View.VISIBLE);
            btSubmitQuiz.setVisibility(View.GONE);
        }


        Question item = questions.get(i);
//        if (item.getOptions().size() == 0)
//        {
//            //setNextQuestion(i + 1);
//            this.finish();
//
//        }
//        else
//        {


        if (sp.getValueInt(Constant.SELECTED_LANGUAGE_ID) == 0)
        {
            tvQuestion.setText(item.getTitle());
        }
        else if (sp.getValueInt(Constant.SELECTED_LANGUAGE_ID) == 1)
        {
            tvQuestion.setText(item.getTitle());
        }
        else if (sp.getValueInt(Constant.SELECTED_LANGUAGE_ID) == 2)
        {
            tvQuestion.setText(item.getTitleH());
        }


            if (item.getDisplay() == 0)
            {
                tvAnswer1.setVisibility(View.GONE);
                tvAnswer2.setVisibility(View.GONE);
                tvAnswer3.setVisibility(View.GONE);
                tvAnswer4.setVisibility(View.GONE);

                ibAnswer1.setVisibility(View.VISIBLE);
                ibAnswer2.setVisibility(View.VISIBLE);
                ibAnswer3.setVisibility(View.VISIBLE);
                ibAnswer4.setVisibility(View.VISIBLE);


//                ibAnswer1.setImageResource(R.drawable.awsome);
//                ibAnswer2.setImageResource(R.drawable.awsome);
//                ibAnswer3.setImageResource(R.drawable.awsome);
//                ibAnswer4.setImageResource(R.drawable.awsome);


                Picasso.get().load(String.valueOf(item.getIcon1())).into(ibAnswer1);
                Picasso.get().load(String.valueOf(item.getIcon2())).into(ibAnswer2);
                Picasso.get().load(String.valueOf(item.getIcon3())).into(ibAnswer3);
                Picasso.get().load(String.valueOf(item.getIcon4())).into(ibAnswer4);
            }
            else if (item.getDisplay() == 1)
            {
                tvAnswer1.setVisibility(View.VISIBLE);
                tvAnswer2.setVisibility(View.VISIBLE);
                tvAnswer3.setVisibility(View.VISIBLE);
                tvAnswer4.setVisibility(View.VISIBLE);

                ibAnswer1.setVisibility(View.GONE);
                ibAnswer2.setVisibility(View.GONE);
                ibAnswer3.setVisibility(View.GONE);
                ibAnswer4.setVisibility(View.GONE);

                if (sp.getValueInt(Constant.SELECTED_LANGUAGE_ID) == 0)
                {
                    tvAnswer1.setText(item.getOpt1());
                    tvAnswer2.setText(item.getOpt2());
                    tvAnswer3.setText(item.getOpt3());
                    tvAnswer4.setText(item.getOpt4());
                }
                else if (sp.getValueInt(Constant.SELECTED_LANGUAGE_ID) == 1)
                {
                    tvAnswer1.setText(item.getOpt1());
                    tvAnswer2.setText(item.getOpt2());
                    tvAnswer3.setText(item.getOpt3());
                    tvAnswer4.setText(item.getOpt4());
                }
                else if (sp.getValueInt(Constant.SELECTED_LANGUAGE_ID) == 2)
                {
                    tvAnswer1.setText(item.getOpt1H());
                    tvAnswer2.setText(item.getOpt2H());
                    tvAnswer3.setText(item.getOpt3H());
                    tvAnswer4.setText(item.getOpt4H());
                }
            }



            String s = getString(R.string.question) + (i + 1) + "/" + questions.size();
            tvQuestionCounter.setText(s);
            int givenAns = item.getGivenAnswers();
            switch (givenAns)
            {
                case 1:
                    tvAnswer1.setBackgroundResource(R.drawable.btn_answer_focus);
                    ibAnswer1.setBackgroundResource(R.drawable.btn_answer_focus);

                    break;
                case 2:
                    tvAnswer2.setBackgroundResource(R.drawable.btn_answer_focus);
                    ibAnswer2.setBackgroundResource(R.drawable.btn_answer_focus);

                    break;
                case 3:
                    tvAnswer3.setBackgroundResource(R.drawable.btn_answer_focus);
                    ibAnswer3.setBackgroundResource(R.drawable.btn_answer_focus);

                    break;
                case 4:
                    tvAnswer4.setBackgroundResource(R.drawable.btn_answer_focus);
                    ibAnswer4.setBackgroundResource(R.drawable.btn_answer_focus);

                    break;
            }
    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
    }


    @Override
    public void onApiRequestComplete(int statusCode, String result, int reqCode)
    {
        Log.d("Get Quiz Data", result);
        if (statusCode == 200)
        {
            if (reqCode == 1)
            {
                if (result.equals(Constant.RESULT_CANCEL))
                {
                    finish();
                }
                else
                {
                    try
                    {
                        init(result);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage(R.string.qdna);

            alertDialog.setNegativeButton(R.string.yes_1 , new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                    finish();
                }
            });
            alertDialog.show();
        }

    }


    public void invokesubmitQuiz(View view)
    {
        //performSubmit();

        if (questions.get(questionIndex).getGivenAnswers() != -1)
        {
            List<QuestionResultModel> questionResultModels = new ArrayList<>();
            c = null;
            correctAnswerCounter = 0;

            for (int i = 0; i < questions.size(); i++)
            {
                Log.d("D1" , questions.get(i).getId()+"");


                QuestionResultModel singleQues = new QuestionResultModel();
                singleQues.setQuestionId(questions.get(i).getId());

                int index = questions.get(i).getGivenAnswers() - 1;
                if (index >= 0)
                {
                    singleQues.setOptionId(questions.get(i).getGivenAnswers());
                }
                else
                {
                    singleQues.setOptionId(0);
                }
                questionResultModels.add(singleQues);
            }

            StringBuilder result_1 = new StringBuilder();
            StringBuilder result_2 = new StringBuilder();
            for(QuestionResultModel string : questionResultModels)
            {
                result_1.append(string.getQuestionId());
                result_1.append(",");

                result_2.append(string.getOptionId());
                result_2.append(",");
            }

//        String inputData = new Gson().toJson(questionResultModels);
//        Log.d("Deepak Result" , inputData);



            Log.d("Question Ids", result_1.substring(0, result_1.length() - 1));
            Log.d("Options Ids", result_2.substring(0, result_2.length() - 1));

            sp.setValueString(Constant.KEY_QUESTIONS_ID , result_1.substring(0, result_1.length() - 1));
            sp.setValueString(Constant.KEY_OPTIONS_ID , result_2.substring(0, result_2.length() - 1));

            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(QuizActivity.this);
            builder.setTitle(" Alert");
            builder.setMessage(getString(R.string.quiz_submitted));
            builder.setIcon(R.drawable.logo);
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent i = new Intent(QuizActivity.this, ActivityMobileNumber.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            finish();
                        }
                    });
            builder.show();
        }
        else
        {
            Support.showAlertDialog(this, getString(R.string.give));
        }


    }

//    private void performSubmit()
//    {
//        MasterQuestionResultModel resultModel = new MasterQuestionResultModel();
//        resultModel.setInternId(sp.getValueInt(Constant.KEY_INTERN_ID) + "");
//        Log.d("Intern ID", sp.getValueInt(Constant.KEY_INTERN_ID) + "");
//        resultModel.setQuizId(quizId);
//
//
//        List<QuestionResultModel> questionResultModels = new ArrayList<>();
//        c = null;
//        correctAnswerCounter = 0;
//
//        for (int i = 0; i < questions.size(); i++)
//        {
//            QuestionResultModel singleQues = new QuestionResultModel();
//            singleQues.setQuestionId(questions.get(i).getId());
//          //  int index = questions.get(i).getGivenAnswers() - 1;
//            if (index >= 0)
//            {
//                singleQues.setOptionId(questions.get(i).getOptions().get(index).getId());
//            }
//            else
//            {
//                singleQues.setOptionId(0);
//            }
//            questionResultModels.add(singleQues);
//        }
//
//        resultModel.setQuestionResult(questionResultModels);
//        String inputData = new Gson().toJson(resultModel);
//
//        String URL = Constant.SERVER_URL + Constant.METHOD_SYNC_QUIZDATA;
//        new ApiRequest(URL, this, ApiRequest.POST, inputData, new ApiRequest.OnApiRequestCompletedListener()
//        {
//            @Override
//            public void onApiRequestComplete(int statusCode, String result, int reqCode)
//            {
//                Log.d("Quiz Result", result);
//                try
//                {
//                    JSONObject jobj = new JSONObject(result);
//                    String message = jobj.getString("Message");
//
//
//                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(QuizActivity.this);
//                    builder.setTitle(" Alert");
//                    builder.setMessage(getString(R.string.quiz_submitted));
//                    builder.setIcon(R.drawable.logo);
//                    builder.setCancelable(false);
//                    builder.setPositiveButton(getResources().getString(R.string.ok),
//                            new DialogInterface.OnClickListener()
//                            {
//                                public void onClick(DialogInterface dialog, int which)
//                                {
//                                    Intent i = new Intent(QuizActivity.this, ActivityMobileNumber.class);
//                                    startActivity(i);
//                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                                    finish();
//                                }
//                            });
//                    builder.show();
//                }
//                catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
//
//            }
//        }, true, 1).execute();
//    }


}

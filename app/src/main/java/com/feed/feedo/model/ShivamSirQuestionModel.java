package com.feed.feedo.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ShivamSirQuestionModel
{
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    public List<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }


}

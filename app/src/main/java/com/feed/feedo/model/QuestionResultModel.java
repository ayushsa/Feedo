package com.feed.feedo.model;

/**
 * Created by admin on 20/12/16.
 */

public class QuestionResultModel
{
    int QuestionId;
    int OptionId;

    public int getQuestionId()
    {
        return QuestionId;
    }

    public void setQuestionId(int questionId)
    {
        QuestionId = questionId;
    }

    public int getOptionId()
    {
        return OptionId;
    }

    public void setOptionId(int optionId)
    {
        OptionId = optionId;
    }
}

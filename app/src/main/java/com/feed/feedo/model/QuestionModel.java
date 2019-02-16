package com.feed.feedo.model;

import java.util.List;

/**
 * Created by HP_User on 16-10-2015.
 */
public class QuestionModel
{
    private int Id;

    private String Title;
    private List<OptionsModel> Options;
    private int GivenAnswers = -1;

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<OptionsModel> getOptions() {
        return Options;
    }

    public void setOptions(List<OptionsModel> options) {
        Options = options;
    }

    public int getGivenAnswers()
    {
        return GivenAnswers;
    }

    public void setGivenAnswers(int givenAnswers)
    {
        GivenAnswers = givenAnswers;
    }

    public class OptionsModel
    {
        String Name;
        boolean IsCorrect;
        int Id;


        public int getId()
        {
            return Id;
        }

        public boolean isCorrect()
        {
            return IsCorrect;
        }

        public String getName()
        {
            return Name;
        }

        public void setName(String name)
        {
            Name = name;
        }
    }
}

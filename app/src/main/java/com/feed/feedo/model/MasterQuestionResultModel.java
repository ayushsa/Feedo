package com.feed.feedo.model;

import java.util.List;


public class MasterQuestionResultModel {

    String InternId;
    String QuizId;
    List<QuestionResultModel> QuestionResult;

    public String getInternId() {
        return InternId;
    }

    public void setInternId(String internId) {
        InternId = internId;
    }

    public String getQuizId() {
        return QuizId;
    }

    public void setQuizId(String quizId) {
        QuizId = quizId;
    }

    public List<QuestionResultModel> getQuestionResult() {
        return QuestionResult;
    }

    public void setQuestionResult(List<QuestionResultModel> questionResult) {
        QuestionResult = questionResult;
    }


}


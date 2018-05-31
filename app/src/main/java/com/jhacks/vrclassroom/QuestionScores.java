package com.jhacks.vrclassroom;

public class QuestionScores {

    private String scoreA;
    private String scoreB;
    private String scoreC;
    private String scoreD;

    public QuestionScores()
    {
        // Empty constructor requirement by Firebase deserializer
    }

    public QuestionScores(String scoreA, String scoreB, String scoreC, String scoreD)
    {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.scoreC = scoreC;
        this.scoreD = scoreD;

    }

    //public String getQuestionText() { return questionText; }

    public String getScoreA() { return scoreA; }
    public String getScoreB() { return scoreB; }
    public String getScoreC() { return scoreC; }
    public String getScoreD() { return scoreD; }
    public void setscoreA(String answerscore) { this.scoreA = answerscore; }
    public void setscoreB(String answerscore) { this.scoreB = answerscore; }
    public void setscoreC(String answerscore) { this.scoreC = answerscore; }
    public void setscoreD(String answerscore) { this.scoreD = answerscore; }
    //public String getCorrectAnswer() { return correctAnswer; }

/*
    public boolean isCorrectAnswer(String selectedAnswer)
    {
        return (selectedAnswer.equals(correctAnswer));
    }

    */

    @Override
    public String toString(){
        return questionText;
    }

}

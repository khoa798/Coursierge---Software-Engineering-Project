package com.jhacks.vrclassroom;

public class QuestionScores {

    //private int questionText;
    private int scoreA;
    private int scoreB;
    private int scoreC;
    private int scoreD;

    public QuestionScores()
    {

    }


    public QuestionScores(int scoreA, int scoreB, int scoreC, int scoreD)
    {
        this.scoreA = 0;
        this.scoreB = 0;
        this.scoreC = 0;
        this.scoreD = 0;

    }

   // public int getQuestionText() { return questionText; }

    public int getScoreA() { return scoreA; }
    public int getScoreB() { return scoreB; }
    public int getScoreC() { return scoreC; }
    public int getScoreD() { return scoreD; }
    //public void setQuestion(int questionChoice) { this.questionText = questionChoice; }
    public void setScoreA(int answerScore) { this.scoreA = answerScore; }
    public void setScoreB(int answerScore) { this.scoreB = answerScore; }
    public void setScoreC(int answerScore) { this.scoreC = answerScore; }
    public void setScoreD(int answerScore) { this.scoreD = answerScore; }




}

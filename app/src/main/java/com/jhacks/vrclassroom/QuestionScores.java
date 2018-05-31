package com.jhacks.vrclassroom;

public class QuestionScores {

    private String questionText;
    private String scoreA;
    private String scoreB;
    private String scoreC;
    private String scoreD;

    public QuestionScores()
    {
        // Empty constructor requirement by Firebase deserializer
    }

    public QuestionScores(String questionText,String scoreA, String scoreB, String scoreC, String scoreD)
    {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.scoreC = scoreC;
        this.scoreD = scoreD;

    }

    public String getQuestionText() { return questionText; }

    public String getScoreA() { return scoreA; }
    public String getScoreB() { return scoreB; }
    public String getScoreC() { return scoreC; }
    public String getScoreD() { return scoreD; }
    public void setQuestion(String questionChoice) { this.questionText = questionChoice; }
    public void setscoreA(String answerScore) { this.scoreA = answerScore; }
    public void setscoreB(String answerScore) { this.scoreB = answerScore; }
    public void setscoreC(String answerScore) { this.scoreC = answerScore; }
    public void setscoreD(String answerScore) { this.scoreD = answerScore; }


    @Override
    public String toString(){
        return questionText;
    }

}

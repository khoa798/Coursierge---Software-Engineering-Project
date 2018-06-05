package com.jhacks.vrclassroom;
// Base code provided by  https://www.youtube.com/watch?v=rRpl7CexurE



public class Question {
    //private String questionText;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private String flagChoicesSelected;
    public Question()
    {
        // Empty constructor requirement by Firebase deserializer
    }

    public Question(String choiceA, String choiceB, String choiceC, String choiceD, String flagChoicesSelected)
    {
       // this.questionText = questionText;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.flagChoicesSelected = flagChoicesSelected;

    }

    //public String getQuestionText() { return questionText; }

    public String getChoiceA() { return choiceA; }
    public String getChoiceB() { return choiceB; }
    public String getChoiceC() { return choiceC; }
    public String getChoiceD() { return choiceD; }
    public String getFlag() { return flagChoicesSelected; }
    public void setChoiceA(String answerChoice) { this.choiceA = answerChoice; }
    public void setChoiceB(String answerChoice) { this.choiceB = answerChoice; }
    public void setChoiceC(String answerChoice) { this.choiceC = answerChoice; }
    public void setChoiceD(String answerChoice) { this.choiceD = answerChoice; }
    public void setFlag(String flag) { this.flagChoicesSelected = flag; }


}
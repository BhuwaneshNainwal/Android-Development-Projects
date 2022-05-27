package com.example.quiz;

/*
    This class will maintain our question attributes
*/

public class Model {

    /*
        Variables to store question, all the four optins and the correct answer

     */
    private String question, aOption, bOption, cOption, dOption, correctAnswer;

    /*

        Parameterized constructor is created

     */
    public Model(String question, String aOption, String bOption, String cOption, String dOption, String correctAnswer) {
        this.question = question;
        this.aOption = aOption;
        this.bOption = bOption;
        this.cOption = cOption;
        this.dOption = dOption;
        this.correctAnswer = correctAnswer;
    }

    /*
        Here, I have defined getter and setter methods

     */
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getaOption() {
        return aOption;
    }

    public void setaOption(String aOption) {
        this.aOption = aOption;
    }

    public String getbOption() {
        return bOption;
    }

    public void setbOption(String bOption) {
        this.bOption = bOption;
    }

    public String getcOption() {
        return cOption;
    }

    public void setcOption(String cOption) {
        this.cOption = cOption;
    }

    public String getdOption() {
        return dOption;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setdOption(String dOption) {
        this.dOption = dOption;


    }
}

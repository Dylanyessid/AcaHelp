package com.example.acahelp.models;

public class Answer {
    String _id;

    public Answer(String question, String user, String answer){
        this.question = question;
        this.user = user;
        this.answer = answer;
    }

    public String get_id() {
        return _id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    String question;
    String user;
    String answer;
    int score;
}

package com.example.acahelp.models;

public class Answer {
    String _id;
    int negatives;
    int positives;
    int scoreForOrder;
    public Answer(String question, String user, String answer){
        this.question = question;
        this.user = user;
        this.answer = answer;
        this.negatives = 0;
        this.positives = 0;
    }

    public Answer(int negatives, int positives){
        this.negatives = negatives;
        this.positives = positives;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getNegatives() {
        return negatives;
    }

    public void setNegatives(int negatives) {
        this.negatives = negatives;
    }

    public int getPositives() {
        return positives;
    }

    public void setPositives(int positives) {
        this.positives = positives;
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

    public int getScoreForOrder() {
        return scoreForOrder;
    }

    public void setScoreForOrder(int scoreForOrder) {
        this.scoreForOrder = scoreForOrder;
    }

    String question, createdAt;
    String user;
    String answer;

}

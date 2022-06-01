package com.example.acahelp.models;

public class Score {

    private String _id, user, answer;
    private boolean isPositive;

    public Score(String user, boolean isPositive) {
        this.user = user;
        this.isPositive = isPositive;
    }

    public Score(String user, boolean isPositive, String answer) {
        this.user = user;
        this.isPositive = isPositive;
        this.answer = answer;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }
}

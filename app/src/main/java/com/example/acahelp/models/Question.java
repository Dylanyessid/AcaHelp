package com.example.acahelp.models;

public class Question {
    private String _id;



    private String user;

    private String title;
    private String description;

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Question(String title, String description, String user) {
        this.user = user;
        this.title = title;
        this.description = description;
    }
}

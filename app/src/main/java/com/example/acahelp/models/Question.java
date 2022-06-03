package com.example.acahelp.models;

import java.util.ArrayList;

public class Question {
    private String _id,user;
    private String fileUri;
    private String title;
    private String description;
    private String area;
    private boolean isPrivate;
    private String designedUser;

    public String getArea() {
        return area;
    }

    public String getDesignedUser() {
        return designedUser;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

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

    public Question(String title, String description, String user, boolean isPrivate) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.isPrivate = isPrivate;
    }

}

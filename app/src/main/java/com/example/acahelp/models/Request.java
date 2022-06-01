package com.example.acahelp.models;

import java.util.ArrayList;

public class Request {
    private String _id, user,status;
    private ArrayList<String> areas;

    public Request(String user, ArrayList areas){
        this.user = user;
        this.areas = areas;
    }

    public String get_id() {
        return _id;
    }

    public String getUser() {
        return user;
    }


    public String getStatus() {
        return status;
    }

    public ArrayList<String> getAreas() {
        return areas;
    }

}

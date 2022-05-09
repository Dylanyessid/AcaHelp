package com.example.acahelp.models;

public class User {
     private String _id, name, surname, email, password;
     private boolean isQualified;

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User(String name, String surname, String  email, String password){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isQualified() {
        return isQualified;
    }

    public void setQualified(boolean qualified) {
        isQualified = qualified;
    }
}

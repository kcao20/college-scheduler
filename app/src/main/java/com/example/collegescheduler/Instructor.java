package com.example.collegescheduler;

public class Instructor {

    private String name;
    private String mail;

    public Instructor(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail() {
        this.mail = mail;
    }

}

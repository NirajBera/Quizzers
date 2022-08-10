package com.example.quizzers;

public class User {
    private String email;
    private String name;
    private String pass;


    private String profile;
    private String referCode;



    private long coins = 25;

    public User() {
    }

    public User( String email,String name, String pass, String referCode) {

        this.email = email;
        this.name = name;
        this.pass = pass;
        this.referCode = referCode;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }


    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }
    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}

package com.example.ashu.housepredictor;

/**
 * Created by ashu on 6/12/17.
 */

public class UserClass {
    public String username;
    public String email;
    public String country;
    public String state;
    public String city ;
    public String level;
    public String score;

    public UserClass()
    {

    }

    public UserClass(String username, String email, String country, String state, String city, String level, String score)
    {
        this.username = username;
        this.email = email;
        this.country = country;
        this.state = state;
        this.city = city;
        this.level = level;
        this.score = score;

    }
}

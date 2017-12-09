package com.example.theroglu.owlme;

import android.media.Image;

import java.util.Date;
import java.util.UUID;

/**
 * Created by theroglu on 25.11.17.
 */

//this class represents a user class containing all the variables for displaying the required info on the screen.

public class User {




    private String username;
    private Date birthday;
    private Image profilePicture;
    private String bio;
    private String languages;
    private int languageLevel;
    private String country;
    private String age;

    public User(String username) {


        setUsername(username);


    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public int getLanguageLevel() {
        return languageLevel;
    }

    public void setLanguageLevel(int languageLevel) {
        this.languageLevel = languageLevel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }








}

package com.example.theroglu.owlme;

import java.util.Map;

/**
 * Created by theroglu on 25.11.17.
 */

//this class represents a user class containing all the variables for displaying the required info on the screen.

public class User {


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    public String aboutme;
    public int    age;
    public String country;
    public String gender;
    public Map<String,Object> languages;








    public User(){}


        public User(String aboutme, int age, String country, String gender, Map<String,Object> languages) {
        this.aboutme = aboutme;
        this.age = age;
        this.country = country;
        this.gender = gender;
        this.languages = languages;

    }


    public int getLanguages() {




        return languages.size();
    }

    public void findLanguages(){
        for (Map.Entry<String, Object> entry : languages.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
    }

    public void setLanguages(Map<String, Object> languages) {
        this.languages = languages;
    }


    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }








}

package com.example.augus.unbdates;

public class User {
    public User(){}

    private String age;
    private String bio;
    private String campus;
    private String gender;
    private String interestedIn;
    private String name;
    private String profileImageUrl;

    public String getAge() {
        return age;
    }

    public void setAge(String a) {
        this.age = a;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String s) {
        this.bio = s;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String s) {
        this.campus = s;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String s) {
        this.gender = s;
    }

    public String getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(String s) {
        this.interestedIn = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String s) {
        profileImageUrl = s;
    }
}

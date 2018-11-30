package com.example.augus.unbdates;

import android.net.Uri;

public class User {
    public User(){}

    private int Age;
    private String Bio;
    private String Campus;
    private String Gender;
    private String InterestedIn;
    private String Name;
    private Uri profileImageUrl;

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getInterestedIn() {
        return InterestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        InterestedIn = interestedIn;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Uri getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(Uri profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}

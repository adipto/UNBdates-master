package com.example.augus.unbdates;

public class User {
    public User(){}

    private String Age;
    private String Bio;
    private String Campus;
    private String Gender;
    private String InterestedIn;
    private String Name;
    private String profileImageUrl;

    public String getAge() {
        return Age;
    }

    public void setAge(String a) {
        this.Age = a;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String s) {
        this.Bio = s;
    }

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String s) {
        this.Campus = s;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String s) {
        this.Gender = s;
    }

    public String getInterestedIn() {
        return InterestedIn;
    }

    public void setInterestedIn(String s) {
        this.InterestedIn = s;
    }

    public String getName() {
        return Name;
    }

    public void setName(String s) {
        this.Name = s;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String s) {
        profileImageUrl = s;
    }
}

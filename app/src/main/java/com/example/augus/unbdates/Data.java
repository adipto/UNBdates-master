package com.example.augus.unbdates;

public class Data
{
    private String description;

    private String imagePath;
    private String name;
    private String userid;
    String profileImageUrl;

    public Data(String imagePath, String description,String name) {
        this.imagePath = imagePath;
        this.description = description;
        this.name = name;
       // this.userid = userid;
    }

    public Data(String imagePath, String description,String name,String userid) {
        this.imagePath = imagePath;
        this.description = description;
        this.name = name;
        this.userid = userid;
    }
    public Data(String imagePath, String description,String name,String userid,String profileImageUrl) {
        this.imagePath = imagePath;
        this.description = description;
        this.name = name;
        this.userid = userid;
        this.profileImageUrl = profileImageUrl;
    }

    public String getDescription() {
        return description;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String getName() { return name;}
    public String getUserid() { return userid;}
    public String getProfileImageUrl() { return profileImageUrl;}


}

package com.example.augus.unbdates;

public class Data
{
    private String description;

    private String imagePath;
    private String name;
    private String userid;

    public Data(String imagePath, String description,String name,String userid) {
        this.imagePath = imagePath;
        this.description = description;
        this.name = name;
        this.userid = userid;
    }

    public String getDescription() {
        return description;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String getName() {
        return name;
    }
    public String getUserid() {
        return userid;
    }


}

package com.example.augus.unbdates;

public class Cards
{
    //Declaring variables
    private String UserId,Name;

    public Cards(String userId,String name)
    {
        this.UserId = userId;
        this.Name = name;
    }
    public String getUserId()
    {
        return UserId;
    }

    public void setUserId(String userId)
    {
        this.UserId = userId;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        this.Name = name;
    }
}

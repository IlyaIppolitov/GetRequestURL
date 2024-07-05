package com.example.getrequesturl;

import com.google.gson.annotations.SerializedName;

public class AgeResponse {
    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    @SerializedName("count")
    private int count;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getCount() {
        return count;
    }
}
package com.fx.folx;

public class User {

    private String userName;
    private Integer userAge;

    public User(String name, Integer age){
        userName = name;
        userAge = age;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public String getUserName() {
        return userName;
    }
}

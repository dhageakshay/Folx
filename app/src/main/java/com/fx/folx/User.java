package com.fx.folx;

import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {

    private String name,nickName, email, password, gender,profession,company,university,story,phone;
    private Integer userAge;
    private Date dob;
    private ArrayList sexualOrientation;
    private Integer maxDistance,minAgeRange,maxAgeRange;

    // viewLastSeen gives the choice of the user when was he/she last active. true implies public;  false implies private
    private Boolean viewLastSeen;

    public User(String name, Integer age){
        this.name = name;
        userAge = age;
    }

    public User(String name, String email,String password, Date dob, String phone){
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.phone = phone;
        viewLastSeen = true;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public String getUserName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompany() {
        return company;
    }

    public String getGender() {
        return gender;
    }

    public String getProfession() {
        return profession;
    }

    public String getStory() {
        return story;
    }

    public String getUniversity() {
        return university;
    }

    public ArrayList getSexualOrientation() {
        return sexualOrientation;
    }

    public Integer getMinAgeRange() {
        return minAgeRange;
    }

    public Integer getMaxAgeRange() {
        return maxAgeRange;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public Boolean getViewLastSeen() {
        return viewLastSeen;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public void setUniversity(String university) {
        this.university = university;
    }


    public void setUserName(String userName) {
        this.name = userName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSexualOrientation(ArrayList sexualOrientation) {
        this.sexualOrientation = sexualOrientation;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setMaxAgeRange(Integer maxAgeRange) {
        this.maxAgeRange = maxAgeRange;
    }

    public void setMinAgeRange(Integer minAgeRange) {
        this.minAgeRange = minAgeRange;
    }

    public void setViewLastSeen(Boolean viewLastSeen) {
        this.viewLastSeen = viewLastSeen;
    }
}

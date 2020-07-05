package com.fx.folx;


import com.fx.folx.api.Restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {

    private String name,nickName, email, gender,profession,company,university,story,phone;
    private Date dob;
    private ArrayList sexualOrientation;
    private Integer maxDistance,minAgeRange,maxAgeRange;
    private ArrayList<String> imageList;
    private ArrayList<Restaurant> favRestaurantList;

    // viewLastSeen gives the choice of the user when was he/she last active. true implies public;  false implies private
    private Boolean viewLastSeen;

    public User(String name, Integer age){
        this.name = name;
    }
    public User(){}

    public User(String name, String email, Date dob, String phone){
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        viewLastSeen = true;
    }

    public User(String name, String nickName, String email, String gender, String profession, String company, String university, String phone,
                Date dob, ArrayList sexualOrientation, Integer maxDistance, Integer minAgeRange, Integer maxAgeRange, ArrayList<String> imageList,
                ArrayList<Restaurant> favRestaurantList, Boolean viewLastSeen) {

        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
        this.company = company;
        this.university = university;
        this.phone = phone;
        this.dob = dob;
        this.sexualOrientation = sexualOrientation;
        this.maxDistance = maxDistance;
        this.minAgeRange = minAgeRange;
        this.maxAgeRange = maxAgeRange;
        this.imageList = imageList;
        this.favRestaurantList = favRestaurantList;
        this.viewLastSeen = viewLastSeen;
    }

    public User(String name, String nickName, String email, String gender, String profession, String company, String university, String story,
                String phone, Date dob, ArrayList sexualOrientation, Integer maxDistance, Integer minAgeRange, Integer maxAgeRange,
                ArrayList<String> imageList, ArrayList<Restaurant> favRestaurantList, Boolean viewLastSeen) {

        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
        this.company = company;
        this.university = university;
        this.story = story;
        this.phone = phone;
        this.dob = dob;
        this.sexualOrientation = sexualOrientation;
        this.maxDistance = maxDistance;
        this.minAgeRange = minAgeRange;
        this.maxAgeRange = maxAgeRange;
        this.imageList = imageList;
        this.favRestaurantList = favRestaurantList;
        this.viewLastSeen = viewLastSeen;
    }

    public ArrayList<Restaurant> getFavRestaurantList() {
        return favRestaurantList;
    }

    public void setFavRestaurantList(ArrayList<Restaurant> favRestaurantList) {
        this.favRestaurantList = favRestaurantList;
    }



    public String getName() {
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

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public void setUniversity(String university) {
        this.university = university;
    }


    public void setName(String userName) {
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

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public int calcUserAge(){
        return calculateAge(dob.getTime());
    }

    private int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }
}

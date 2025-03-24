package com.progress.smartdiet;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Entity(tableName = "user_profiles")
public class UserProfile {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String birthday;
    private int height;
    private float weight;
    private String scope;
    private float activityLevel;
    private float index;
    private int recommendedValueOfCalories;

    private int age;
    private String gender;
    private String status;

    public UserProfile(String name, String birthday, int height, float weight, String scope,
                       float activityLevel, float index, int recommendedValueOfCalories) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.scope = scope;
        this.activityLevel = activityLevel;
        this.index = index;
        this.recommendedValueOfCalories = recommendedValueOfCalories;
    }

    public int calculateAge() {
        if (birthday == null || birthday.isEmpty()) {
            return 0;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(birthday, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public String getScope() {
        return scope;
    }

    public float getActivityLevel() {
        return activityLevel;
    }

    public float getIndex() {
        return index;
    }

    public int getRecommendedValueOfCalories() {
        return recommendedValueOfCalories;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {return status;}

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setActivityLevel(float activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setIndex(float index) {
        this.index = index;
    }

    public void setRecommendedValueOfCalories(int recommendedValueOfCalories) {
        this.recommendedValueOfCalories = recommendedValueOfCalories;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStatus(String status) {this.status = status;}
}

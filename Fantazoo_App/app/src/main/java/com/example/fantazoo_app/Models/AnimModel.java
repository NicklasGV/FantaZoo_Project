package com.example.fantazoo_app.Models;

public class AnimModel {
    public int id;
    public String name;
    public int age;
    public Gender gender;
    public CageModel cage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public CageModel getCage() {
        return cage;
    }

    public void setCage(CageModel cage) {
        this.cage = cage;
    }
}

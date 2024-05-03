package com.example.fantazoo_app.Models;

import java.util.ArrayList;

public class WeapModel {
    public int id;
    public String name;
    public String damage;
    public ArrayList<ZKModel> zookeepers;

    public WeapModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public ArrayList<ZKModel> getZookeepers() {
        return zookeepers;
    }

    public void setZookeepers(ArrayList<ZKModel> zookeepers) {
        this.zookeepers = zookeepers;
    }
}

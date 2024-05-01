package com.example.fantazoo_app.Models;

import java.util.ArrayList;

public class CageModel {
    public int id;
    public String name;
    public ArrayList<ZKModel> zookeepers;
    public ArrayList<AnimModel> animals;

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

    public ArrayList<ZKModel> getZookeepers() {
        return zookeepers;
    }

    public void setZookeepers(ArrayList<ZKModel> zookeepers) {
        this.zookeepers = zookeepers;
    }

    public ArrayList<AnimModel> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<AnimModel> animals) {
        this.animals = animals;
    }
}

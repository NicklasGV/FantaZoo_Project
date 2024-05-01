package com.example.fantazoo_app.Models;

public class ZKModel {
    public int id;
    public String name;
    public WeapModel weapon;
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

    public WeapModel getWeapon() {
        return weapon;
    }

    public void setWeapon(WeapModel weapon) {
        this.weapon = weapon;
    }

    public CageModel getCage() {
        return cage;
    }

    public void setCage(CageModel cage) {
        this.cage = cage;
    }
}

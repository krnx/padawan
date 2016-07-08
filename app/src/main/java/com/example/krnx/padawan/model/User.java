package com.example.krnx.padawan.model;

/**
 * Created by arnau on 05/07/16.
 */
public class User {

    private int icon;
    private String name;
    private String phone;

    public User(int icon, String name, String phone){
        this.icon = icon;
        this.name = name;
        this.phone = phone;

    }
    public User(){

    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

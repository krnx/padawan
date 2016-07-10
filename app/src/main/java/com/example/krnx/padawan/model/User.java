package com.example.krnx.padawan.model;

/**
 * Created by arnau on 05/07/16.
 */
public class User {

    private int icon;
    private String name;
    private String surname;
    private String phone;
    private String email;

    public User(int icon, String name, String surname, String email) {
        this.icon = icon;
        this.name = name;
        this.name = surname;
        this.phone = email;

    }

    public User() {

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

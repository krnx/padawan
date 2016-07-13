package com.example.krnx.padawan.model;

/**
 * Created by arnau on 05/07/16.
 */
public class Ranking {

    private String email;
    private Integer points;

    public Ranking(String email, Integer points) {
        this.email = email;
        this.points = points;
    }

    public Ranking() {
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

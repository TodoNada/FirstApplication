package com.example.holynskyi.firstapplication.basic;

/**
 * Created by holynskyi on 09.08.17.
 */

public class BasicUser {
    protected long id;
    protected String name;
    protected String password;
    protected long cars;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCars() {
        return cars;
    }

    public void setCars(long cars) {
        this.cars = cars;
    }
}

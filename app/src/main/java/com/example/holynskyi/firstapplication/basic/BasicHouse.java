package com.example.holynskyi.firstapplication.basic;

/**
 * Created by holynskyi on 15.08.17.
 */

public class BasicHouse {
    protected long id;
    protected String city;
    protected String adress;
    protected String other;
    protected long data;
    protected long userId;


    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

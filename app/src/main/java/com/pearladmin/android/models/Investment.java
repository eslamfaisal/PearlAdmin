package com.pearladmin.android.models;

public class Investment {
    private String user_id;
    private String id;
    private int shares_count;
    private int shares_price;

    public Investment() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getShares_count() {
        return shares_count;
    }

    public void setShares_count(int shares_count) {
        this.shares_count = shares_count;
    }

    public int getShares_price() {
        return shares_price;
    }

    public void setShares_price(int shares_price) {
        this.shares_price = shares_price;
    }
}

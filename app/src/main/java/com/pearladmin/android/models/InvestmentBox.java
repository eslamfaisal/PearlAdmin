package com.pearladmin.android.models;

public class InvestmentBox {

    private String id;
    private String name;
    private String description;
    private String total_price;
    private String share_price;
    private int total_shares_count;
    private int sold_shares_count;

    public InvestmentBox() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotal_shares_count() {
        return total_shares_count;
    }

    public void setTotal_shares_count(int total_shares_count) {
        this.total_shares_count = total_shares_count;
    }

    public int getSold_shares_count() {
        return sold_shares_count;
    }

    public void setSold_shares_count(int sold_shares_count) {
        this.sold_shares_count = sold_shares_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getShare_price() {
        return share_price;
    }

    public void setShare_price(String share_price) {
        this.share_price = share_price;
    }
}

package com.example.sociomantic_darren.webviewapp.resources;

import java.util.ArrayList;

public class Product {
    private ArrayList<String> category;
    private String identifier;
    private String brand;
    private String description;
    private String fn;
    private String price;
    private String amount;
    private String url;
    private String photo;
    private String valid; 
    private String currency;


    public void setID(String str) {
        this.identifier = str;
    }

    public void setBrand(String str) {
        this.brand = str;
    }

    public void setName(String str) {
        this.fn = str;
    }

    public void setCategories(ArrayList<String> arrayList) {
        this.category = arrayList;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setImageUrl(String str) {
        this.photo = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public void setAmount(String str) {
        this.amount = str;
    }

    public void setCurrency(String str) {
        this.amount = str;
    }

    public void setValidity(String str) {
        this.valid = str;
    }

    public String getID() {
        return this.identifier;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getName() {
        return this.fn;
    }

    public ArrayList<String> getCategory() {
        return this.category;
    }

    public String getImageUrl() {
        return this.photo;
    }

    public String getProductUrl() {
        return this.url;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPrice() {
        return this.price;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getValidity() {
        return this.valid;
    }

}

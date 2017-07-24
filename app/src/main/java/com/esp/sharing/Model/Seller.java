package com.esp.sharing.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Seller {

    private int id;
    private String name;
    private String phone;
    private String location;
    private String avatarUrl;
    private String description;
    private ArrayList<Product> productList;

    public Seller() {
        this(0, "", "", "", "", "");
    }

    public Seller(String json) {
        try {
            JSONObject root = new JSONObject("");
        } catch (JSONException e) {
        }
    }

    public Seller(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.name = object.getString("name");
            this.phone = object.getString("phone");
            this.location = object.getString("location");
            this.avatarUrl = object.getString("thumbnail");
            this.description = object.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Seller(int id, String name, String phone, String location, String avatarUrl, String description) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.avatarUrl = avatarUrl;
        this.description = description;
        this.productList = new ArrayList<>();
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @Override
    public boolean equals(Object obj) {
        final Seller seller = (Seller) obj;
        return  seller.getId() == this.getId();
    }
}

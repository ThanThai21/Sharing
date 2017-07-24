package com.esp.sharing.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Product {

    private int id;
    private String name;
    private int count;
    private String[] imageUrls;

    public Product() {
    }

    public Product(String json) {
        try {
            JSONObject root = new JSONObject(json);
            //parse json

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Product(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }
}

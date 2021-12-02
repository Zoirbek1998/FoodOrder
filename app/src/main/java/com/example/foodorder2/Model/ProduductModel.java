package com.example.foodorder2.Model;

import android.widget.ProgressBar;

public class ProduductModel {

    String id;
    String cat_id;
    String name;
    String details;
    String sum;
    String imageUri;

    public ProduductModel(){

    }

    public ProduductModel(String id, String cat_id, String name, String details, String sum, String imageUri) {
        this.id = id;
        this.cat_id = cat_id;
        this.name = name;
        this.details = details;
        this.sum = sum;
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

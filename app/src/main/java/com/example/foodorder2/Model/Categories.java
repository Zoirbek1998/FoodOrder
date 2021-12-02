package com.example.foodorder2.Model;

import com.squareup.picasso.RequestCreator;

public class Categories {
    public String id;
    public String imageUri;
    public String name;
    public String postid;
    public String publisherid;

    public Categories(){

    }

    public Categories(String id, String imageUri, String name, String postid, String publisherid) {
        this.id = id;
        this.imageUri = imageUri;
        this.name = name;
        this.postid = postid;
        this.publisherid = publisherid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPublisherid() {
        return publisherid;
    }

    public void setPublisherid(String publisherid) {
        this.publisherid = publisherid;
    }
}

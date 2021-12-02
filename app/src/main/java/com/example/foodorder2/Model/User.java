package com.example.foodorder2.Model;

public class User {

    String name;
    String surname;
    String phone;
    String imageUri;

    public User() {
    }

    public User(String name, String surname, String phone, String imageUri) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}

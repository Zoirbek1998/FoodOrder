package com.example.foodorder2.Model;

public class Comment {
    String comment;
    String data;
    String time;
    String phone;

    public Comment() {}

    public Comment(String comment, String data, String time, String phone) {
        this.comment = comment;
        this.data = data;
        this.time = time;
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

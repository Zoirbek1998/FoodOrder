package com.example.foodorder2.Model;

public class Orders {
    String product_id;
    int count;


    public Orders(){

    }

    public Orders(String product_id, int count) {
        this.product_id = product_id;
        this.count = count;

    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}

package com.example.tanuj.ilovezappos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanuj on 2/6/2017.
 */

public class User implements Serializable {
    String email,fullname,userKey;
    int cartCount;
    ArrayList<Product> cartItems;

    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

    public ArrayList<Product> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<Product> cartItems) {
        this.cartItems = cartItems;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public User() {
    }

    public User(String email, String fullname, String userKey) {
        this.email = email;
        this.fullname = fullname;
        this.userKey = userKey;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", userKey='" + userKey + '\'' +
                '}';
    }
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("fullname", fullname);
        result.put("email", email);
        result.put("userKey", userKey);
        return result;
    }


}

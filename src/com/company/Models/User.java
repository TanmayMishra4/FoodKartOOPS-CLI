package com.company.Models;

import com.company.Constants.Gender;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int userId;
    private String name;
    private long phone;
    private Long pincode;
    private Gender gender;
    List<Restaurant> serviceableRestaurants;
    List<Order> orderHistory;

    public List<Restaurant> getServiceableRestaurants() {
        return serviceableRestaurants;
    }

    public void setServiceableRestaurants(List<Restaurant> serviceableRestaurants) {
        this.serviceableRestaurants = serviceableRestaurants;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public User() {
        serviceableRestaurants = new ArrayList<>();
        orderHistory = new ArrayList<>();
    }

    public User(int id, Long phone, String name, Long pincode, Gender gender) {
        this.userId = id;
        this.name = name;
        this.phone = phone;
        this.pincode = pincode;
        this.gender = gender;
        serviceableRestaurants = new ArrayList<>();
        orderHistory = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

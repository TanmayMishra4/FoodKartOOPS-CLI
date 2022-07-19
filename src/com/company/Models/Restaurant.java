package com.company.Models;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private int restaurantId;
    private String name;
    private String item;
    private double price;
    private int quantity;
    private List<Review> reviewList;
    private double overallRating;
    private List<Long> serviceablePincode;

    public Restaurant(){
        reviewList = new ArrayList<>();
        serviceablePincode = new ArrayList<>();
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public List<Long> getServiceablePincode() {
        return serviceablePincode;
    }

    public void setServiceablePincode(List<Long> serviceablePincode) {
        this.serviceablePincode = serviceablePincode;
    }
}

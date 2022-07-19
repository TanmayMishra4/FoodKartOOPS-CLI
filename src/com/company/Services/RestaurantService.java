package com.company.Services;

import com.company.DAO.UserDAO;
import com.company.Models.Restaurant;
import com.company.Models.Review;

import java.util.List;

public class RestaurantService {
    private static RestaurantService instance = null;
    private RestaurantService(){

    }

    public static RestaurantService getInstance(){
        if(instance == null){
            instance = new RestaurantService();
        }
        return instance;
    }

    UserDAO userDAO = UserDAO.getInstance();

    public Restaurant registerRestaurant(String name, String pinCodes, String item, int price, int quantity){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Please input valid Restaurant name");
        }
        else if(pinCodes == null || pinCodes.isEmpty()){
            throw new IllegalArgumentException("Please input valid pinCodes list in String");
        }
        else if(item == null || item.isEmpty()){
            throw new IllegalArgumentException("Please input valid item name");
        }
        else if(price <= 0){
            throw new IllegalArgumentException("Please input valid item price");
        }
        return userDAO.registerRestaurant(name, pinCodes, item, price, quantity);
    }

    public Restaurant updateQuantity(String name, int quantity){
        if(quantity < 0){
            throw new IllegalArgumentException("Please enter valid quantity");
        }
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Please enter valid name");
        }
        return userDAO.updateQuantity(name, quantity);
    }

    public Review rateRestaurant(String name, int rating, String comment){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Please input valid Restaurant name");
        }
        else if(rating < 1 || rating > 5){
            throw new IllegalArgumentException("Please enter rating from 1 and 5");
        }
        else if(comment == null || comment.isEmpty()){
            throw new IllegalArgumentException("Please enter a valid comment");
        }
        return userDAO.reviewRestaurant(name, rating, comment);
    }

    public List<Restaurant> showRestaurant(String sortBy){
        if(sortBy.equalsIgnoreCase("Rating") || sortBy.equalsIgnoreCase("price")){
            return userDAO.showRestaurant(sortBy);
        }
        throw new IllegalArgumentException("Please enter valid field to sortBy");
    }
}

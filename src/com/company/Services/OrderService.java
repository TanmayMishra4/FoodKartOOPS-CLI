package com.company.Services;

import com.company.DAO.UserDAO;
import com.company.Models.Order;

import java.util.List;

public class OrderService {
    private static OrderService instance = null;
    private OrderService(){

    }

    UserDAO userDAO = UserDAO.getInstance();

    public static OrderService getInstance(){
        if(instance == null){
            instance = new OrderService();
        }
        return instance;
    }

    public Order placeOrder(String name, int quantity){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Please enter valid name");
        }
        else if(quantity < 1){
            throw new IllegalArgumentException("Please enter valid quantity to place order");
        }
        return userDAO.placeOrder(name, quantity);
    }

    public List<Order> listOrders(){
        return userDAO.listOrders();
    }

}

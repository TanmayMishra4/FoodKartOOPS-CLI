package com.company.Utils;

import com.company.Constants.Gender;
import com.company.Models.Order;
import com.company.Models.Restaurant;
import com.company.Models.Review;
import com.company.Models.User;
import com.company.Services.OrderService;
import com.company.Services.RestaurantService;
import com.company.Services.UserService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Execeution started");
        UserService userService = UserService.getInstance();
        RestaurantService restaurantService = RestaurantService.getInstance();
        OrderService orderService = OrderService.getInstance();

        User user1 = userService.registerUser(989898981L, "Harry Windsor", 1L, Gender.MALE);
        User user2 = userService.registerUser(989898982L, "William Windsor", 2L, Gender.MALE);
        User user3 = userService.registerUser(989898983L, "Elizabeth Windsor", 3L, Gender.FEMALE);

        User login = userService.login(user1.getPhone());
        Restaurant r1 = restaurantService.registerRestaurant("FoodCourt1", "1,2,4,5", "Pizza", 45, 10);
        Restaurant r2 = restaurantService.registerRestaurant("FoodCourt2", "1,4,5", "Pasta", 35, 8);
        r1 = restaurantService.updateQuantity(r1.getName(), 5);
        login = userService.login(user2.getPhone());
        Restaurant r3 = restaurantService.registerRestaurant("FoodCourt3", "2,5,3", "Lasagna", 80, 7);

        login = userService.login(user3.getPhone());

        Restaurant r4 = restaurantService.registerRestaurant("FoodCourt4", "3,4", "Risotto", 60, 9);

        login = userService.login(user1.getPhone());
        List<Restaurant> restList = restaurantService.showRestaurant("Price");
        System.out.println();
        for(Restaurant res : restList){
            System.out.println(res.getName()+"\tRating----------->"+res.getOverallRating());
        }
        System.out.println();
        Order order = orderService.placeOrder("FoodCourt1", 5);
        Review review = restaurantService.rateRestaurant("FoodCourt1", 4, "Nice");
        List<Order> orderList = orderService.listOrders();
        System.out.println();
        for(Order or : orderList){
            System.out.println(or.getName()+"--"+"with restaurant ID: "+or.getRestaurantId()+" with UserID: "+or.getUserId()+"__Quantity= "+or.getQuantity()+" and price = "+or.getPrice());
        }
        System.out.println();
        restList = restaurantService.showRestaurant("Rating");
        System.out.println();
        for(Restaurant res : restList){
            System.out.println(res.getName()+"\tRating----------->"+res.getOverallRating());
        }
        System.out.println();
        System.out.println("Execution ended");

    }
}

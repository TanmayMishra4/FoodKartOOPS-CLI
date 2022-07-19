package com.company.DAO;

import com.company.Constants.Gender;
import com.company.Models.Order;
import com.company.Models.Restaurant;
import com.company.Models.Review;
import com.company.Models.User;
import com.company.Services.RestaurantService;
import com.company.Utils.IDGenerator;

import java.util.*;

public class UserDAO {
    private static UserDAO userDAO;

    private UserDAO(){

    }

    private HashMap<Integer, User> userMap = new HashMap<>();    // mapping of userID with USer object
    private HashMap<Long, Integer> phoneNumberMap = new HashMap<>();   // mapping of phone number with user ID
    private HashMap<String, Restaurant> restaurantMap = new HashMap<>();  // mapping of restaurant name with its Object
    private User loggedInUser = null;

    public static UserDAO getInstance() {
        if(userDAO == null)
            userDAO = new UserDAO();
        return userDAO;
    }

    public User registerUser(Long phone, String name, Long pincode, Gender gender){
        if(phoneNumberMap.containsKey(phone)) {
            User user = userMap.get(phoneNumberMap.get(phone));
            System.out.println("User already exists with phone number: " + phone + " and with user ID: " + user.getUserId());
            return user;
        }
        User user = new User(IDGenerator.getId(), phone, name, pincode, gender);
        phoneNumberMap.put(phone, user.getUserId());
        userMap.put(user.getUserId(), user);
        System.out.println("User with name: "+name+" registered successfully, phone no. : "+phone);
        return user;
    }

    public User login(long phone){
        if(!phoneNumberMap.containsKey(phone)){
            System.out.println("Please register the user first and then login");
            return null;
        }
        loggedInUser = userMap.get(phoneNumberMap.get(phone));
        System.out.println("Successfully logged in User with id: "+loggedInUser.getUserId());
        return loggedInUser;
    }

    public Restaurant registerRestaurant(String name, String pinCodes, String item, int price, int quantity){
        if(loggedInUser == null){
            System.out.println("Please log in first ");
            return null;
        }
        if(restaurantMap.containsKey(name)){
            System.out.println("Restaurant already exists with name: "+name+" and with id: "+restaurantMap.get(name).getRestaurantId());
            return restaurantMap.get(name);
        }
        List<String> pinCodeList = Arrays.asList(pinCodes.split(","));
        List<Long> pins = new ArrayList<>();
        for(String s : pinCodeList){
            if(!s.chars().allMatch(Character::isDigit)){
                System.out.println("Invalid pincode provided\n");
                return null;
            }
            pins.add(Long.parseLong(s));
        }
        // TODO: check if builder pattern is applicable
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(IDGenerator.getId());
        restaurant.setItem(item);
        restaurant.setName(name);
        restaurant.setPrice(price);
        restaurant.setQuantity(quantity);
        restaurant.setServiceablePincode(pins);
        restaurantMap.put(name, restaurant);
        System.out.println("Successfully registered restaurant with name: "+name+" and id: "+restaurant.getRestaurantId());
        return restaurant;
    }

    public Review reviewRestaurant(String name, int rating, String comment){
        if(loggedInUser == null){
            System.out.println("Please log in first ");
            return null;
        }
        Restaurant restaurant = restaurantMap.get(name);
        if(restaurant == null){
            System.out.println("Restaurant does not exist");
            return null;
        }
        Review review = new Review();
        review.setReviewId(IDGenerator.getId());
        review.setComment(comment);
        review.setRating(rating);
        System.out.println("Successfully reviewed restaurant");
        List<Review> reviewList = restaurant.getReviewList();
        if(reviewList == null || reviewList.isEmpty()){
            restaurant.setOverallRating(Double.valueOf(rating));
        }
        else{
            int n = reviewList.size();
            double newrating = (restaurant.getOverallRating()*n + rating)/(n+1);
            restaurant.setOverallRating(newrating);
        }
        reviewList.add(review);
        return review;
    }

    public Restaurant updateQuantity(String name, int quantity){
        if(loggedInUser == null){
            System.out.println("Please log in first ");
            return null;
        }
        Restaurant restaurant = restaurantMap.get(name);
        if(restaurant == null){
            System.out.println("Restaurant does not exist");
            return null;
        }
        restaurant.setQuantity(quantity);
        return restaurant;
    }

    public List<Restaurant> showRestaurant(String sortBy){
        if(loggedInUser == null){
            System.out.println("Please log in first ");
            return null;
        }
        List<Restaurant> result = new ArrayList<>();
        Collection<Restaurant> restaurantList = restaurantMap.values();
        for(Restaurant restaurant : restaurantList){
            for(Long pincode : restaurant.getServiceablePincode()){
                if(pincode == loggedInUser.getPincode()){
                    result.add(restaurant);
                    break;
                }
            }
        }
        if(sortBy.equalsIgnoreCase("Price")){
            Collections.sort(result, new SortByPrice());
        }
        else{
            Collections.sort(result, new SortByRating());
        }
        return result;
    }

    public List<Order> listOrders(){
        if(loggedInUser == null){
            System.out.println("Please log in first ");
            return null;
        }
        System.out.println("Order History of "+loggedInUser.getName()+"\n ");
        return loggedInUser.getOrderHistory();
    }

    public Order placeOrder(String name, int quantity){
        if(loggedInUser == null){
            System.out.println("Please log in first ");
            return null;
        }
        Restaurant restaurant = restaurantMap.get(name);
        if(restaurant == null){
            System.out.println("Invalid Restaurant");
            return null;
        }
        if(quantity > restaurant.getQuantity()){
            System.out.println("Quantity should be less than quantity avbl at restayurant");
            return null;
        }
        List<Long> pinCodeList = restaurant.getServiceablePincode();
        int flag = 0;
        for(Long pincode : pinCodeList){
            if(pincode == loggedInUser.getPincode()){
                flag = 1;
                break;
            }
        }
        if(flag == 0){
            System.out.println("Restaurant is not serviceable for you");
            return null;
        }
        Order order = new Order();
        order.setOrderId(IDGenerator.getId());
        order.setUserId(loggedInUser.getUserId());
        order.setName(restaurant.getName());
        order.setPrice(restaurant.getPrice());
        order.setQuantity(quantity);
        order.setRestaurantId(restaurant.getRestaurantId());
        loggedInUser.getOrderHistory().add(order);
        System.out.println("Successfully order placed at Restaurant: "+restaurant.getName()+" with quantity: "+quantity);
        RestaurantService restaurantService = RestaurantService.getInstance();
        restaurantService.updateQuantity(name, restaurant.getQuantity()-quantity);
        return order;
    }

    class SortByRating implements Comparator<Restaurant>{

        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            if(o1 == null || o2 == null)
                return 0;
            return (int) (o2.getOverallRating() - o1.getOverallRating());
        }
    }

    class SortByPrice implements Comparator<Restaurant>{

        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            if(o1 == null || o2 == null) return 0;
            return (int) (o1.getPrice() - o2.getPrice());
        }
    }
}

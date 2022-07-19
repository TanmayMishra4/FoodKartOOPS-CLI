package com.company.Services;

import com.company.Constants.Gender;
import com.company.DAO.UserDAO;
import com.company.Models.User;

public class UserService {
    private static UserService instance = null;
    private UserService(){

    }

    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }
    UserDAO userDAO = UserDAO.getInstance();

    public User registerUser(Long phone, String name, Long pincode, Gender gender){
        if(phone == null || phone <= 0){
            throw new IllegalArgumentException("Please input valid phone number");
        }
        else if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Please input valid name");
        }
        else if(pincode == null || pincode <= 0){
            throw new IllegalArgumentException("Please input valid pincode");
        }
//        System.out.println("User with name: "+name+" and phone number: "+phone+" registered");
        return userDAO.registerUser(phone, name, pincode, gender);
    }

    public User login(long phone){
        if(phone <= 0){
            System.out.println("Please enter valid phone number to login with");
            return null;
        }
        return userDAO.login(phone);
    }
}

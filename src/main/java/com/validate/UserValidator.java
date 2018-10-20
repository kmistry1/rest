package com.validate;

import com.validate.user.Users;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class UserValidator {

    
   
    private String username;

    public String getUserName(){
        return username;
    }
    public void setName(String name){
        this.username = name;
    }

    private String checkForNullOrEmpty(String username) {
        Users validUsers = new Users();
        ArrayList<String> users = validUsers.getValidUsers();
        if(StringUtils.isBlank(username)){
            return "username cannot be null, blank or empty";
        }

        for(String user:users){

            if(user.equals(username)){
                return "username is valid";
            }
        }
        return "username is invalid";
    }

    public String getUserValidation(){
        return checkForNullOrEmpty(getUserName());
    }
}

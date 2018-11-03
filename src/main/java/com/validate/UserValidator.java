package com.validate;

import com.validate.aes.IEncryptionLoop;
import com.validate.user.Users;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class UserValidator {

    private String userName;
    private String password;

    public void setUserName(String name){
        this.userName = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }


    private String checkForNullOrEmpty(String username) {
        Users validUsers = new Users();
        ArrayList<String> users = validUsers.getValidUsers();
        if(StringUtils.isBlank(username)){
            return "username or password cannot be null, blank or empty";
        }

        for(String user:users){

            try {
                user = IEncryptionLoop.decrypt(user);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(user.equals(username)){
                if(checkForpassword(getPassword())){
                    return "username and password is valid";
                }
            }
        }
        return "username or password is invalid";
    }

    private boolean checkForpassword(String password) {
        Users validUsers = new Users();
        ArrayList<String> passwords = validUsers.getValidPasswors();
        if(StringUtils.isBlank(password)){
            return false;
        }
        for(String pass:passwords){

            try {
                pass = IEncryptionLoop.decrypt(pass);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(password.equals(password)){
                return true;
            }
        }
        return false;
    }

    public String getUserValidation(){
        return checkForNullOrEmpty(getUserName());
    }
}

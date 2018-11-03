package com.validate;

import com.validate.aes.IEncryptionLoop;
import com.validate.user.Users;
import org.apache.commons.lang3.StringUtils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                if(checkForpassword()){
                    return "username and password is valid";
                }
            }
        }
        return "username or password is invalid";
    }

    private boolean checkForpassword() {

        String password = null;

        try {
            password = getPasswordFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(StringUtils.isBlank(password)){
            return false;
        }
            try {
                password = IEncryptionLoop.decrypt(password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(password.equals(getPassword())) {
                return true;
            }
        return false;
    }

    protected String getPasswordFromDB() throws SQLException, ClassNotFoundException {

        String password=null;
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection con = DriverManager.getConnection(
                "jdbc:mysql://sunny.ccy10divxtl4.us-east-2.rds.amazonaws.com/rest", "sunny", "ajxx2020");

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users where userName="+getUserName());



        while (rs.next()) {
            password = rs.getString("password");
        }
        rs.close();
        return password;
    }

    public String getUserValidation(){
        return checkForNullOrEmpty(getUserName());
    }
}

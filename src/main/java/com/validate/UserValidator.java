package com.validate;

import com.validate.aes.IEncryptionLoop;
import com.validate.aes.SqlConnection;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserValidator {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String checkForNullOrEmpty(String username, String password) throws Exception {

        if (StringUtils.isBlank(username)) {
            return "username or password cannot be null, blank or empty";
        } else {
            if (checkForpassword(username, password)) {
                return "username and password is valid";
            }
        }


        return "username and password is invalid";


    }

    private boolean checkForpassword(String userName, String password) throws Exception {

        String passwordFromDB = getPasswordFromDB(userName);


        if (StringUtils.isBlank(password) || StringUtils.isBlank(passwordFromDB)) {
            return false;
        }

        if (password.equals(IEncryptionLoop.decrypt(passwordFromDB))) {
            return true;
        }
        return false;
    }

    protected String getPasswordFromDB(String userName) throws SQLException, ClassNotFoundException {
        String password = null;
        String databaseName = "rest";
        String dbUserName = "sunny";
        String dbPassword = "ajxx2020";

        SqlConnection sqlConnection = new SqlConnection();
        Connection con = sqlConnection.createNewConnection(databaseName, dbUserName, dbPassword);

        Statement stmt = con.createStatement();

        String query = "SELECT * FROM users WHERE userName='" + userName + "'";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            password = rs.getString("password");
        }

        rs.close();
        return password;
    }

    public String getUserValidation() throws Exception {
        return checkForNullOrEmpty(getUserName(), getPassword());
    }
}

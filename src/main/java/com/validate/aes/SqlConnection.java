package com.validate.aes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

    public Connection createNewConnection(String datebaseName, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection con = DriverManager.getConnection(
                "jdbc:mysql://sunny.ccy10divxtl4.us-east-2.rds.amazonaws.com/" + datebaseName, username, password);
        return con;
    }
}

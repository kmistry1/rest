package com.validate.aes;

import com.validate.aes.unlock.Decryption;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

    public Connection createNewConnection(String datebaseName, String username, String password) throws Exception {
        DBProperties properties = new DBProperties();
        String url = properties.getDBProperties().getProperty("db.url");
        Decryption de =new Decryption();
        java.sql.Connection con = DriverManager.getConnection(de.decrypt(url) + datebaseName, username, password);
        return con;
    }
}

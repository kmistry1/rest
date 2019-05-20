//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.validate.aes.encryption;

import com.validate.aes.unlock.Decryption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlConnection {
    public SqlConnection() {
    }

    public Connection createNewConnection(String datebaseName, String username, String password) throws Exception {
        DBProperties properties = new DBProperties();
        String url = properties.getDBProperties().getProperty("db.url");
        Decryption de = new Decryption();
        Connection con = DriverManager.getConnection(de.decrypt(url) + datebaseName, username, password);
        return con;
    }

    public String getPasswordFromDB(String userName) throws Exception {
        String password = null;
        DBProperties properties = new DBProperties();
        SqlConnection sqlConnection = new SqlConnection();
        Decryption de = new Decryption();
        Connection con = sqlConnection.createNewConnection(de.decrypt(properties.getDBProperties().getProperty("db.data")), de.decrypt(properties.getDBProperties().getProperty("db.user")), de.decrypt(properties.getDBProperties().getProperty("db.password")));
        Statement stmt = con.createStatement();
        String query = "SELECT * FROM users WHERE userName='" + userName + "'";

        ResultSet rs;
        for(rs = stmt.executeQuery(query); rs.next(); password = rs.getString("password")) {
        }

        rs.close();
        return password;
    }
}

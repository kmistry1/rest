package com.validate.aes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperties {


    public Properties getDBProperties() {
        Properties properties = new Properties();

        try {
            InputStream in = ClassLoader.getSystemResourceAsStream("dbconfig.properties");
            properties.load(in);
            in.close();

        } catch (IOException e) {
            System.out.println("Exception Occurred " + e.getMessage());
        }
        return properties;
    }
}






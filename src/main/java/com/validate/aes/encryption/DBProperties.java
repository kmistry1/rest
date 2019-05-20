//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.validate.aes.encryption;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperties {
    public DBProperties() {
    }

    public Properties getDBProperties() {
        Properties properties = new Properties();

        try {
            InputStream in = ClassLoader.getSystemResourceAsStream("dbconfig.properties");
            properties.load(in);
            in.close();
        } catch (IOException var3) {
            System.out.println("Exception Occurred " + var3.getMessage());
        }

        return properties;
    }
}

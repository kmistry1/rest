//package com.validate.aes.unlock;
//
//import com.validate.aes.DBProperties;
//
//public class Test{
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws Exception{
//        DBProperties properties = new DBProperties();
//        String password = properties.getDBProperties().getProperty("db.encgfgf");
//
//        Decryption de =new Decryption();
//        System.out.println("Decrypted word is : " +    de.decrypt(password));
//    }
//}
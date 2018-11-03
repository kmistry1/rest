package com.validate.aes;



import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

class Encryption {
    private String password = null;
    private  String salt;
    private  int pswdIterations;
    private  int keySize;
    private  int saltlength;
    private  byte[] ivBytes;


    // Methods
    public  String encrypt(String plainText) throws Exception {
        return getEncryptedString(plainText);

    }

    protected String getEncryptedString(String plainText) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, java.security.spec.InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, SQLException, ClassNotFoundException {
        //get salt
        setValues();
        salt = generateSalt();
        byte[] saltBytes = salt.getBytes("UTF-8");

        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
        );

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        //encrypt the message
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

        String encodedText = Base64.encodeBase64String(encryptedTextBytes);
        String encodedIV = Base64.encodeBase64String(ivBytes);
        String encodedSalt = Base64.encodeBase64String(saltBytes);
        String encodedPackage = encodedSalt + "]" + encodedIV + "]" + encodedText;
        return encodedPackage;
    }

     String decrypt(String encryptedText) throws Exception {

        return getDecryptedString(encryptedText);
    }

    protected  String getDecryptedString(String encryptedText) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, SQLException, ClassNotFoundException {
        setValues();
        String[] fields = encryptedText.split("]");
        byte[] saltBytes = Base64.decodeBase64(fields[0]);
        ivBytes = Base64.decodeBase64(fields[1]);
        byte[] encryptedTextBytes = Base64.decodeBase64(fields[2]);

        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
        );

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        // Decrypt the message
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));


        byte[] decryptedTextBytes = null;
        try {
            decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return new String(decryptedTextBytes);
    }

     String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[saltlength];
        random.nextBytes(bytes);
        return new String(bytes);
    }



    protected void setValues() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        java.sql.Connection con = DriverManager.getConnection(
                "jdbc:mysql://sunny.ccy10divxtl4.us-east-2.rds.amazonaws.com/rest", "sunny", "ajxx2020");

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from aes_encryption where id = 1");



        while (rs.next()) {
            setPassword(rs.getString("aes_key"));
            setPswdIterations(rs.getInt("Iterations"));
            setKeySize(rs.getInt("Size"));
            setSaltlength(rs.getInt("Length"));
        }
        rs.close();

    }



    private void setPassword(String password){
        this.password = password;
    }

    private void setPswdIterations(int pswdIterations){
        this.pswdIterations = pswdIterations;
    }
    private void setKeySize(int keySize){
        this.keySize = keySize;
    }

    private void setSaltlength(int saltlength){
        this.saltlength = saltlength;
    }
}
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.validate.aes.encryption;

import com.validate.aes.unlock.Decryption;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

class Encryption {
    private String password = null;
    private String salt;
    private int pswdIterations;
    private int keySize;
    private int saltlength;
    private byte[] ivBytes;

    Encryption() {
    }

    public String encrypt(String plainText) throws Exception {
        return this.getEncryptedString(plainText);
    }

    protected String getEncryptedString(String plainText) throws Exception {
        this.setValues();
        this.salt = this.generateSalt();
        byte[] saltBytes = this.salt.getBytes("UTF-8");
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec spec = new PBEKeySpec(this.password.toCharArray(), saltBytes, this.pswdIterations, this.keySize);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secret);
        AlgorithmParameters params = cipher.getParameters();
        this.ivBytes = ((IvParameterSpec)params.getParameterSpec(IvParameterSpec.class)).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encodedText = Base64.encodeBase64String(encryptedTextBytes);
        String encodedIV = Base64.encodeBase64String(this.ivBytes);
        String encodedSalt = Base64.encodeBase64String(saltBytes);
        String encodedPackage = encodedSalt + "]" + encodedIV + "]" + encodedText;
        return encodedPackage;
    }

    String decrypt(String encryptedText) throws Exception {
        return this.getDecryptedString(encryptedText);
    }

    protected String getDecryptedString(String encryptedText) throws Exception {
        this.setValues();
        String[] fields = encryptedText.split("]");
        byte[] saltBytes = Base64.decodeBase64(fields[0]);
        this.ivBytes = Base64.decodeBase64(fields[1]);
        byte[] encryptedTextBytes = Base64.decodeBase64(fields[2]);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec spec = new PBEKeySpec(this.password.toCharArray(), saltBytes, this.pswdIterations, this.keySize);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secret, new IvParameterSpec(this.ivBytes));
        byte[] decryptedTextBytes = null;

        try {
            decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
        } catch (IllegalBlockSizeException var12) {
            var12.printStackTrace();
        } catch (BadPaddingException var13) {
            var13.printStackTrace();
        }

        return new String(decryptedTextBytes);
    }

    String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[this.saltlength];
        random.nextBytes(bytes);
        return new String(bytes);
    }

    protected void setValues() throws Exception {
        DBProperties properties = new DBProperties();
        SqlConnection sqlConnection = new SqlConnection();
        Decryption de = new Decryption();
        Connection con = sqlConnection.createNewConnection(de.decrypt(properties.getDBProperties().getProperty("db.enc")), de.decrypt(properties.getDBProperties().getProperty("enc.username")), de.decrypt(properties.getDBProperties().getProperty("enc.dbPassword")));
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from aes_encryption where id = 3");

        while(rs.next()) {
            this.setPassword(rs.getString("aes_key"));
            this.setPswdIterations(rs.getInt("Iterations"));
            this.setKeySize(rs.getInt("Size"));
            this.setSaltlength(rs.getInt("Length"));
        }

        rs.close();
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setPswdIterations(int pswdIterations) {
        this.pswdIterations = pswdIterations;
    }

    private void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    private void setSaltlength(int saltlength) {
        this.saltlength = saltlength;
    }
}

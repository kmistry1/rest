//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.validate.aes.unlock;

import com.validate.aes.encryption.DBProperties;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class Encryption {
    public Encryption() {
    }

    public String encrypt(String word) throws Exception {
        return this.getEncryptedString(word);
    }

    protected String getEncryptedString(String word) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        DBProperties properties = new DBProperties();
        String password = properties.getDBProperties().getProperty("db.encgfgf");
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[369];
        random.nextBytes(bytes);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), bytes, 428, 256);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secret);
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = ((IvParameterSpec)params.getParameterSpec(IvParameterSpec.class)).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(word.getBytes("UTF-8"));
        byte[] buffer = new byte[bytes.length + ivBytes.length + encryptedTextBytes.length];
        System.arraycopy(bytes, 0, buffer, 0, bytes.length);
        System.arraycopy(ivBytes, 0, buffer, bytes.length, ivBytes.length);
        System.arraycopy(encryptedTextBytes, 0, buffer, bytes.length + ivBytes.length, encryptedTextBytes.length);
        return (new Base64()).encodeToString(buffer);
    }
}

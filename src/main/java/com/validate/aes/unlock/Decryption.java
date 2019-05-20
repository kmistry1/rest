//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.validate.aes.unlock;

import com.validate.aes.encryption.DBProperties;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

public class Decryption {
    public Decryption() {
    }

    public String decrypt(String encryptedText) throws Exception {
        return this.getDecryptedString(encryptedText);
    }

    protected String getDecryptedString(String encryptedText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        DBProperties properties = new DBProperties();
        String password = properties.getDBProperties().getProperty("db.encgfgf");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ByteBuffer buffer = ByteBuffer.wrap((new Base64()).decode(encryptedText));
        byte[] saltBytes = new byte[369];
        buffer.get(saltBytes, 0, saltBytes.length);
        byte[] ivBytes1 = new byte[cipher.getBlockSize()];
        buffer.get(ivBytes1, 0, ivBytes1.length);
        byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes1.length];
        buffer.get(encryptedTextBytes);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 428, 256);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        cipher.init(2, secret, new IvParameterSpec(ivBytes1));
        byte[] decryptedTextBytes = null;

        try {
            decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
        } catch (IllegalBlockSizeException var15) {
            var15.printStackTrace();
        } catch (BadPaddingException var16) {
            var16.printStackTrace();
        }

        return new String(decryptedTextBytes);
    }
}

package com.validate.aes;



import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

class Encryption {
    private static final String password = "dfhsdhsdfhsdfhsdfhsdfhsdfhdshsfhxfhxdhdthxdghdthsdthx";
    private static String salt;
    private static int pswdIterations = 93759;
    private static int keySize = 256;
    private static int saltlength = 275;
    private static byte[] ivBytes;


    // Methods
    public static String encrypt(String plainText) throws Exception {
        return getEncryptedString(plainText);

    }

    protected static String getEncryptedString(String plainText) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, java.security.spec.InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException {
        //get salt
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

        // Base64 for Android
        //String encodedText = Base64.encodeToString(encryptedTextBytes, Base64.DEFAULT);

        // Base64 for Java
        String encodedText = Base64.encodeBase64String(encryptedTextBytes);
        String encodedIV = Base64.encodeBase64String(ivBytes);
        String encodedSalt = Base64.encodeBase64String(saltBytes);
        String encodedPackage = encodedSalt + "]" + encodedIV + "]" + encodedText;
        return encodedPackage;
    }

    static String decrypt(String encryptedText) throws Exception {

        return getDecryptedString(encryptedText);
    }

    protected static String getDecryptedString(String encryptedText) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
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

    static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[saltlength];
        random.nextBytes(bytes);
        return new String(bytes);
    }
}
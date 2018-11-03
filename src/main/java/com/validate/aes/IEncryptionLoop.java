package com.validate.aes;

public interface IEncryptionLoop {
    public static String encrypt(String encrypt)throws Exception{
        return EncryptionLoop.encrypt(encrypt);
    }
    public static String decrypt(String decrypt)throws Exception{
        return EncryptionLoop.decrypt(decrypt);
    }

}

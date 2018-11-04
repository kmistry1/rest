package com.validate.user;

import com.validate.aes.IEncryptionLoop;


 class Users {

    public static void main(String[] args) {
        String name = "name";
        try {
            name = IEncryptionLoop.encrypt(name);
            System.out.println(name);
            System.out.println(IEncryptionLoop.decrypt(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.validate.aes.encryption;

public interface IEncryptionLoop {
    static String encrypt(String encrypt) throws Exception {
        return EncryptionLoop.encrypt(encrypt);
    }

    static String decrypt(String decrypt) throws Exception {
        return EncryptionLoop.decrypt(decrypt);
    }
}

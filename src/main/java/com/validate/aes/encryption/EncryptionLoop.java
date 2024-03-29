//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.validate.aes.encryption;

public class EncryptionLoop implements IEncryptionLoop {
    static Encryption encryption = new Encryption();
    private static int loop = 1;

    EncryptionLoop() {
    }

    protected static String getSecureEncryptedString(String encrypt) throws Exception {
        for(int i = 0; i < loop; ++i) {
            encrypt = encryption.encrypt(encrypt);
        }

        return encrypt;
    }

    protected static String getSecureDecryptString(String decrypt) throws Exception {
        for(int i = 0; i < loop; ++i) {
            decrypt = encryption.decrypt(decrypt);
        }

        return decrypt;
    }

    public static String encrypt(String encrypt) throws Exception {
        return getSecureEncryptedString(encrypt);
    }

    public static String decrypt(String decrypt) throws Exception {
        return getSecureDecryptString(decrypt);
    }
}

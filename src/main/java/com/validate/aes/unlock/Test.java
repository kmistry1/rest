package com.validate.aes.unlock;

import com.validate.aes.encryption.DBProperties;
import com.validate.aes.encryption.EncryptionLoop;
import com.validate.aes.encryption.IEncryptionLoop;

public class Test {

    public static void main(String[] args) throws Exception {
//        DBProperties dbProperties = new DBProperties();
//        dbProperties.getDBProperties();
//       // String encodedKay = dbProperties.getDBProperties().getProperty("db.password");
//
//        Decryption decryption = new Decryption();
//
//        System.out.println(decryption.decrypt(encodedKay));




        // St
        // ring encodedKay = dbProperties.getDBProperties().getProperty("db.password");

        String encodedKay = "77+9SH/vv73vv70z77+9f2pkB8qf77+977+9U++/vUrvv73vv70O77+9Pk7vv71idDIS77+977+977+977+9KCvvv73vv70W77+9bO+/vd6x77+9Oy0877+9T1UQ77+977+9Vjjbmu+/ve+/ve+/ve+/vRHvv71DDe+/vREBb++/ve+/ve+/ve+/vTtOUdug77+977+977+9aO+/vR9iQ++/ve+/ve+/ve+/ve+/vXJYNhzvv71977+9MF7vv71L77+9Mxw677+9L++/vVLvv73vv73vv73vv73vv70HQn8277+977+9Y9aZ77+9Hu+/vQDUmO+/vT4a77+977+9Qm7vv71j77+977+9MVsFFHVbNO+/vTfvv73vv70m77+9f++/vQvvv73vv70T77+9LigULW7vv71/77+977+9Ku+/vQfvv73vv73vv73vv707yaZi77+977+9ZO+/vSvOme+/vQ==]HP52LxnaA943LeLbgh6SSA==]SlkCdq6kAOpnnTbqz+kQZw==";

        System.out.println(IEncryptionLoop.decrypt(encodedKay));
    }
}

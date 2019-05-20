package com.webapp.logic;

import com.validate.aes.encryption.IEncryptionLoop;
import com.validate.aes.encryption.SqlConnection;
import org.apache.commons.lang3.StringUtils;

import static com.validate.aes.encryption.ErrorCodes.*;

//jar cvf program.jar -C path .


public class UserValidator {

    private String userName;
    private String password;


    private boolean buildErrorCode(String username, String password) throws Exception {

        SqlConnection Connection = new SqlConnection();
        String passwordFromDB = Connection.getPasswordFromDB(userName);

        if (StringUtils.isBlank(username)) {
            return false;
        }

        if (StringUtils.isBlank(password)) {
            return false;
        }

        if (StringUtils.isBlank(passwordFromDB)) {
            return false;
        } else {
            if (password.equals(IEncryptionLoop.decrypt(passwordFromDB))) {
                return true;
            }

            if (!password.equals(IEncryptionLoop.decrypt(passwordFromDB))) {
                return false;
            }
        }

        return false;
    }

    public boolean getUserValidation() throws Exception {
        return buildErrorCode(getUserName(), getPassword());
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

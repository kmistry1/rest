package com.webapp.logic;

import com.validate.aes.encryption.IEncryptionLoop;
import com.validate.aes.encryption.SqlConnection;
import org.apache.commons.lang3.StringUtils;
//jar cvf program.jar -C path .
import static com.validate.aes.encryption.ErrorCodes.*;


public class UserValidator {

    private String userName;
    private String password;


    private String buildErrorCode(String username, String password) throws Exception {

        SqlConnection Connection = new SqlConnection();
        String passwordFromDB = Connection.getPasswordFromDB(userName);

        if (StringUtils.isBlank(username)){
            return USERNAME_ERROR;
        }

        if (StringUtils.isBlank(password)){
            return PASSWORD_ERROR;
        }

        if (StringUtils.isBlank(passwordFromDB)) {
            return INVALID_CREDENTIALS;
        } else {
            if (password.equals(IEncryptionLoop.decrypt(passwordFromDB))) {
                return SUCCESS;
            }

            if (!password.equals(IEncryptionLoop.decrypt(passwordFromDB))) {
                return INVALID_CREDENTIALS;
            }
        }

        return OTHER_ERR;
    }

    public String getUserValidation() throws Exception {
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

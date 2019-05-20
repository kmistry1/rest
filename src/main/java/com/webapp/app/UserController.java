package com.webapp.app;


import com.webapp.logic.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rest.UserInput;
import rest.UserOutput;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/resource/validation")
    public UserOutput validator(@RequestBody UserInput userInput) throws Exception {

        UserValidator userValidator = new UserValidator();
        userValidator.setUserName(userInput.getUsername());
        userValidator.setPassword(userInput.getPassword());
        UserOutput userOutput = new UserOutput();
        userOutput.setCredentialStatus(userValidator.getUserValidation());
        return userOutput;
    }
}

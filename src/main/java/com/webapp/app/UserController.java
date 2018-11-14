package com.webapp.app;


import com.webapp.logic.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/resource/validation")
    public String validator(@RequestParam(value = "username") String userName,
                            @RequestParam(value = "password") String password) throws Exception {

        UserValidator userValidator = new UserValidator();
        userValidator.setUserName(userName);
        userValidator.setPassword(password);

        return userValidator.getUserValidation();
    }
}

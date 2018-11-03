package com.validate;


import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static final String template = "Entered %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/api/resource/validation")
    public Increment validator(@RequestParam(value="name") String userName,@RequestParam(value="password") String password) {

        UserValidator userValidator = new UserValidator();

        userValidator.setUserName(userName);
        userValidator.setPassword(password);

        return new Increment(String.format(template, userValidator.getUserValidation()));
    }
}

package com.ydova.ahub.controller;


import com.ydova.ahub.entity.AppUser;
import com.ydova.ahub.service.UserService;
import com.ydova.cv.YdovaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UserService userService;

   @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    private AppUser login(@RequestBody AppUser appUser) throws YdovaException {
       return userService.authenticate(appUser);
    }

    @PostMapping("/sign-up")
    private AppUser signUp(@RequestBody AppUser appUser) throws YdovaException {
        return userService.create(appUser);
    }
}

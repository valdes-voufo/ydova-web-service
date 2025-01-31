package com.ydova.ahub.controller;

import com.ydova.ahub.LoginDto;
import com.ydova.ahub.entity.AppUser;
import com.ydova.ahub.service.UserService;
import com.ydova.cv.YdovaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "User Management", description = "Endpoints for user authentication and registration")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticates a user and returns user details.")
    public AppUser login(@RequestBody LoginDto dto) throws YdovaException {
        return userService.authenticate(dto);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "User Registration", description = "Creates a new user account.")
    public AppUser signUp(@RequestBody AppUser appUser) throws YdovaException {
        return userService.create(appUser);
    }
}

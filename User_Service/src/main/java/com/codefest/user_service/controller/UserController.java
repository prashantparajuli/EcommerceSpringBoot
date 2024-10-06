package com.codefest.user_service.controller;

import com.codefest.user_service.model.User;
import com.codefest.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
//    @GetMapping
//    public User getUser() {}

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try{
            User newUser = userService.registerUser(user);
            newUser.setId(user.getId());
            newUser.setPassword(null);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

package com.gorillaz.app.controller;

import com.gorillaz.app.model.User;
import com.gorillaz.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("users/all")
    public ResponseEntity<List<User>> findAll() {
        List<User> user = userService.findAll();

        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("usuario " + user.toString());
        User userCreated = userService.save(user);

        return ResponseEntity.ok(userCreated);
    }

}

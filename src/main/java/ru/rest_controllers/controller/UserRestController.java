package ru.rest_controllers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rest_controllers.model.User;
import ru.rest_controllers.security.UserDetailsServiceImpl;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserRestController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<User> getUserByUsername(Principal principal) {
        User user = userDetailsService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

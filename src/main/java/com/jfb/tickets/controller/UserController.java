package com.jfb.tickets.controller;

import com.jfb.tickets.model.Ticket;
import com.jfb.tickets.model.User;
import com.jfb.tickets.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        Optional<User> userOptional = userService.getUserById(userId);

        return userOptional.orElse(null);
    }

    @PutMapping("/update-status")
    public String updateUserStatusAndCreateTicket(@PathVariable UUID userId, @RequestParam boolean status, @RequestBody Ticket ticket) {
        try {
            userService.updateUserStatusAndCreateTicket(userId, status, ticket);
            return "User status updated and ticket created.";
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
            return "Error " + e.getMessage();
        }
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }

}

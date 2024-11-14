package com.jfb.tickets.controller;

import com.jfb.tickets.model.Ticket;
import com.jfb.tickets.model.User;
import com.jfb.tickets.service.UserService;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userService.saveUser(user);
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getUsers();
  }

  @GetMapping("/{userId}")
  public User getUserById(@PathVariable UUID userId) {
    return userService.getUserById(userId);
  }

  @PutMapping("/update-status/{userId}")
  public String updateUserStatusAndCreateTicket(
      @PathVariable UUID userId, @RequestParam boolean status, @RequestBody Ticket ticket) {
    userService.updateUserStatusAndCreateTicket(userId, status, ticket);
    return "User status updated and ticket created.";
  }

  @DeleteMapping("/{userId}")
  public void deleteUserById(@PathVariable UUID userId) {
    userService.deleteUser(userId);
  }
}

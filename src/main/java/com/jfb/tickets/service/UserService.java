package com.jfb.tickets.service;

import com.jfb.tickets.exceptions.EmptyNameFieldException;
import com.jfb.tickets.exceptions.ResourceNotFoundException;
import com.jfb.tickets.exceptions.UserUpdateNotAllowedException;
import com.jfb.tickets.model.Ticket;
import com.jfb.tickets.model.User;
import com.jfb.tickets.repository.TicketRepository;
import com.jfb.tickets.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final TicketRepository ticketRepository;

  private final boolean allowUserUpdateAndTicketCreate;

  public UserService(
      UserRepository userRepository,
      TicketRepository ticketRepository,
      @Value("${isUpdatingUserAndCreatingTicketAllowed}") boolean allowUserUpdateAndTicketCreate) {
    this.userRepository = userRepository;
    this.ticketRepository = ticketRepository;
    this.allowUserUpdateAndTicketCreate = allowUserUpdateAndTicketCreate;
  }

  public User getUserById(UUID userId) {
    Optional<User> userOptional = userRepository.findById(userId);

    return userOptional.orElseThrow(
        () ->
            new ResourceNotFoundException(
                "User with id:" + userId + " was not found", HttpStatus.NOT_FOUND));
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Transactional
  public User saveUser(User user) {

    if (user == null) {
      throw new NullPointerException();
    }

    if (user.getName().isBlank()) {
      throw new EmptyNameFieldException();
    }

    System.out.println(
        "Transaction open? : " + TransactionSynchronizationManager.isActualTransactionActive());
    userRepository.save(user);
    return user;
  }

  @Transactional
  public void deleteUser(UUID userId) {
    if (userId == null) {
      throw new NullPointerException("User id is null");
    }

    userRepository.deleteById(userId);
  }

  @Transactional
  public void updateUserStatusAndCreateTicket(UUID userId, boolean status, Ticket ticket) {
    if (!allowUserUpdateAndTicketCreate) {
      throw new UserUpdateNotAllowedException(
          "Updating is not allowed. Reason: update user flag is false");
    }
    System.out.println(
        "Transaction open? : " + TransactionSynchronizationManager.isActualTransactionActive());
    userRepository.updateUserStatus(userId, status);
    ticketRepository.save(ticket);
  }
}

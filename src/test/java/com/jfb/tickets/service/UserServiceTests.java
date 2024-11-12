package com.jfb.tickets.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

  @Mock private UserRepository userRepository;

  @Mock private TicketRepository ticketRepository;

  @InjectMocks private UserService userService;

  private UUID userId;
  private User user;

  @BeforeEach
  public void setup() {
    userId = UUID.randomUUID();
    user = new User("Test User", true);
    ReflectionTestUtils.setField(userService, "allowUserUpdateAndTicketCreate", true);
  }

  @Test
  void saveUser_WhenValidUserProvided_ShouldSaveUserSuccessfully() {
    when(userRepository.save(user)).thenReturn(user);

    User savedUser = userService.saveUser(user);

    assertNotNull(savedUser);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void saveUser_WhenNullUserProvided_ShouldThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> userService.saveUser(null));
    verify(userRepository, never()).save(null);
  }

  @Test
  void saveUser_WhenUserNameIsEmpty_ShouldThrowEmptyNameFieldException() {
    User user1 = new User("   ", false);

    assertThrows(EmptyNameFieldException.class, () -> userService.saveUser(user1));
    verify(userRepository, never()).save(user1);
  }

  @Test
  void getUserById_WhenUserIdExists_ShouldReturnUser() {
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    User result = userService.getUserById(userId);

    assertEquals(user, result);
    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void getUserById_WhenUserIdIsNotFound_ShouldThrowResourceNotFoundException() {
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void getUserById_WhenUserIdIsNull_ShouldThrowResourceNotFoundException() {
    assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(null));
    verify(userRepository, times(1)).findById(null);
  }

  @Test
  void getUsers_WhenUsersExist_ShouldReturnUsers() {
    List<User> users = List.of(user, user, user);
    when(userRepository.findAll()).thenReturn(users);

    List<User> result = userService.getUsers();

    assertEquals(users, result);
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void getUsers_WhenNoUsersFound_ShouldReturnEmptyList() {
    List<User> users = List.of();

    when(userRepository.findAll()).thenReturn(users);

    List<User> result = userService.getUsers();

    assertEquals(users, result);
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void getUsers_WhenDatabaseError_ShouldThrowRuntimeException() {
    when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

    assertThrows(RuntimeException.class, () -> userService.getUsers());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void deleteUserById_WhenUserExists_ShouldDeleteUser() {
    doNothing().when(userRepository).deleteById(userId);

    assertDoesNotThrow(() -> userService.deleteUser(userId));
    verify(userRepository, times(1)).deleteById(userId);
  }

  @Test
  void deleteUserById_WhenUserIdIsNull_ShouldThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> userService.deleteUser(null));
    verify(userRepository, never()).deleteById(null);
  }

  @Test
  void deleteUserById_WhenUserIsNotAccessible_ShouldThrowRuntimeException() {
    doThrow(new RuntimeException("Database error")).when(userRepository).deleteById(userId);

    assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
    verify(userRepository, times(1)).deleteById(userId);
  }

  @Test
  void updateUserStatusAndCreateTicket_WhenUpdateAllowed_ShouldUpdateUserStatusAndCreateTicket() {

    Ticket ticket = new Ticket();
    doNothing().when(userRepository).updateUserStatus(userId, true);
    when(ticketRepository.save(ticket)).thenReturn(ticket);

    assertDoesNotThrow(() -> userService.updateUserStatusAndCreateTicket(userId, true, ticket));
    verify(userRepository, times(1)).updateUserStatus(userId, true);
    verify(ticketRepository, times(1)).save(ticket);
  }

  @Test
  void
      updateUserStatusAndCreateTicket_WhenUpdatingIsNotAllowed_ShouldThrowUserUpdateNotAllowedException() {
    Ticket ticket = new Ticket();

    ReflectionTestUtils.setField(userService, "allowUserUpdateAndTicketCreate", false);

    assertThrows(
        UserUpdateNotAllowedException.class,
        () -> userService.updateUserStatusAndCreateTicket(userId, true, ticket));
  }

  @Test
  void updateUserStatusAndCreateTicket_WhenTicketCreationFails_ShouldThrowRuntimeException() {
    Ticket ticket = new Ticket();

    doNothing().when(userRepository).updateUserStatus(userId, true);
    when(ticketRepository.save(ticket)).thenThrow(new RuntimeException("Ticket creation failed"));

    assertThrows(
        RuntimeException.class,
        () -> userService.updateUserStatusAndCreateTicket(userId, true, ticket));
  }
}

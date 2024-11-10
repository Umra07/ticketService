package com.jfb.tickets.service;

import com.jfb.tickets.exceptions.ResourceNotFoundException;
import com.jfb.tickets.exceptions.UserUpdateNotAllowedException;

import com.jfb.tickets.model.User;
import com.jfb.tickets.model.Ticket;

import com.jfb.tickets.repository.TicketRepository;
import com.jfb.tickets.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${isUpdatingUserAndCreatingTicketAllowed}")
    private boolean allowUserUpdateAndTicketCreate;

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public User getUserById(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        return userOptional.orElseThrow(() -> new ResourceNotFoundException("User with id:" + userId + " was not found", HttpStatus.NOT_FOUND));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        System.out.println("Transaction open? : " + TransactionSynchronizationManager.isActualTransactionActive());
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUserStatusAndCreateTicket(UUID userId, boolean status, Ticket ticket) {
        if(!allowUserUpdateAndTicketCreate) {
            throw new UserUpdateNotAllowedException("Updating is not allowed. Reason: update user flag is false");
        }
        System.out.println("Transaction open? : " + TransactionSynchronizationManager.isActualTransactionActive());
        userRepository.updateUserStatus(userId, status);
        ticketRepository.save(ticket);
    }
}

package com.jfb.tickets.service;

import com.jfb.tickets.model.User;
import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.dao.UserDAO;
import com.jfb.tickets.model.Ticket;
import com.jfb.tickets.repository.TicketRepository;
import com.jfb.tickets.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.*;

@Service
public class UserService {
    @Value("${isUpdatingUserAndCreatingTicketAllowed}")
    private boolean allowUserUpdateAndTicketCreate;

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public UserService(UserRepository userRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
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
            throw new RuntimeException("User status update and ticket creation are not allowed.");
        }
        System.out.println("Transaction open? : " + TransactionSynchronizationManager.isActualTransactionActive());
        userRepository.updateUserStatus(userId, status);
        ticketRepository.save(ticket);
    }
}

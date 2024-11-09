package com.jfb.tickets.service;

import com.jfb.tickets.dao.TicketDAO;
import com.jfb.tickets.dao.UserDAO;
import com.jfb.tickets.model.Ticket;
import com.jfb.tickets.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Value("${isUpdatingUserAndCreatingTicketAllowed}")
    private boolean allowUserUpdateAndTicketCreate;

    private final UserDAO userDAO;
    private final TicketDAO ticketDAO;

    public UserService(UserDAO userDAO, TicketDAO ticketDAO) {
        this.userDAO = userDAO;
        this.ticketDAO = ticketDAO;
    }

    public User getUser(UUID userId) {
        return userDAO.get(userId);
    }

    public List<User> getUsers() {
        return userDAO.getAll();
    }

    @Transactional
    public void saveUser(User user) {
        System.out.println("Transaction open? : " + TransactionSynchronizationManager.isActualTransactionActive());
        userDAO.save(user);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        userDAO.delete(userId);
    }

    @Transactional
    public void updateUserStatusAndCreateTicket(UUID userId, boolean status, Ticket ticket) {
        if(!allowUserUpdateAndTicketCreate) {
            throw new RuntimeException("User status update and ticket creation are not allowed.");
        }
        System.out.println("Transaction open? : " + TransactionSynchronizationManager.isActualTransactionActive());
        userDAO.updateUserStatus(userId, status);
        ticketDAO.save(ticket, userId);
    }
}

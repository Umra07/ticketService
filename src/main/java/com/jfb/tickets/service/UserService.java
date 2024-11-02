package com.jfb.tickets.service;

import com.jfb.tickets.dao.UserDAO;
import com.jfb.tickets.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser(UUID userId) {
        return userDAO.get(userId);
    }

    public List<User> getUsers() {
        return userDAO.getAll();
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }

    public void deleteUser(UUID userId) {
        userDAO.delete(userId);
    }
}

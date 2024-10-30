package com.jfb.tickets.dao;

import com.jfb.tickets.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class UserDAO {

    public User get(UUID id) {
        return SessionFactoryProvider.getSessionFactory().openSession().get(User.class, id);
    }

    public List<User> getAll() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        return session.createQuery("from users", User.class).list();
    }

    public void save(User user) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(UUID userId) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}

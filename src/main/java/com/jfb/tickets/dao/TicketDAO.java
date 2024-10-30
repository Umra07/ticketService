package com.jfb.tickets.dao;

import com.jfb.tickets.DatabaseConnection;
import com.jfb.tickets.enums.TicketType;
import com.jfb.tickets.model.Ticket;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import java.time.Instant;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Connection;

public class TicketDAO {

    public Ticket get(UUID id) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.get(Ticket.class, id);
        }
    }

    public List<Ticket> getAll() {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.createQuery("from tickets", Ticket.class).list();
        }
    }

    public List<Ticket> getAllByUserId(UUID userId) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.createQuery("from tickets where userId = :userId", Ticket.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    public void save(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateTicketType(UUID ticketId, String ticketType) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, ticketId);
            if (ticket != null) {
                ticket.setTicketType(Enum.valueOf(TicketType.class, ticketType));
                session.update(ticket);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}

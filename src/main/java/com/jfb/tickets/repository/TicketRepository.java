package com.jfb.tickets.repository;

import com.jfb.tickets.enums.TicketType;
import com.jfb.tickets.model.Ticket;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends ListCrudRepository<Ticket, UUID> {
    List<Ticket> findByUserId(UUID userId);

    @Modifying
    @Query("UPDATE Ticket t SET t.ticketType = :ticketType WHERE t.id = :id")
    void updateTicketTypeById(UUID id, TicketType ticketType);
}

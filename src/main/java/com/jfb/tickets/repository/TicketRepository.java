package com.jfb.tickets.repository;

import com.jfb.tickets.enums.TicketType;
import com.jfb.tickets.model.Ticket;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends ListCrudRepository<Ticket, UUID> {
    List<Ticket> findByUserId(UUID userId);

    @Modifying
    @Query("UPDATE Ticket t SET t.ticket_type = :ticketType WHERE t.id = :id")
    public void updateTicketTypeById(UUID id, TicketType ticketType);
}

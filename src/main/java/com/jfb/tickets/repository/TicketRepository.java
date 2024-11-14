package com.jfb.tickets.repository;

import com.jfb.tickets.enums.TicketType;
import com.jfb.tickets.model.Ticket;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends ListCrudRepository<Ticket, UUID> {

  @Query("SELECT t FROM Ticket t WHERE t.userId = :userId")
  List<Ticket> findByUserId(UUID userId);

  @Modifying
  @Query("UPDATE Ticket t SET t.ticketType = :ticketType WHERE t.id = :id")
  void updateTicketTypeById(@Param("id") UUID id, @Param("ticketType") TicketType ticketType);
}

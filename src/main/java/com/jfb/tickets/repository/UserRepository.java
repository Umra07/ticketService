package com.jfb.tickets.repository;

import com.jfb.tickets.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ListCrudRepository<User, UUID> {

  @Modifying
  @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
  void updateUserStatus(@Param("id") UUID id, @Param("status") boolean status);
}

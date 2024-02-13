package com.hb.messenger.repositories;

import com.hb.messenger.models.entities.Chat;
import com.hb.messenger.models.entities.UnreadMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UnreadMessageRepository extends JpaRepository<UnreadMessages,Long> {

     @Query(value="SELECT m FROM UnreadMessages m WHERE m.user.username= :user")
     Optional<UnreadMessages> fetchUnreadMessages(@Param("user") String username);
}

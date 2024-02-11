package com.hb.messenger.repositories;


import com.hb.messenger.models.entities.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatEntity,Long> {

     @Query(value="SELECT m FROM ChatEntity m WHERE m.to= :user and m.isRead =false order by timestamp")
     List<ChatEntity> fetchUnreadMessages(@Param("user") String username);

     @Query(value="SELECT m FROM ChatEntity m WHERE (m.to= :user1 and m.from = :user2) or (m.to= :user2 and m.from= :user1) order by m.timestamp")
     List<ChatEntity> fetchChatHistory(@Param("user1") String username1, @Param("user2") String username2);

}

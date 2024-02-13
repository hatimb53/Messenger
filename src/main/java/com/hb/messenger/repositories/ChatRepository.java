package com.hb.messenger.repositories;

import com.hb.messenger.models.entities.Chat;
import com.hb.messenger.models.response.ChatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {


     @Query(value="SELECT m FROM Chat m WHERE m.type=0 and (( m.to= :userName1 and m.from = :userName2) or (m.to= :userName2 and m.from= :userName1)) order by m.timestamp")
     List<Chat> fetchChatHistory(@Param("userName1") String username1, @Param("userName2") String username2);

     @Query(value="SELECT m FROM Chat m WHERE m.type=1 and m.to= :groupName order by m.timestamp")
     List<Chat> fetchGroupChatHistory(@Param("groupName") String groupName);

}

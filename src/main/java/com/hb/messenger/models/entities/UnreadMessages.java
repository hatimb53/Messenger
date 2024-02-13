package com.hb.messenger.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name="unreadmessages",indexes = @Index(name="idx_username",columnList = "username"))

public class UnreadMessages {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "username")
    UserInfo user;

    @OneToMany
    @JoinColumn( name = "chat_id",referencedColumnName = "id")
    List<Chat> chatList;
}

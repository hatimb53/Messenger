package com.hb.messenger.models.entities;

import com.hb.messenger.models.enums.ChatType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "chat",indexes = {@Index(name="idx_sender_receiver",columnList = "receiver,sender"),
@Index(name="idx_timestamp",columnList = "timestamp"),
        @Index(name="idx_type",columnList = "type")})

public class Chat {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="type",nullable = false)
    ChatType type;

    @Column(name = "receiver", nullable = false)
    String to;

    @Column(name = "sender", nullable = false)
    String from;

    @Column(name = "message", nullable = false)
    String message;

    @Column(name="timestamp",insertable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime timestamp;


}

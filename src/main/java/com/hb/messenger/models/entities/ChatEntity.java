package com.hb.messenger.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @Column(name = "receiver", nullable = false)
    String to;

    @Column(name = "sender", nullable = false)
    String from;

    @Column(name = "message", nullable = false)
    String message;

    @Column(name="timestamp",insertable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime timestamp;

    @Column(name="isRead",nullable = false)
    boolean isRead;
}

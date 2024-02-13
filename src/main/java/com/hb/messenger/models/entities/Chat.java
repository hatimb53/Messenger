package com.hb.messenger.models.entities;

import com.hb.messenger.models.enums.ChatType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "chat", indexes = {
    @Index(name = "idx_sender_receiver", columnList = "receiver,sender"),
    @Index(name = "idx_timestamp", columnList = "timestamp"),
    @Index(name = "idx_type", columnList = "type")})
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "type", nullable = false)
  private ChatType type;

  @Column(name = "receiver", nullable = false)
  private String to;

  @Column(name = "sender", nullable = false)
  private String from;

  @Column(name = "message", nullable = false)
  private String message;

  @Column(name = "timestamp", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime timestamp;


}

package com.hb.messenger.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "unreadmessages", indexes = @Index(name = "idx_username", columnList = "username"))

public class UnreadMessages {

  @OneToOne
  @JoinColumn(name = "username")
  UserInfo user;
  @OneToMany
  @JoinColumn(name = "chat_id", referencedColumnName = "id")
  List<Chat> chatList;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
}
